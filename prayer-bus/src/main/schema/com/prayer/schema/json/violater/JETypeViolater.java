package com.prayer.schema.json.violater;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.schema.AbstractViolater;
import com.prayer.schema.json.rule.JETypeRule;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class JETypeViolater extends AbstractViolater implements Violater {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final Rule rule;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public JETypeViolater(@NotNull @InstanceOfAny(JETypeRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        final ConcurrentMap<String, List<Class<?>>> types = this.extractData(habitus);
        final ConcurrentMap<String, Class<?>> expectes = this.preparedMap(rule, this::extractClass);

        AbstractSchemaException error = null;
        final String key = VExecutor.focalize(types, expectes, VCondition::neq);
        if (null != key) {
            final Object[] arguments = new Object[] { this.rule.position() + " -> " + key };
            error = this.error(rule, arguments, null);
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 将类型信息展开
     * 
     * @param habitus
     * @return
     */
    // 如：columns -> [1,"Test",{}]
    // 变成：columns -> [java.lang.Integer, java.lang.String, JsonObject]
    private ConcurrentMap<String, List<Class<?>>> extractData(final ObjectHabitus habitus) {
        final ConcurrentMap<String, Object> values = habitus.values();
        final ConcurrentMap<String, List<Class<?>>> ret = new ConcurrentHashMap<>();
        for (final String key : values.keySet()) {
            final Object value = values.get(key);
            if (null != value && JsonArray.class == value.getClass()) {
                final List<Class<?>> types = new ArrayList<>();
                final JsonArray valueArr = (JsonArray) value;
                for (final Object item : valueArr) {
                    if (null != item) {
                        types.add(item.getClass());
                    }
                }
                ret.put(key, types);
            }
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
