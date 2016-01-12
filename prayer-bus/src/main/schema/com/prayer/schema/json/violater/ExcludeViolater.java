package com.prayer.schema.json.violater;

import java.util.Set;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ExcludeViolater implements Violater {
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
    public ExcludeViolater(@NotNull final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** **/
        final Set<String> fields = habitus.fields();
        final JsonArray excluded = this.rule.getRule().getJsonArray(R_VALUE);
        /** 遍历所有排除的属性 **/
        AbstractSchemaException error = null;
        for (final Object item : excluded) {
            if (null != item && String.class == item.getClass()) {
                if (fields.contains(item)) {
                    error = new OptionalAttrMorEException(getClass(), item.toString(), "Existing");
                    break;
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
