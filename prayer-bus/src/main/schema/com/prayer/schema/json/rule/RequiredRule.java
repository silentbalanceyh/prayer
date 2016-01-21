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
 * 
 * @author Lang
 *
 */
@Guarded
public final class RequiredRule extends AbstractRule implements Rule {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RequiredRule.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Rule create(@NotNull @NotEmpty @NotBlank final String rule) {
        return new RequiredRule(rule);
    }

    // ~ Constructors ========================================
    /** 私有构造函数 **/
    private RequiredRule(final String rule) {
        super(rule, Names.RULE_REQ);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @return
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
