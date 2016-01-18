package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.JTypeRule;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * Error 10002: JsonTypeConfusedException
 * 
 * @author Lang
 *
 */
@Guarded
public final class JTypeViolater extends AbstractViolater implements Violater {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Violater和Rule绑定，一个Violater只能有一个Rule **/
    @NotNull
    private transient final Rule rule;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public JTypeViolater(@NotNull @InstanceOfAny(JTypeRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 验证当前这条Rule的Json类型信息
     */
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        final ConcurrentMap<String, Class<?>> types = habitus.types();
        final ConcurrentMap<String, Class<?>> expectes = this.preparedMap(rule, this::extractClass);
        /**
         * 最终返回值
         */
        AbstractSchemaException error = null;
        final String key = VExecutor.map(types, expectes, VCondition::neq);
        if (null != key) {
            final Object[] arguments = new Object[] { this.rule.position() + " -> " + key };
            error = this.error(rule, arguments, null);
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
