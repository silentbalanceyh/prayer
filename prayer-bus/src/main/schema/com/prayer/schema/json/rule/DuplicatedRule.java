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
public final class DuplicatedRule extends AbstractRule implements Rule{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Rule create(@NotNull @NotEmpty @NotBlank final String rule){
        return new DuplicatedRule(rule);
    }
    // ~ Constructors ========================================
    /** **/
    private DuplicatedRule(final String rule){
        super(rule,Names.RULE_DUP);
    }
    // ~ Abstract Methods ====================================
    // ~ Overide Methods =====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
