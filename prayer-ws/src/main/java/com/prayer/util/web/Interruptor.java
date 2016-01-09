package com.prayer.util.web;

import static com.prayer.util.debug.Log.peError;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractWebException;
import com.prayer.exception.web.UCAConfigErrorException;
import com.prayer.exception.web.UCAConfigMissingException;
import com.prayer.exception.web.UCAInvalidException;
import com.prayer.exception.web.UCANotFoundException;
import com.prayer.util.reflection.Instance;

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
public final class Interruptor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Interruptor.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 检查配置中是否丢失
     * 
     * @param clazz
     * @param name
     * @param config
     * @param key
     * @throws AbstractWebException
     */
    public static void interruptRequired(@NotNull final Class<?> clazz, @NotNull @NotBlank @NotEmpty final String name,
            @NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String key)
                    throws AbstractWebException {
        if (!config.containsKey(key)) {
            final AbstractWebException error = new UCAConfigMissingException(clazz, name, clazz.getName(), key);
            peError(LOGGER, error);
            throw error;
        }
    }

    /**
     * 检查配置中的String类型信息配置信息
     * 
     * @param clazz
     * @param name
     * @param config
     * @param key
     * @throws AbstractWebException
     */
    public static void interruptStringConfig(@NotNull final Class<?> clazz,
            @NotNull @NotBlank @NotEmpty final String name, @NotNull final JsonObject config,
            @NotNull @NotBlank @NotEmpty final String key) throws AbstractWebException {
        final String retStr = Extractor.getString(config, key);
        if (null == retStr) {
            final AbstractWebException error = new UCAConfigErrorException(clazz, name, clazz.getName(),
                    key + " = " + config.getString(key));
            peError(LOGGER, error);
            throw error;
        }
    }

    /**
     * 检查配置中的Number类型信息配置信息
     * 
     * @param clazz
     * @param name
     * @param config
     * @param key
     * @throws AbstractWebException
     */
    public static void interruptNumberConfig(@NotNull final Class<?> clazz,
            @NotNull @NotBlank @NotEmpty final String name, @NotNull final JsonObject config,
            @NotNull @NotBlank @NotEmpty final String key) throws AbstractWebException {
        final Integer retNum = Extractor.getNumber(config, key);
        if (null == retNum) {
            final AbstractWebException error = new UCAConfigErrorException(clazz, name, clazz.getName(),
                    key + " = " + config.getString(key));
            peError(LOGGER, error);
            throw error;
        }
    }

    /**
     * 检查一个类是否存在
     * 
     * @param clazz
     * @param className
     * @param component
     * @throws AbstractWebException
     */
    public static void interruptClass(@NotNull final Class<?> clazz,
            @NotNull @NotBlank @NotEmpty final String className, @NotNull @NotBlank @NotEmpty final String component)
                    throws AbstractWebException {
        final Class<?> targetClass = Instance.clazz(className);
        if (null == targetClass) {
            final AbstractWebException error = new UCANotFoundException(clazz, className);
            peError(LOGGER, error);
            throw error;
        }
    }

    /**
     * 检查父类信息
     * 
     * @param clazz
     * @param className
     * @param componentCls
     * @param interfaceCls
     * @throws AbstractWebException
     */
    public static void interruptExtends(@NotNull final Class<?> clazz,
            @NotNull @NotBlank @NotEmpty final String className, @NotNull final Class<?> componentCls,
            final Class<?> interfaceCls) throws AbstractWebException {
        final List<Class<?>> parents = Instance.parents(className);
        boolean flag = false;
        for (final Class<?> item : parents) {
            if (item == componentCls) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            AbstractWebException error = null;
            // 需要检查接口
            if (null != interfaceCls) {
                try {
                    interruptImplements(clazz, className, interfaceCls);
                } catch (AbstractWebException ex) {
                    peError(LOGGER, error);
                    error = ex;
                }
            }
            if (null != error) {
                throw error;
            }
        }
    }

    /**
     * 检查接口信息
     * 
     * @param clazz
     * @param className
     * @param component
     * @throws AbstractWebException
     */
    public static void interruptImplements(@NotNull final Class<?> clazz,
            @NotNull @NotBlank @NotEmpty final String className, @NotNull final Class<?> componentCls)
                    throws AbstractWebException {
        final List<Class<?>> interfaces = Instance.interfaces(className);
        boolean flag = false;
        for (final Class<?> item : interfaces) {
            if (item == componentCls) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            final AbstractWebException error = new UCAInvalidException(clazz, className, componentCls.getName());
            peError(LOGGER, error);
            throw error;
        }
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Interruptor() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
