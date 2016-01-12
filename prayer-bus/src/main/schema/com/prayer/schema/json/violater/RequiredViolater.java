package com.prayer.schema.json.violater;

import java.util.Set;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RequiredViolater implements Violater {
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
    public RequiredViolater(@NotNull final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 验证当前一条Rule的信息
     */
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        final JsonObject rules = rule.getRule();
        final JsonArray expectes = rules.getJsonArray(R_VALUE);
        final Set<String> fields = habitus.fields();
        /** 
         * 最终返回值
         */
        AbstractSchemaException error = null;
        for (final Object expected : expectes) {
            /**
             * 只检查字符串类型，因为是Required Missing
             */
            if (null != expected && String.class == expected.getClass()) {
                final String attr = expected.toString();
                if (!fields.contains(attr)) {
                    error = new RequiredAttrMissingException(getClass(), attr);
                }
            }
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
