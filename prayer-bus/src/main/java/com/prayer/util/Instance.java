package com.prayer.util;

import static com.prayer.util.Log.jvmError;
import static com.prayer.util.Log.ovalError;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.prayer.util.cv.MemoryPool;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.guard.Guarded;

/**
 * 不启用OVal，因为反射部分需要知道的是实际对象的约束防御异常，而不是当前的
 * 
 * @author Lang
 * @see
 */
@SuppressWarnings("unchecked")
@Guarded
public final class Instance { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Instance.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 支持带Pool模式的单例
     * 
     * @param objectPool
     * @param key
     * @param className
     * @param params
     * @return
     */
    public static <T> T reservoir(@NotNull final ConcurrentMap<String, T> objectPool, final String key,
            @NotNull @NotBlank @NotEmpty final String className, final Object... params) {
        T ret = objectPool.get(null == key ? "" : key);
        if (null == ret) {
            ret = instance(className, params);
            if (null != ret) {
                objectPool.put(key, ret);
            }
        }
        return ret;
    }

    /**
     * 支持带Pool模式的单例
     * 
     * @param clazz
     * @param key
     * @param objectPool
     * @param params
     * @return
     */
    public static <T> T reservoir(@NotNull final ConcurrentMap<String, T> objectPool, final String key,
            @NotNull final Class<?> clazz, final Object... params) {
        T ret = objectPool.get(null == key ? "" : key);
        if (null == ret) {
            ret = instance(clazz, params);
            if (null != ret) {
                objectPool.put(key, ret);
            }
        }
        return ret;
    }

    /**
     * 
     * @param clazz
     * @param params
     * @return
     */
    public static <T> T singleton(@NotNull final Class<?> clazz, final Object... params) {
        return (T) reservoir(MemoryPool.POOL_OBJECT, clazz.getName(), clazz, params);
    }

    /**
     * 全环境的singleton单例模式
     * 
     * @param className
     * @param params
     * @return
     */
    public static <T> T singleton(@NotNull @NotBlank @NotEmpty final String className, final Object... params) {
        return (T) reservoir(MemoryPool.POOL_OBJECT, className, className, params);
    }

    /**
     * 构造一个新的实例
     * 
     * @param className
     * @param params
     * @return
     */
    public static <T> T instance(@NotNull @NotBlank @NotEmpty final String className, final Object... params) {
        final Class<?> clazz = clazz(className);
        return instance(clazz, params);
    }

    /**
     * 
     * @param className
     * @return
     */
    public static Class<?> clazz(@NotNull @NotBlank @NotEmpty final String className) {
        Class<?> ret = null;
        try {
            ret = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            jvmError(LOGGER,ex);
        }
        return ret;
    }

    /**
     * 获取所有父类信息
     * 
     * @param className
     * @return
     */
    public static List<Class<?>> parents(@NotNull @NotBlank @NotEmpty final String className) {
        final List<Class<?>> parents = new ArrayList<>();
        final Class<?> current = clazz(className);
        final Class<?> parent = current.getSuperclass();
        if (null != current && parent != Object.class) {
            // 1.添加当前父类
            parents.add(parent);
            // 2.递归添加父类
            parents.addAll(parents(parent.getName()));
        }
        return parents;
    }

    /**
     * 获取所有的接口信息，包括父类接口信息
     * 
     * @param className
     * @return
     */
    public static List<Class<?>> interfaces(@NotNull @NotBlank @NotEmpty final String className) {
        final List<Class<?>> interfaces = new ArrayList<>();
        final Class<?> current = clazz(className);
        final Class<?>[] superInter = current.getInterfaces();
        interfaces.addAll(Arrays.asList(superInter));
        for (final Class<?> clazz : superInter) {
            // 2.递归添加
            if (null != clazz) {
                interfaces.addAll(interfaces(clazz.getName()));
            }
        }
        return interfaces;
    }

    /**
     * 获取instance中field的信息
     * 
     * @param instance
     * @param name
     * @return
     */
    public static <T extends Object> T field(@NotNull final Object instance,
            @NotNull @NotBlank @NotEmpty final String name) {
        T ret = null;
        final Class<?> clazz = instance.getClass();
        try {
            final Field field = clazz.getDeclaredField(name);
            // 打开属性访问标记
            if (null != field) {
                field.setAccessible(true);
            }
            ret = (T) field.get(instance);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            jvmError(LOGGER,ex);
        }
        return ret;
    }

    /**
     * 
     * @param instance
     * @param name
     * @param value
     */
    public static void field(@NotNull final Object instance, @NotNull @NotBlank @NotEmpty final String name,
            @NotNull final Serializable value) {
        final Class<?> clazz = instance.getClass();
        Field field;
        try {
            field = clazz.getDeclaredField(name);
            // 打开访问属性标记
            if (null != field && !field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(instance, value);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            jvmError(LOGGER,ex);
        }
    }

    // ~ Constructors ========================================
    private Instance() {
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    /**
     * 
     * @param clazz
     * @param params
     * @return
     */
    private static <T> T instance(final Class<?> clazz, final Object... params) {
        T ret = null;
        try {
            if (0 == params.length) {
                ret = construct(clazz);
            } else {
                ret = construct(clazz, params);
            }
        } catch (ConstraintsViolatedException ex) { // NOPMD
            ovalError(LOGGER, ex);
            throw ex;
        } catch (SecurityException ex) {
            jvmError(LOGGER,ex);
        }
        return ret;
    }

    private static <T> T construct(final Class<?> clazz, final Object... params) {
        T ret = null;
        if (0 < params.length) {
            final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (final Constructor<?> constructor : constructors) {
                // 参数长度不匹配，直接略过
                if (params.length != constructor.getParameterTypes().length) {
                    continue;
                }
                ret = construct(constructor, params);
                if (null != ret) {
                    break;
                }
            }
        } else {
            final ConstructorAccess<?> access = ConstructorAccess.get(clazz);
            ret = (T) access.newInstance();
        }
        return ret;
    }

    private static <T> T construct(final Constructor<?> constructor, final Object... params) {
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        T ret = null;
        try {
            ret = (T) (constructor.newInstance(params));
        } catch (InvocationTargetException ex) {
            if (ex.getTargetException() instanceof ConstraintsViolatedException) {
                final ConstraintsViolatedException error = (ConstraintsViolatedException) ex.getTargetException();
                ovalError(LOGGER, error);
                throw error;
            }
        } catch (IllegalArgumentException ex) {
            jvmError(LOGGER,ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            jvmError(LOGGER,ex);
        }
        return ret;
    }
    // ~ hashCode,equals,toString ============================
}