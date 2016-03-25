package com.prayer.schema.json.violater;

import com.prayer.builder.util.SqlTypes;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.schema.AbstractViolater;
import com.prayer.schema.json.rule.MappingRule;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 固定验证，数据库类型验证、数据库Mapping验证，不可重用的特殊Rule
 * 
 * @author Lang
 *
 */
@Guarded
public final class MappingViolater extends AbstractViolater implements Violater {
    // ~ Static Fields =======================================
    /** **/
    private static final String COL_TYPE_KEY = "columnType";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final Rule rule;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public MappingViolater(@NotNull @InstanceOfAny(MappingRule.class) final Rule rule) {
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
            /** 验证columnType中的数据内容是否存在于DB的Type Mapping中 **/
            final String value = SqlTypes.get(habitus.get(COL_TYPE_KEY));
            final JsonArray types = SqlTypes.types();
            boolean ret = VCondition.nin(value, types);
            if (ret) {
                final JsonObject addtional = new JsonObject();
                /** 如果value为null获取原始值 **/
                if (null == value) {
                    addtional.put(COL_TYPE_KEY, habitus.get(COL_TYPE_KEY));
                } else {
                    addtional.put(COL_TYPE_KEY, value);
                }
                error = this.error(rule, new Object[] {}, addtional);
            }
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
