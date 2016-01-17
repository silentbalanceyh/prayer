package com.prayer.schema.json.violater;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.RequiredRule;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * Error 10001: RequiredAttrMissingException
 * 
 * @author Lang
 *
 */
@Guarded
public final class RequiredViolater extends AbstractViolater implements Violater {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Violater和Rule绑定死，一个Vialoter只能有一个Rule **/
    @NotNull
    private transient final Rule rule;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public RequiredViolater(@NotNull @InstanceOfAny(RequiredRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 验证当前一条Rule的信息
     */
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /**
         * 解析Rule获取期望值
         */
        final JsonArray expectes = rule.getRule();
        final JsonArray fields = habitus.fields();
        /**
         * 最终返回值
         */
        AbstractSchemaException error = null;
        /**
         * fields中必须包含所有expectes的元素，即required的基础条件
         */
        final Object attr = VHelper.calculate(fields, expectes, VCondition::nin);
        if (null != attr) {
            final Object[] arguments = new Object[] { this.rule.position() + " -> " + attr };
            error = this.error(rule, arguments, null);
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
