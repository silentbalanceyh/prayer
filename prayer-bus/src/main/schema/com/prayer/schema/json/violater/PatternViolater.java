package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.PatternRule;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * Error 10003 : PatternNotMatchException
 * 
 * @author Lang
 *
 */
@Guarded
public final class PatternViolater extends AbstractViolater implements Violater {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    @NotNull
    private transient final Rule rule;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public PatternViolater(@NotNull @InstanceOfAny(PatternRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        final ConcurrentMap<String, Object> values = habitus.values();
        final ConcurrentMap<String, Pattern> expectes = this.preparedMap(rule, this::extract);
        /** **/
        AbstractSchemaException error = null;
        final String key = VHelper.calculate(values, expectes, VCondition::unmatch);
        if (null != key) {
            final Object[] arguments = new Object[] { this.rule.position() + " -> " + key, habitus.get(key),
                    expectes.get(key).toString() };
            error = this.error(rule, arguments, habitus.addtional(), key);
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Pattern extract(final Object value) {
        Pattern pattern = null;
        if (null != value) {
            pattern = Pattern.compile(value.toString());
        }
        return pattern;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
