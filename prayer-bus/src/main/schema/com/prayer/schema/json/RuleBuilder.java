package com.prayer.schema.json;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.reflection.Instance.clazz;
import static com.prayer.util.reflection.Instance.instance;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.DBColumnRule;
import com.prayer.schema.json.rule.DBConstraintRule;
import com.prayer.schema.json.rule.DBTableRule;
import com.prayer.schema.json.rule.DiffRule;
import com.prayer.schema.json.rule.ExcludeRule;
import com.prayer.schema.json.rule.ExistingRule;
import com.prayer.schema.json.rule.InRule;
import com.prayer.schema.json.rule.JTypeRule;
import com.prayer.schema.json.rule.NotInRule;
import com.prayer.schema.json.rule.PatternRule;
import com.prayer.schema.json.rule.RequiredRule;
import com.prayer.schema.json.rule.UnsupportRule;
import com.prayer.util.io.PropertyKit;

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
public final class RuleBuilder {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleBuilder.class);
    /** Bind Map **/
    private static final ConcurrentMap<Class<?>, Class<?>> BIND_MAP = new ConcurrentHashMap<>();
    /** 设置BIND_MAP **/
    private static final PropertyKit LOADER = new PropertyKit(Resources.SYS_RULES + "bind.properties");
    /** Rule包信息 **/
    private static final String PKG_RULE = "com.prayer.schema.json.rule";
    /** Violater包信息 **/
    private static final String PKG_VIOLATER = "com.prayer.schema.json.violater";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 填充系统中的映射关系 **/
    static {
        final Properties bindData = LOADER.getProp();
        /**
         * 填充绑定关系
         */
        for (final Object item : bindData.keySet()) {
            if (null != item) {
                // 先提取Properties
                String keyStr = item.toString();
                String valueStr = bindData.getProperty(keyStr);
                // 处理Key
                if (keyStr.indexOf(Symbol.DOT) < 0) {
                    keyStr = PKG_RULE + Symbol.DOT + keyStr;
                }
                final Class<?> key = clazz(keyStr);
                // 处理Value
                if (valueStr.indexOf(Symbol.DOT) < 0) {
                    valueStr = PKG_VIOLATER + Symbol.DOT + valueStr;
                }
                final Class<?> value = clazz(valueStr);
                if (null != key && null != value) {
                    debug(LOGGER, "[BIND] " + key.getName() + " -> " + value.getName());
                    BIND_MAP.put(key, value);
                }
            }
        }
    }

    // ~ Static Methods ======================================

    /**
     * 
     * @param rule
     * @return
     */
    public static Violater bind(@NotNull final Rule rule) {
        final Class<?> clazz = rule.getClass();
        Violater violater = null;
        final Class<?> target = BIND_MAP.get(clazz);
        if (null != target) {
            violater = instance(target, rule);
        }
        return violater;
    }

    // ~ Rule Building =======================================
    /**
     * E10001
     * 
     * @param file
     * @return
     */
    public static Rule required(@NotNull @NotEmpty @NotBlank final String file) {
        return RequiredRule.create(file);
    }

    /**
     * E10002
     * 
     * @param file
     * @return
     */
    public static Rule jtype(@NotNull @NotEmpty @NotBlank final String file) {
        return JTypeRule.create(file);
    }

    /**
     * E10017
     * 
     * @param file
     * @return
     */
    public static Rule unsupport(@NotNull @NotEmpty @NotBlank final String file) {
        return UnsupportRule.create(file);
    }

    /**
     * E10003
     * 
     * @param file
     * @return
     */
    public static Rule pattern(@NotNull @NotEmpty @NotBlank final String file) {
        return PatternRule.create(file);
    }
    /**
     * E10004
     * @param file
     * @return
     */
    public static Rule exclude(@NotNull @NotEmpty @NotBlank final String file){
        return ExcludeRule.create(file);
    }
    
    /**
     * E10004
     * @param file
     * @return
     */
    public static Rule existing(@NotNull @NotEmpty @NotBlank final String file){
        return ExistingRule.create(file);
    }
    /**
     * E10005
     * @param file
     * @return
     */
    public static Rule in(@NotNull @NotEmpty @NotBlank final String file){
        return InRule.create(file);
    }
    /**
     * E10005
     * @param file
     * @return
     */
    public static Rule notin(@NotNull @NotEmpty @NotBlank final String file){
        return NotInRule.create(file);
    }
    /**
     * E10020
     * @param file
     * @return
     */
    public static Rule diff(@NotNull @NotEmpty @NotBlank final String file){
        return DiffRule.create(file);
    }
    
    /**
     * E10027
     * @param file
     * @return
     */
    public static Rule dbtable(@NotNull @NotEmpty @NotBlank final String file){
        return DBTableRule.create(file);
    }
    /**
     * E10028
     * @param file
     * @return
     */
    public static Rule dbcolumn(@NotNull @NotEmpty @NotBlank final String file){
        return DBColumnRule.create(file);
    }
    /**
     * E10029
     * @param file
     * @return
     */
    public static Rule dbconstraint(@NotNull @NotEmpty @NotBlank final String file){
        return DBConstraintRule.create(file);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private RuleBuilder() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
