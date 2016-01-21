package com.prayer.schema.json.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.schema.rule.Rule;
import com.prayer.fantasm.schema.AbstractRule;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 最多出现次数
 * 
 * @author Lang
 *
 */
@Guarded
public final class MostRule extends AbstractRule implements Rule {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MostRule.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Rule create(@NotNull @NotEmpty @NotBlank final String rule){
        return new MostRule(rule);
    }
    // ~ Constructors ========================================
    /** **/
    private MostRule(final String rule) {
        super(rule, Names.RULE_MST);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
