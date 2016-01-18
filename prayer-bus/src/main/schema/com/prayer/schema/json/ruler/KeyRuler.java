package com.prayer.schema.json.ruler;

import static com.prayer.util.Converter.fromStr;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class KeyRuler implements Ruler {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /** 8.1.1.验证Required属性 **/
        RulerHelper.applyRequired(habitus, FileConfig.CFG_KEY);
        /** 8.1.2.验证columns的类型 **/
        RulerHelper.applyJType(habitus, FileConfig.CFG_KEY);
        /** 8.1.3.验证不支持属性 **/
        RulerHelper.applyUnsupport(habitus, FileConfig.CFG_KEY);
        /** 8.1.4.验证Patterns **/
        RulerHelper.applyPattern(habitus, FileConfig.CFG_KEY);
        /** 8.1.6.Key中包含了Array类型的元素，对Array的长度限制 **/
        RulerHelper.applyMinLength(habitus, FileConfig.CFG_KEY, new JsonObject());
        /** 8.1.5.Key中包含了Array类型的元素，对Array的每个元素的Json类型限制 **/
        RulerHelper.applyJEType(habitus, FileConfig.CFG_KEY);
        /** 8.1.6.Multi特殊验证 **/
        applyMulti(habitus);
        /** 8.1.7.Key Names验证 **/
        applyKeyNames(habitus);
        
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void applyKeyNames(final ObjectHabitus habitus) throws AbstractSchemaException {
        final KeyCategory category = fromStr(KeyCategory.class, habitus.get(Attributes.K_CATEGORY).toString());
        /** Prepare for addtional **/
        final JsonObject addtional = new JsonObject();
        addtional.put("name", habitus.get(Attributes.K_NAME));
        addtional.put("category", category.toString());
        /** Dispatch **/
        switch (category) {
        case PrimaryKey:
            RulerHelper.applyPattern(habitus, FileConfig.CFG_KPK, addtional);
            break;
        case ForeignKey:
            RulerHelper.applyPattern(habitus, FileConfig.CFG_KFK, addtional);
            break;
        case UniqueKey:
            RulerHelper.applyPattern(habitus, FileConfig.CFG_KUK, addtional);
            break;
        }
    }

    private void applyMulti(final ObjectHabitus habitus) throws AbstractSchemaException {
        final boolean multi = Boolean.parseBoolean(habitus.get(Attributes.K_MULTI).toString());
        final JsonObject addtional = new JsonObject();
        addtional.put("value", habitus.get(Attributes.K_COLUMNS).toString());
        if (multi) {
            addtional.put("pattern", " Length > 1 ");
            /** 8.1.6.1. Multi = true **/
            RulerHelper.applyMinLength(habitus, FileConfig.CFG_KEY_M, addtional);
        } else {
            addtional.put("pattern", " Length == 1 ");
            /** 8.1.6.2. Multi = false **/
            RulerHelper.applyMaxLength(habitus, FileConfig.CFG_KEY_M, addtional);
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
