package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.RuleBuilder;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Schema的根节点的规则器
 * 
 * @author Lang
 *
 */
@Guarded
public final class RootRuler implements Ruler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /**
         * 1.1.验证Required属性
         */
        final Rule rule = RuleBuilder.required("__root__");
        final Violater violater = RuleBuilder.bind(rule);
        AbstractSchemaException error = violater.violate(habitus);

        /**
         * 最终判断
         */
        if (null != error) {
            throw error;
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
