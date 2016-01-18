package com.prayer.schema.json.violater;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.UnsupportRule;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * Error 10017 ：UnsupportAttrException
 * 
 * @author Lang
 *
 */
@Guarded
public final class UnsupportViolater extends AbstractViolater implements Violater {
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
    public UnsupportViolater(@NotNull @InstanceOfAny(UnsupportRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /**
         * 解析Rule的期望值
         */
        final JsonArray expectes = rule.getRule();
        final JsonArray fields = habitus.fields();
        /**
         * 最终返回结果
         */
        AbstractSchemaException error = null;
        /**
         * expectes为所有支持的元素，则必须包含所有fields中出现过的元素
         */
        final Object attr = VExecutor.execute(expectes, fields, VCondition::nin);
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
