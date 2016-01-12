package com.prayer.schema.json.violater;

import java.util.Set;

import com.prayer.constant.Constants;
import com.prayer.exception.schema.UnsupportAttrException;
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
    public UnsupportViolater(@NotNull final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public UnsupportAttrException violate(@NotNull final ObjectHabitus habitus) {
        /**
         * 解析Rule的期望值
         */
        final JsonArray expectes = rule.getRule().getJsonArray(R_VALUE);
        final Set<String> fields = habitus.fields();
        /**
         * 最终返回结果
         */
        UnsupportAttrException error = null;
        for (final Object expected : expectes) {
            fields.remove(expected);
        }
        /**
         * 最终的fields的尺寸应该为0，不可以大于0
         */
        if (Constants.ZERO < fields.size()) {
            error = new UnsupportAttrException(getClass(), fields);
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
