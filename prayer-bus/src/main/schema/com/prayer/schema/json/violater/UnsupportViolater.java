package com.prayer.schema.json.violater;

import static com.prayer.util.Converter.toStr;

import java.util.Set;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.UnsupportAttrException;
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
 * @author Lang
 *
 */
@Guarded
public final class UnsupportViolater implements Violater {
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
        final JsonArray expectes = rule.getRule().getJsonArray(R_VALUE);
        final Set<String> fields = habitus.fields();
        /**
         * 最终返回结果
         */
        AbstractSchemaException error = null;
        for (final Object expected : expectes) {
            fields.remove(expected);
        }
        /**
         * 最终的fields的尺寸应该为0，不可以大于0
         */
        if (Constants.ZERO < fields.size()) {
            error = new UnsupportAttrException(getClass(), this.rule.position() + " -> " + toStr(fields));
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
