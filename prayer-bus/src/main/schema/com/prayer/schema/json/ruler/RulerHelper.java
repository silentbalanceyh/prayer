package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.RuleConstants;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.JObjectHabitus;
import com.prayer.schema.json.RuleBuilder;

import io.vertx.core.json.JsonObject;
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
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyMost(@NotNull final ArrayHabitus habitus, @NotNull @NotBlank @NotEmpty final String file,
            @NotNull final JsonObject addtional) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.most(file);
        sharedApply(wrapperHabitus(habitus, addtional), rule);
    }

    /**
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyLeast(@NotNull final ArrayHabitus habitus, @NotNull @NotBlank @NotEmpty final String file,
            @NotNull final JsonObject addtional) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.least(file);
        sharedApply(wrapperHabitus(habitus, addtional), rule);
    }

    /**
     * Error10007/10008 -- 检查Duplicated的Column以及Name
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyDuplicated(@NotNull final ArrayHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.duplicated(file);
        sharedApply(wrapperHabitus(habitus, null), rule);
    }

    /**
     * Error10029 -- 数据库中约束对不对
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyDBConstraint(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.dbconstraint(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10028 -- 数据库的某张表中的列是否存在
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyDBColumn(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.dbcolumn(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10027 -- 数据库中表不存在
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyDBTable(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.dbtable(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10020 -- 两个值不可以相等
     * 
     * @param habitus
     * @param file
     */
    public static void applyDiff(@NotNull final ObjectHabitus habitus, @NotNull @NotBlank @NotEmpty final String file)
            throws AbstractSchemaException {
        final Rule rule = RuleBuilder.diff(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10005 -- 值不能在提供的范围内
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyNotIn(@NotNull final ObjectHabitus habitus, @NotNull @NotBlank @NotEmpty final String file)
            throws AbstractSchemaException {
        final Rule rule = RuleBuilder.notin(file);
        sharedApply(habitus, rule);
    }

    /**
     * Error10005 -- 值必须在范围内的验证
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyIn(@NotNull final ObjectHabitus habitus, @NotNull @NotBlank @NotEmpty final String file)
            throws AbstractSchemaException {
        final Rule rule = RuleBuilder.in(file);
        sharedApply(habitus, rule);
    }

    /**
     * 扩展的In方式，多一个参数
     * 
     * @param habitus
     * @param file
     * @param addtional
     * @throws AbstractSchemaException
     */
    public static void applyIn(@NotNull final ObjectHabitus habitus, @NotNull @NotBlank @NotEmpty final String file,
            @NotNull final JsonObject addtional) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.extin(file);
        sharedApply(wrapperHabitus(habitus, addtional), rule);
    }

    /**
     * Error10004 -- 不包含属性验证
     * 
     * @param habitus
     * @param file
     * @throws AbstractSchemaException
     */
    public static void applyExclude(@NotNull final ObjectHabitus habitus,
            @NotNull @NotBlank @NotEmpty final String file) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.exclude(file);
        sharedApply(habitus, rule);
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

    private static ObjectHabitus wrapperHabitus(final ObjectHabitus habitus, final JsonObject addtional) {
        // 内部初始化
        final JsonObject data = new JsonObject();
        data.put(RuleConstants.R_DATA, habitus.getRaw());
        if (null != addtional) {
            data.put(RuleConstants.R_ADDT, addtional);
        }
        return new JObjectHabitus(data);
    }

    private static ObjectHabitus wrapperHabitus(final ArrayHabitus habitus, final JsonObject addtional) {
        // 内部初始化
        final JsonObject data = new JsonObject();
        data.put(RuleConstants.R_DATA, habitus.getRaw());
        if (null != addtional) {
            data.put(RuleConstants.R_ADDT, addtional);
        }
        return new JObjectHabitus(data);
    }

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
