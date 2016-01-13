package com.prayer.schema.json.violater;

import java.util.HashSet;
import java.util.Set;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.ExistingRule;

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
public class ExistingViolater implements Violater {
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
    public ExistingViolater(@NotNull @InstanceOfAny(ExistingRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** 存在属性判断 **/
        final Set<JsonArray> expectes = this.preparedExpected();
        final Set<String> fields = habitus.fields();
        /** 遍历集合，一条一条判断 **/
        AbstractSchemaException error = null;
        for (final JsonArray item : expectes) {
            // 每一个item遍历计算一条Rule，全部包含才为true，只要了有个属性不存在就false
            final int size = item.size();
            for (int idx = 0; idx < size; idx++) {
                final Object value = item.getValue(idx);
                // 必须同时存在才行，既然有item则已经过滤了item为空的清空，一旦判断则必须存在
                if (!fields.contains(value)) {
                    error = new OptionalAttrMorEException(getClass(), item.getString(idx), "Existing");
                    break;
                }
            }
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Set<JsonArray> preparedExpected() {
        final JsonArray expectes = rule.getRule().getJsonArray(R_VALUE);
        final Set<JsonArray> retSet = new HashSet<>();
        final int size = expectes.size();
        for (int idx = 0; idx < size; idx++) {
            final Object item = expectes.getValue(idx);
            if (JsonArray.class == item.getClass()) {
                final JsonArray value = expectes.getJsonArray(idx);
                // 过滤空Rule的情况
                if (Constants.ZERO < value.size()) {
                    retSet.add(value);
                }
            }
        }
        return retSet;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
