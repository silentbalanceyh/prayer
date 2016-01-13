package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Symbol;
import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.JTypeRule;
import com.prayer.util.reflection.Instance;
import com.prayer.util.string.StringKit;

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
public final class JTypeViolater implements Violater {
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
        final ConcurrentMap<String, Class<?>> types = habitus.typeMap();
        final ConcurrentMap<String, Class<?>> expectes = this.prepareExpected();
        /**
         * 最终返回值
         */
        AbstractSchemaException error = null;
        for (final String expected : expectes.keySet()) {
            /**
             * 如果包含则检查，没有包含则表示可选属性可能未出现
             */
            if (types.keySet().contains(expected)) {
                if (types.get(expected) != expectes.get(expected)) {
                    error = new JsonTypeConfusedException(getClass(), this.rule.position() + " -> " + expected);
                }
            }
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private ConcurrentMap<String, Class<?>> prepareExpected() {
        final JsonObject expectes = rule.getRule().getJsonObject(R_VALUE);
        final String pkg = JsonObject.class.getPackage().getName();
        final ConcurrentMap<String, Class<?>> retJMap = new ConcurrentHashMap<>();
        for (final String field : expectes.fieldNames()) {
            if (StringKit.isNonNil(field)) {
                final Class<?> clazz = Instance.clazz(pkg + Symbol.DOT + expectes.getString(field));
                if (null != clazz) {
                    retJMap.put(field, clazz);
                }
            }
        }
        return retJMap;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
