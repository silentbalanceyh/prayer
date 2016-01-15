package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.JTypeRule;
import com.prayer.util.reflection.Instance;

import io.vertx.core.json.JsonObject;
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
        final ConcurrentMap<String, Class<?>> expectes = this.preparedMap(rule, this::extract);
        /**
         * 最终返回值
         */
        AbstractSchemaException error = null;

        final String key = VHelper.calculate(types, expectes, this::wrong);
        if (null != key) {
            final Object[] arguments = new Object[] { this.rule.position() + " -> " + key };
            error = this.error(rule, arguments, null);
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 返回错误信息的条件
     * @param left
     * @param right
     * @return
     */
    private boolean wrong(final Class<?> left, final Class<?> right) {
        return left != right;
    }

    private Class<?> extract(final Object value) {
        Class<?> clazz = null;
        if (null != value) {
            final String cls = JsonObject.class.getPackage().getName() + Symbol.DOT + value.toString();
            clazz = Instance.clazz(cls);
        }
        return clazz;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
