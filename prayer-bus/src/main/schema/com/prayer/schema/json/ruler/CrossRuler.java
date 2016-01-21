package com.prayer.schema.json.ruler;

import static com.prayer.util.Converter.fromStr;

import java.util.List;

import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.RuleConstants;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.schema.json.JObjectHabitus;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class CrossRuler implements Ruler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Field的Habitus问题 **/
    @NotNull
    private transient final ArrayHabitus fields;
    /** Key的Habitus问题 **/
    @NotNull
    private transient final ArrayHabitus keys;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public CrossRuler(@NotNull final ArrayHabitus keys, @NotNull final ArrayHabitus fields) {
        this.fields = fields;
        this.keys = keys;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /** 9.1. 验证multi和policy的匹配程度 **/
        applyPolicy(habitus);
        /** 9.2. 验证Meta中的SubTable和SubKey对应的数据类型是否匹配 **/
        applyColumns();
        /** 9.3. 验证属性的次数是否匹配 **/
        applyAttrTimes(habitus);
        /** 9.4. 验证最后SubType的类型 **/
        if (checkSubType(habitus)) {
            applySubType(habitus);
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private boolean checkSubType(final ObjectHabitus habitus) {
        final Object table = habitus.get(Attributes.M_SUB_TABLE);
        final Object key = habitus.get(Attributes.M_SUB_KEY);
        return null != table && null != key;
    }

    private void applySubType(final ObjectHabitus habitus) throws AbstractSchemaException {
        /** 查找Column类型 **/
        final List<ObjectHabitus> objects = this.fields.objects();
        String colType = "";
        for (final ObjectHabitus item : objects) {
            Boolean isPK = false;
            if (null != item.get(Attributes.F_PK)) {
                isPK = Boolean.parseBoolean(item.get(Attributes.F_PK).toString());
            }
            if (isPK) {
                colType = item.get(Attributes.F_COL_TYPE).toString();
            }
        }
        /** 转换ObjectHabitus **/
        final JsonObject data = new JsonObject();
        data.put("type", colType);
        data.put(Attributes.M_SUB_TABLE, habitus.get(Attributes.M_SUB_TABLE).toString());
        data.put(Attributes.M_SUB_KEY, habitus.get(Attributes.M_SUB_KEY).toString());
        RulerHelper.applyDBType(new JObjectHabitus(data), FileConfig.CFG_CDBT);
    }

    // ~ Attributes ==========================================
    private void applyAttrTimes(final ObjectHabitus habitus) throws AbstractSchemaException {
        final List<ObjectHabitus> objects = this.keys.objects();
        for (final ObjectHabitus item : objects) {
            final JsonArray columns = (JsonArray) item.get(Attributes.K_COLUMNS);
            final KeyCategory category = fromStr(KeyCategory.class, item.get(Attributes.K_CATEGORY).toString());
            for (final Object column : columns) {
                if (null != column) {
                    final ObjectHabitus complexHabitus = wrapperHabitus(column.toString(), category, columns.size());
                    RulerHelper.applyUnique(complexHabitus, FileConfig.CFG_CATT);
                }
            }
        }
    }

    private ObjectHabitus wrapperHabitus(final String column, final KeyCategory category, final int expected) {
        final JsonObject data = new JsonObject();
        data.put(RuleConstants.R_DATA, this.fields.data());
        /** 构造Addtional **/
        final JsonObject addtional = new JsonObject();
        final String attr = getAttribute(category);
        addtional.put("attribute", attr);
        addtional.put("expected", String.valueOf(expected));
        addtional.put("category", category.toString());
        data.put(RuleConstants.R_ADDT, addtional);
        final ObjectHabitus ret = new JObjectHabitus(data);
        final JsonObject filter = new JsonObject();
        filter.put(attr, Boolean.TRUE);
        filter.put(Attributes.F_COL_NAME, column);
        ret.filter(filter);
        return ret;
    }

    private String getAttribute(final KeyCategory category) {
        String ret = "";
        switch (category) {
        case PrimaryKey:
            ret = "primarykey";
            break;
        case ForeignKey:
            ret = "foreignkey";
            break;
        case UniqueKey:
            ret = "unique";
            break;
        }
        return ret;
    }

    // ~ Column ==============================================

    private void applyColumns() throws AbstractSchemaException {
        final List<ObjectHabitus> objects = this.keys.objects();
        for (final ObjectHabitus habitus : objects) {
            final String key = habitus.get(Attributes.K_NAME).toString();
            final JsonArray columns = (JsonArray) habitus.get(Attributes.K_COLUMNS);
            for (final Object column : columns) {
                if (null != column) {
                    final ObjectHabitus complexHabitus = wrapperHabitus(column.toString(), key);
                    RulerHelper.applyUnique(complexHabitus, FileConfig.CFG_CCOL);
                }
            }
        }
    }

    private ObjectHabitus wrapperHabitus(final String column, final String key) {
        final JsonObject data = new JsonObject();
        data.put(RuleConstants.R_DATA, this.fields.data());
        /** 构造Addtinoal **/
        final JsonObject addtional = new JsonObject();
        addtional.put("column", column);
        addtional.put("key", key);
        data.put(RuleConstants.R_ADDT, addtional);
        final ObjectHabitus ret = new JObjectHabitus(data);
        final JsonObject filter = new JsonObject();
        filter.put(Attributes.F_COL_NAME, column);
        ret.filter(filter);
        return ret;
    }

    // ~ Policy =============================================

    private void applyPolicy(final ObjectHabitus habitus) throws AbstractSchemaException {
        final MetaPolicy policy = fromStr(MetaPolicy.class, habitus.get(Attributes.M_POLICY).toString());
        final ObjectHabitus complexHabitus = wrapperHabitus(habitus);
        if (MetaPolicy.COLLECTION == policy) {
            /** 9.1.1. policy = Collection -> multi = true **/
            RulerHelper.applyUnique(complexHabitus, FileConfig.CFG_CPMT);
        } else {
            /** 9.1.1. policy != Collection -> multi = false **/
            RulerHelper.applyUnique(complexHabitus, FileConfig.CFG_CPMN);
        }
    }

    private ObjectHabitus wrapperHabitus(final ObjectHabitus habitus) {
        final JsonObject data = new JsonObject();
        data.put(RuleConstants.R_DATA, this.keys.data());
        /** 构造Addtinoal **/
        final JsonObject addtional = new JsonObject();
        addtional.put("policy", habitus.get(Attributes.M_POLICY).toString());
        data.put(RuleConstants.R_ADDT, addtional);
        final ObjectHabitus ret = new JObjectHabitus(data);
        ret.filter(new JsonObject());
        return ret;
    }
    // ~ hashCode,equals,toString ============================

}
