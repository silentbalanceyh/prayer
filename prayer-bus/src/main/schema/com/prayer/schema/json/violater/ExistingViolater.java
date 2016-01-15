package com.prayer.schema.json.violater;

import java.util.Set;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
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
public final class ExistingViolater extends AbstractViolater implements Violater {
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
        final Set<JsonArray> expectes = this.preparedArraySet(this.rule);
        final JsonArray fields = habitus.fields();
        /** 遍历集合，一条一条判断 **/
        AbstractSchemaException error = null;
        for (final JsonArray expected : expectes) {
            /**
             * fields中必须包含所有expect的元素
             */
            final Object attr = VHelper.calculate(fields, expected, VCondition::nin);
            if (null != attr) {
                final Object[] arguments = new Object[] { this.rule.position() + " -> " + attr, "Missing" };
                error = this.error(rule, arguments, null);
                break;
            }
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
