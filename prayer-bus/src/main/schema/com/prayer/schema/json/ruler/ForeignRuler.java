package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.RuleConstants;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.schema.json.JObjectHabitus;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class ForeignRuler implements Ruler {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前表的信息，用于外键是否需要判断当前引用表的信息 **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String table;
    /** **/
    @NotNull
    private transient final ArrayHabitus fields;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ForeignRuler(@AssertFieldConstraints("table") final String table, @NotNull final ArrayHabitus fields) {
        this.table = table;
        this.fields = fields;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /** (20.1) 6.1.1. 外键字段必须包含两个特殊属性refId, refTable **/
        // Required: refId, refTable
        RulerHelper.applyExisting(habitus, FileConfig.CFG_FFK);
        /** (20.2) 6.1.2. 检查refId和refTable的格式对不对 **/
        final JsonObject addtional = this.getAddtional(habitus);
        RulerHelper.applyPattern(habitus, FileConfig.CFG_FFK, addtional);
        /** (21.1) 6.1.3. 检查Foreign Key的类型是否正确 **/
        RulerHelper.applyIn(habitus, FileConfig.CFG_FFK, addtional);
        /** (21.3) 分流检查外键的表值 **/
        if (skipDbCheck(habitus)) {
            /** (21.3.2) 6.1.4. 直接从Schema中检查 **/
            applySchemaRule(habitus);
        } else {
            /** (21.3.1) 6.1.5. Database检查 **/
            applyDatabaseRule(habitus);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void applySchemaRule(final ObjectHabitus habitus) throws AbstractSchemaException {
        /** (21.3.2.1) 6.1.4.1. 因为引用了本表信息，所以这里不需要考虑这种情况，直接SKIP **/
        /** (21.3.2.2) 6.1.4.2. 验证外键对应的字段是否存在，在Json中查找是否包含了R_REF_ID的字段 **/
        /** (21.3.2.3) 6.1.4.3. 验证外键字段的约束是否OK **/
        final ObjectHabitus complexHabitus = wrapperHabitus(habitus);
        /** 动态Filter在程序中构造，静态的Filter则直接在Violater中处理 **/
        {
            final JsonObject filter = new JsonObject();
            filter.put(Attributes.F_COL_NAME, habitus.get(Attributes.F_REF_ID));
            complexHabitus.filter(filter);
        }
        // TODO: 验证Schema过程中暂时不考虑Schema中引用Unique键的情况，因为Unique带有默认值，原来也不验证
        RulerHelper.applyUnique(complexHabitus, FileConfig.CFG_FFK);
        /** (21.3.2.4) 6.1.4.4. 验证外键字段的类型是否OK **/
        {
            final JsonObject filter = new JsonObject();
            filter.put(Attributes.F_COL_NAME, habitus.get(Attributes.F_REF_ID));
            filter.put(Attributes.F_TYPE, habitus.get(Attributes.F_TYPE));
            complexHabitus.filter(filter);
        }
        RulerHelper.applyUnique(complexHabitus, FileConfig.CFG_FFKH);
    }

    private void applyDatabaseRule(final ObjectHabitus habitus) throws AbstractSchemaException {
        /** (21.3.1.1) 6.1.5.1. 验证外键表是否存在 **/
        // Db Table : refTable
        RulerHelper.applyDBTable(habitus, FileConfig.CFG_FFK);
        /** (21.3.1.2) 6.1.5.2. 验证外键表对应的字段是否存在 **/
        // Db Table : refTable, refId
        RulerHelper.applyDBColumn(habitus, FileConfig.CFG_FFK);
        /** (21.3.1.3) 6.1.5.3. 验证外键对应字段的约束是OK的 **/
        // Db Table : refTable, refId
        RulerHelper.applyDBConstraint(habitus, FileConfig.CFG_FFK);
        /** (21.3.1.4) 6.1.5.4. 验证外键在数据库中的类型是否匹配 **/
        // Db Table : refTable, refId, columnType
        RulerHelper.applyDBType(habitus, FileConfig.CFG_FFK);
    }

    private JsonObject getAddtional(final ObjectHabitus habitus) {
        final JsonObject addtional = new JsonObject();
        addtional.put(Attributes.F_COL_TYPE, habitus.get(Attributes.F_COL_TYPE));
        addtional.put(Attributes.F_COL_NAME, habitus.get(Attributes.F_COL_NAME));
        addtional.put(Attributes.M_TABLE, this.table);
        return addtional;
    }

    /**
     * 获取Schema的新ObjectHabitus
     * 
     * @param habitus
     * @return
     */
    private ObjectHabitus wrapperHabitus(final ObjectHabitus habitus) {
        final JsonObject data = new JsonObject();
        data.put(RuleConstants.R_DATA, this.fields.data());
        data.put(RuleConstants.R_ADDT, this.getAddtional(habitus));
        final ObjectHabitus ret = new JObjectHabitus(data);
        return ret;
    }

    /**
     * 是否跳过DB检查规则，如果当前定义表和refTable相同则跳过，走自定义外键检查流程
     * 
     * @param habitus
     * @return
     */
    private boolean skipDbCheck(final ObjectHabitus habitus) {
        boolean ret = false;
        final String refTable = habitus.get(Attributes.F_REF_TABLE).toString();
        if (refTable.equals(this.table)) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
