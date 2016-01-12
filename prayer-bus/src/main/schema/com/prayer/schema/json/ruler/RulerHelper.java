package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.RuleBuilder;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class RulerHelper {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * Error10004 -- 不包含属性验证
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyExclude(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.exclude(file);
        sharedApply(habitus,rule);
    }

    /**
     * Error10004 -- 必须同时存在的属性
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyExisting(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.existing(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10003 -- 值是否符合Pattern
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyPattern(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.pattern(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10017 -- 不支持的属性的操作
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyUnsupport(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.unsupport(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10002 -- Json Type的验证
     * 
     * @param habitus
     * @param file
     */
    public static void applyJType(@NotNull final ObjectHabitus habitus, @NotNull @NotBlank @NotEmpty final String file)
            throws AbstractSchemaException {
        final Rule rule = RuleBuilder.jtype(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10001 -- Required的验证
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyRequired(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.required(file);
        sharedApply(habitus, rule);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 
     * @param habitus
     * @param rule
     * @throws AbstractSchemaException
     */
    private static void sharedApply(final ObjectHabitus habitus, final Rule rule) throws AbstractSchemaException {
        // Reset创建数据拷贝，执行数据的时候需要使用
        habitus.reset();
        final Violater violater = RuleBuilder.bind(rule);
        AbstractSchemaException error = violater.violate(habitus);
        if (null != error) {
            throw error;
        }
    }

    private RulerHelper() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
