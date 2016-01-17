package com.prayer.schema.json.ruler;

import java.util.Locale;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class TypeRuler implements Ruler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前这个Ruler验证的类型 **/
    @NotNull
    private transient final DataType type;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 构造函数，根据不同类型构造 **/
    @PostValidateThis
    public TypeRuler(@NotNull final DataType type) {
        this.type = type;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        final String ruleFile = FileConfig.CFG_TYPE + this.type.name().toLowerCase(Locale.getDefault());
        /** 7.1.1. 验证Required属性 **/
        RulerHelper.applyRequired(habitus, ruleFile);
        /** 7.1.2. 验证Unsupport属性 **/
        RulerHelper.applyUnsupport(habitus, ruleFile);
        /** 7.1.3. 验证Pattern **/
        RulerHelper.applyPattern(habitus, ruleFile);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
