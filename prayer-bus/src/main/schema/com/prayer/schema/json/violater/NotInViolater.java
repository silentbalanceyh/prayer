package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.InvalidValueException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.NotInRule;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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
public class NotInViolater implements Violater {
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
        final ConcurrentMap<String, JsonArray> expectes = this.prepareExpected();
        AbstractSchemaException error = null;
        for (final String expected : expectes.keySet()) {
            final String literal = habitus.get(expected);
            if (StringKit.isNonNil(literal)) {
                final JsonArray values = expectes.get(expected);
                if (values.contains(literal)) {
                    error = new InvalidValueException(getClass(), this.rule.position() + " -> " + expected, values.encode(), literal, Flag.FLAG_NIN);
                }
            }
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private ConcurrentMap<String, JsonArray> prepareExpected() {
        final JsonObject expectes = rule.getRule().getJsonObject(R_VALUE);
        final ConcurrentMap<String, JsonArray> retIMap = new ConcurrentHashMap<>();
        for (final String field : expectes.fieldNames()) {
            if (StringKit.isNonNil(field)) {
                final JsonArray values = expectes.getJsonArray(field);
                if (null != values && Constants.ZERO < values.size()) {
                    retIMap.put(field, values);
                }
            }
        }
        return retIMap;
    }
}
