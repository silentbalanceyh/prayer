package com.prayer.schema.json.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.schema.rule.AbstractRule;
import com.prayer.facade.schema.rule.Rule;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 不支持属性表
 * 
 * @author Lang
 *
 */
@Guarded
public final class UnsupportRule extends AbstractRule implements Rule {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UnsupportRule.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Rule create(final String rule) {
        return new UnsupportRule(rule);
    }
    // ~ Constructors ========================================
    /** 私有构造函数 **/
    @PostValidateThis
    private UnsupportRule(@NotNull @NotBlank @NotEmpty final String rule) {
        super(rule, Names.RULE_OPT);
        /** 除了读取可选的属性以外，还需要将Required的加入，这样才可以计算Supported **/
        final JsonArray required = readRule(rule, Names.RULE_REQ).getJsonArray(R_VALUE);
        for (final Object item : required) {
            if (item.getClass() == String.class) {
                this.getRule().getJsonArray(R_VALUE).add(item);
            }
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @return
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
