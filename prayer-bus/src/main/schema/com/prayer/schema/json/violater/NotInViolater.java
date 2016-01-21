package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.schema.AbstractViolater;
import com.prayer.schema.json.rule.NotInRule;

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
public final class NotInViolater extends AbstractViolater implements Violater {
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
    public NotInViolater(@NotNull @InstanceOfAny(NotInRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        final ConcurrentMap<String, Object> values = habitus.values();
        final ConcurrentMap<String, JsonArray> expectes = this.preparedMap(rule, this::extract);
        AbstractSchemaException error = null;
        /**
         * 直接检查Not In，如果为null表示并没有包含，则直接Skip
         */
        String key = VExecutor.map(values, expectes, VCondition::in);
        if (null != key) {
            final Object[] arguments = new Object[] { this.rule.position() + " -> " + key, expectes.get(key).encode(),
                    habitus.get(key), Flag.FLAG_NIN };
            error = this.error(rule, arguments, null);
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
}
