package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.LengthRule;

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
public final class LengthViolater extends AbstractViolater implements Violater {

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
    public LengthViolater(@NotNull @InstanceOfAny(LengthRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        final ConcurrentMap<String, Integer> fieldLen = this.extractData(habitus);
        final ConcurrentMap<String, Integer> expectes = this.preparedMap(rule, this::extract);

        AbstractSchemaException error = null;
        final String key = VExecutor.map(fieldLen, expectes, VCondition::lt);
        if (null != key) {
            final Object[] arguments = new Object[] { this.rule.position() + " -> " + key };
            // 别忘记传第四参数
            error = this.error(rule, arguments, null, key);
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Integer extract(final Object value) {
        int size = -1;
        if (null != value) {
            size = Integer.parseInt(value.toString());
        }
        return size;
    }

    /** 读取原始字段的所有数据信息JsonArray类型的所有长度信息 **/
    private ConcurrentMap<String, Integer> extractData(final ObjectHabitus habitus) {
        final ConcurrentMap<String, Object> values = habitus.values();
        final ConcurrentMap<String, Integer> ret = new ConcurrentHashMap<>();
        for (final String key : values.keySet()) {
            final Object value = values.get(key);
            if (null != value && JsonArray.class == value.getClass()) {
                ret.put(key, ((JsonArray) value).size());
            }
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
