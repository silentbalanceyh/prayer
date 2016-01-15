package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;

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
public final class FKeyRuler implements Ruler {

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
    public FKeyRuler(@AssertFieldConstraints("table") final String table, @NotNull final ArrayHabitus fields) {
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

    /**
     * 是否跳过DB检查规则，如果当前定义表和refTable相同则跳过，走自定义外键检查流程
     * 
     * @param habitus
     * @return
     */
    private boolean skipDbCheck(final ObjectHabitus habitus) {
        boolean ret = false;
        final String refTable = habitus.get(Attributes.F_REF_TABLE);
        if (refTable.equals(this.table)) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    private JsonObject getAddtional(final ObjectHabitus habitus) {
        final JsonObject addtional = new JsonObject();
        addtional.put(Attributes.F_NAME, habitus.get(Attributes.F_NAME));
        addtional.put(Attributes.F_TYPE, habitus.get(Attributes.F_TYPE));
        addtional.put(Attributes.F_COL_TYPE, habitus.get(Attributes.F_COL_TYPE));
        return addtional;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
