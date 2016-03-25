package com.prayer.schema.json.violater;

import static com.prayer.util.Converter.toStr;

import com.prayer.builder.util.SqlTypes;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.schema.AbstractViolater;
import com.prayer.schema.json.rule.VectorRule;

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
public final class VectorViolater extends AbstractViolater implements Violater {
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
    public VectorViolater(@NotNull @InstanceOfAny(VectorRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        AbstractSchemaException error = null;
        final JsonObject checked = rule.getRule();
        if (null != checked && checked.getBoolean("check")) {
            // 1.提取数据库列类型：columnType
            final String value = SqlTypes.get(habitus.get("columnType"));
            // 2.提取当前类型的支持：supports
            final String type = habitus.get("type").toString();
            final JsonArray supports = SqlTypes.supports(type);
            boolean ret = VCondition.nin(value, supports);
            if (ret) {
                final JsonObject addtional = new JsonObject().put("columnType", value).put("type", type);
                error = this.error(rule, new Object[] { toStr(supports) }, addtional);
            }
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
