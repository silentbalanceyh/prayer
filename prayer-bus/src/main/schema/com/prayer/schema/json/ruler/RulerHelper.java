package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.RuleBuilder;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class RulerHelper {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param habitus
     * @param rule
     * @throws AbstractSchemaException
     */
    public static void sharedApply(@NotNull final ObjectHabitus habitus, @NotNull final Rule rule)
            throws AbstractSchemaException {
        final Violater violater = RuleBuilder.bind(rule);
        AbstractSchemaException error = violater.violate(habitus);
        if (null != error) {
            throw error;
        }
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private RulerHelper() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
