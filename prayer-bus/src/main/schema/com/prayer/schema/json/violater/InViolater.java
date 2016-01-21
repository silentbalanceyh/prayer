package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.schema.AbstractViolater;
import com.prayer.schema.json.rule.InRule;

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
public final class InViolater extends AbstractViolater implements Violater {
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
    public InViolater(@NotNull @InstanceOfAny(InRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** 准备数据 **/
        final ConcurrentMap<String, JsonArray> expectes = this.preparedMap(rule, this::extract);
        final ConcurrentMap<String, Object> values = habitus.values();

        AbstractSchemaException error = null;
        /**
         * 先检查in是否OK，如果为null表示OK，已经存在，则不进行Patterns匹配
         */
        String key = VExecutor.map(values, expectes, VCondition::nin);
        if (null != key) {
            /**
             * 操作失败，直接返回Error
             */
            final Object[] arguments = new Object[] { this.rule.position() + " -> " + key, expectes.get(key).encode(),
                    habitus.get(key), Flag.FLAG_IN };
            error = this.error(rule, arguments, habitus.addtional(), key);
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private JsonArray extract(final Object value) {
        JsonArray array = null;
        if (JsonArray.class == value.getClass()) {
            array = (JsonArray) value;
        }
        return array;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
