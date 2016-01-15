package com.prayer.base.schema;

import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.reservoir;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Accessors;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.facade.fun.schema.Extractor;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.RuleConstants;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.schema.json.violater.VDatabase;
import com.prayer.util.debug.Log;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;

/**
 * 抽象Violater，提供非规则辅助函数，规则辅助函数使用VHelper去完成
 * 
 * @author Lang
 *
 */
public abstract class AbstractViolater {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractViolater.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    // ~ Methods =============================================
    /**
     * 获取数据库的所有信息
     * 
     * @param rule
     * @param habitus
     * @return
     */
    protected List<VDatabase> preparedDatabase(final Rule rule, final ObjectHabitus habitus) {
        final JsonArray expectes = rule.getRule().getJsonArray(RuleConstants.R_VALUE);
        final List<VDatabase> databases = new ArrayList<>();
        for (final Object value : expectes) {
            if (null != value && JsonObject.class == value.getClass()) {
                final JsonObject config = (JsonObject) value;
                databases.add(VDatabase.create(habitus, config));
            }
        }
        return databases;
    }

    /**
     * 双数组格式: [ ["A","B","C"] ]
     * 
     * @param rule
     * @return
     */
    protected Set<JsonArray> preparedArraySet(final Rule rule) {
        final JsonArray expectes = rule.getRule().getJsonArray(RuleConstants.R_VALUE);
        final Set<JsonArray> ret = new HashSet<>();
        final int size = expectes.size();
        for (int idx = 0; idx < size; idx++) {
            final Object item = expectes.getValue(idx);
            if (null != item && JsonArray.class == item.getClass()) {
                final JsonArray value = expectes.getJsonArray(idx);
                if (!value.isEmpty()) {
                    ret.add(value);
                }
            }
        }
        return ret;
    }

    /**
     * 将JsonObject类型的values直接提取成Map提供给系统使用
     * 
     * @param rule
     * @param fun
     * @return
     */
    protected <T> ConcurrentMap<String, T> preparedMap(final Rule rule, final Extractor<T> fun) {
        /** 1.从元数据Rule中读取JsonObject **/
        final JsonObject expectes = rule.getRule().getJsonObject(RuleConstants.R_VALUE);
        final ConcurrentMap<String, T> retMap = new ConcurrentHashMap<>();
        for (final String field : expectes.fieldNames()) {
            if (StringKit.isNonNil(field)) {
                /** 2.如果可以取到对应的field数据 **/
                final Object value = expectes.getValue(field);
                if (null != value) {
                    if (null != fun) {
                        /** 2.1.使用Format函数直接执行Format **/
                        final T instance = fun.extract(value);
                        if (null != instance) {
                            retMap.put(field, instance);
                        }
                    } else {
                        /** 2.2.直接提取 **/
                        @SuppressWarnings("unchecked")
                        final T instance = (T) value;
                        retMap.put(field, instance);
                    }
                }
            }
        }
        return retMap;
    }

    /**
     * 重载方法，用于不同的Error
     * 
     * @param rule
     * @param arguments
     * @param addtional
     * @return
     */
    protected AbstractSchemaException error(@NotNull final Rule rule, @NotNull Object[] arguments,
            final JsonObject addtional) {
        return this.error(rule, arguments, addtional, null);
    }

    /**
     * 重载方法，用于不同的Error
     * 
     * @param rule
     * @param arguments
     * @param addtional
     * @return
     */
    protected AbstractSchemaException error(@NotNull final Rule rule, @NotNull Object[] arguments,
            final JsonObject addtional, final String field) {
        /** 1.从元数据Rule中读取Error **/
        final JsonObject errorConfig = this.getErrorConfig(rule, field);
        /** 2.提取error配置中的数据 **/
        final String errorCls = "com.prayer.exception.schema" + Symbol.DOT + errorConfig.getString("name");
        /** 3.构造第一参数 **/
        JsonArray preparedArgs = new JsonArray();
        // 1.第一个参数通过getClass()获得，一般为Error的第一参数，所有的异常统一规范
        // 2.其次先添加arguments中存在的参数
        // 3.再判断preparedArgs中是否有值，有值的话，将这些值作为addtional的key对待从addtional中提取参数
        final List<Object> args = new ArrayList<>();
        args.add(getClass());
        /** 4.从ErrorConfig中提取模式 **/
        boolean append = false;
        if (errorConfig.containsKey("append")) {
            append = errorConfig.getBoolean("append");
        }
        if (errorConfig.containsKey("arguments")) {
            /** 3.1.1.如果有arguments参数，则查看append模式，如果为true则先添加arguments函数参数 **/
            if (append) {
                for (final Object argument : arguments) {
                    args.add(argument);
                }
            }
            /** 3.1.2.如果append为false则直接以arguments节点中的参数为准 **/
            preparedArgs = errorConfig.getJsonArray("arguments");
            if (!preparedArgs.isEmpty() && null != addtional) {
                for (final Object preparedArg : preparedArgs) {
                    if (null != preparedArg) {
                        args.add(addtional.getValue(preparedArg.toString()));
                    }
                }
            }
        } else {
            /** 3.2.如果不包含arguments节点，则直接添加arguments函数参数，这个时候addtional失效 **/
            for (final Object argument : arguments) {
                args.add(argument);
            }
        }
        /** 4.参数处理完成过后，直接构造 **/
        return instance(errorCls, args.toArray());
    }

    /**
     * 子类可用，获取DataValidator引用
     * 
     * @return
     */
    protected DataValidator validator() {
        return reservoir(MemoryPool.POOL_VALIDATOR, Resources.DB_CATEGORY, Accessors.validator());
    }

    // ~ Private Methods =====================================
    /**
     * 读取Error的配置
     * 
     * @param rule
     * @param habitus
     * @return
     */
    private JsonObject getErrorConfig(@NotNull final Rule rule, final String field) {
        /**
         * 直接从Rule之下读取error节点，这个时候可以从name中拿到对应信息
         */
        JsonObject config = rule.getRule().getJsonObject(RuleConstants.R_ERROR);
        if (null == config) {
            /**
             * 没有读到的时候，尝试从errors中读取，这个时候不同的规则使用不同的方式读取
             */
            config = rule.getRule().getJsonObject(RuleConstants.R_ERRORS);
            if (null != config) {
                config = config.getJsonObject(field);
                if (null == config) {
                    Log.error(LOGGER, "[ERROR] Error Configuration Missing, please check your rule file first.");
                }
            }
        }
        return config;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
