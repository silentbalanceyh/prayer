package com.prayer.schema.json.rule;

import com.prayer.facade.schema.rule.Rule;
import com.prayer.fantasm.schema.AbstractRule;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class PatternRule extends AbstractRule implements Rule{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Rule create(@NotNull @NotEmpty @NotBlank final String rule) {
        return new PatternRule(rule);
    }
    // ~ Constructors ========================================
    /** 私有构造函数 **/
    public PatternRule(final String rule){
        super(rule, Names.RULE_PTN);
    }
    
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
