package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;

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
        RulerHelper.applyLength(habitus, FileConfig.CFG_KEY);
        /** 8.1.5.Key中包含了Array类型的元素，对Array的每个元素的Json类型限制 **/
        RulerHelper.applyJEType(habitus, FileConfig.CFG_KEY);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
