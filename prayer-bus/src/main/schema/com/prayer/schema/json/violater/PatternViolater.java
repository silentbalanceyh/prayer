package com.prayer.schema.json.violater;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonObject;
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
public final class PatternViolater implements Violater {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    @NotNull
    private transient final Rule rule;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public PatternViolater(@NotNull final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        final Set<String> fields = habitus.fields();
        final ConcurrentMap<String, Pattern> expectes = this.prepareExpected();
        /** **/
        AbstractSchemaException error = null;
        for (final String field : fields) {
            if (StringKit.isNonNil(field)) {
                final Pattern pattern = expectes.get(field);
                // 没有对应的Pattern设置，则直接不匹配，Skip掉
                if (null != pattern) {
                    final Matcher matcher = pattern.matcher(habitus.get(field));
                    if (!matcher.matches()) {
                        error = new PatternNotMatchException(getClass(), this.rule.position() + " -> " + field,
                                habitus.get(field), pattern.toString());
                    }
                }
            }
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private ConcurrentMap<String, Pattern> prepareExpected() {
        final JsonObject expectes = rule.getRule().getJsonObject(R_VALUE);
        final ConcurrentMap<String, Pattern> retPMap = new ConcurrentHashMap<>();
        for (final String field : expectes.fieldNames()) {
            if (StringKit.isNonNil(field)) {
                final Pattern pattern = Pattern.compile(expectes.getString(field));
                if (null != pattern) {
                    retPMap.put(field, pattern);
                }
            }
        }
        return retPMap;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
