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
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.RequiredRule;
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
                final String keyStr = item.toString();
                final Class<?> key = clazz(keyStr);
                final Class<?> value = clazz(bindData.getProperty(keyStr));
                if (null != key && null != value) {
                    debug(LOGGER,"[BIND] " + key.getName() + " -> " + value.getName());
                    BIND_MAP.put(key, value);
                }
            }
        }
    }

    // ~ Static Methods ======================================
    /**
     * 
     * @param file
     * @return
     */
    public static Rule required(@NotNull @NotEmpty @NotBlank final String file) {
        return instance(RequiredRule.class.getName(), file);
    }

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
