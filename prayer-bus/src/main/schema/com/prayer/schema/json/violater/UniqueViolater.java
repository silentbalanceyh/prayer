package com.prayer.schema.json.violater;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.schema.AbstractViolater;
import com.prayer.schema.json.rule.UniqueRule;

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
public final class UniqueViolater extends AbstractViolater implements Violater {
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
    public UniqueViolater(@NotNull @InstanceOfAny(UniqueRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** 提取各种数据 **/
        final JsonObject addtional = habitus.addtional();
        final JsonArray data = habitus.data();
        final JsonArray filters = this.mergeFilters(habitus.filter());
        /** **/
        AbstractSchemaException error = null;
        final JsonObject ret = VExecutor.execute(data, filters, VCondition::neq);
        if (null != ret) {
            error = this.error(rule, new Object[] {}, addtional, extractField(ret, null));
        }
        return error;
    }

    /** Unique Fule的特殊方法，合并Filter，和Least以及Most做法相同 **/
    private JsonArray mergeFilters(final JsonObject dynamicFilter) {
        final JsonArray filters = this.rule.getRule();
        /** 格式化每一个Filters **/
        final int size = filters.size();
        for (int pos = 0; pos < size; pos++) {
            final Object item = filters.getValue(pos);
            if (null != item && JsonObject.class == item.getClass()) {
                final JsonObject filter = filters.getJsonObject(pos);
                if (filter.containsKey(Filters.FILTER)
                        && JsonObject.class == filter.getValue(Filters.FILTER).getClass()) {
                    filter.getJsonObject(Filters.FILTER).mergeIn(dynamicFilter);
                }
                filter.put(Filters.OCCURS, Constants.ONE);
            }
        }
        return filters;
    }
}
