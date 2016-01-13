package com.prayer.schema.json.violater;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.DuplicatedTablesException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.DiffRule;
import com.prayer.util.string.StringKit;

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
public final class DiffViolater implements Violater {
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
    public DiffViolater(@NotNull @InstanceOfAny(DiffRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** **/
        final JsonObject diffMap = this.rule.getRule().getJsonObject(R_VALUE);
        /** **/
        AbstractSchemaException error = null;
        for (final String source : diffMap.fieldNames()) {
            // Source Key不可以为null
            if (null != source) {
                final String target = diffMap.getString(source);
                // Target Key不可以为null
                if (null != target) {
                    final String sourceValue = habitus.get(source);
                    final String targetValue = habitus.get(target);
                    // 两个属性的值不可以相同
                    if (StringKit.isNonNil(sourceValue) && StringKit.isNonNil(targetValue)
                            && StringKit.equals(sourceValue, targetValue)) {
                        error = new DuplicatedTablesException(getClass(), this.rule.position() + " ==> " + source, target);
                    }
                }
            }
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
