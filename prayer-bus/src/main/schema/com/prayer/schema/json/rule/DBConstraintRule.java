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
public final class DBConstraintRule extends AbstractRule implements Rule{
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DBConstraintRule.class);
    
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Rule create(@NotNull @NotEmpty @NotBlank final String rule){
        return new DBConstraintRule(rule);
    }
    // ~ Constructors ========================================
    /** **/
    private DBConstraintRule(final String rule){
        super(rule, Names.RULE_DB_CT);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
