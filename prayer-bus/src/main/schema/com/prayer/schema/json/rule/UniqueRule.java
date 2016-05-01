package com.prayer.schema.json.rule;

import com.prayer.facade.schema.rule.Rule;
import com.prayer.fantasm.schema.AbstractRule;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 只能出现一次的规则，复杂规则，主要用于Array类型，根据查询条件获取唯一记录，如果出现2次以上则视为越界
 * most + least ( occurs = 1 ) 的组合，但比most和least的规则更为专一和复杂，不替换原始的most和least
 * @author Lang
 *
 */
@Guarded
public final class UniqueRule extends AbstractRule implements Rule {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Rule create(@NotNull @NotEmpty @NotBlank final String rule){
        return new UniqueRule(rule);
    }
    // ~ Constructors ========================================
    /** **/
    private UniqueRule(final String rule){
        super(rule,Names.RULE_UNQ);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
