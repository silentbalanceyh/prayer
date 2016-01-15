package com.prayer.schema.json.violater;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.MostRule;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 最多出现次数
 * 
 * @author Lang
 *
 */
@Guarded
public final class MostViolater extends AbstractViolater implements Violater {
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
    public MostViolater(@NotNull @InstanceOfAny(MostRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** **/
        final JsonArray data = habitus.data();
        final JsonObject addtional = habitus.addtional();
        final JsonArray filters = this.rule.getRule().getJsonArray(R_VALUE);
        /** **/
        AbstractSchemaException error = null;
        final JsonObject ret = VHelper.calculate(data, filters, VCondition::leastRepel);
        if (null != ret) {
            error = this.error(rule, new Object[] {}, addtional);
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
