package com.prayer.schema.json.rule;

import com.prayer.facade.schema.rule.Rule;
import com.prayer.fantasm.schema.AbstractRule;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 不支持属性表
 * 
 * @author Lang
 *
 */
@Guarded
public final class UnsupportRule extends AbstractRule implements Rule {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Rule create(@NotNull @NotBlank @NotEmpty final String rule) {
        return new UnsupportRule(rule);
    }
    // ~ Constructors ========================================
    /** 私有构造函数 **/
    private UnsupportRule(final String rule) {
        super(rule, Names.RULE_OPT);
        /** 除了读取可选的属性以外，还需要将Required的加入，这样才可以计算Supported **/
        final JsonArray required = readRule(rule, Names.RULE_REQ).getJsonArray(R_VALUE);
        for (final Object item : required) {
            if (item.getClass() == String.class) {
                final JsonArray addAttr = this.getRule();
                addAttr.add(item);
            }
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
