package com.prayer.model.cache;

import com.prayer.constant.Accessors;
import com.prayer.facade.cache.Cache;
import com.prayer.util.reflection.Instance;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 生成Cache用的Builder信息
 * 
 * @author Lang
 *
 */
@Guarded
public final class CacheBuilder {
    // ~ Static Fields =======================================
    /** 顶级Cache，Global直接使用 **/
    private static final Cache GLOBAL_CACHE = Instance.instance(Accessors.cache());

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 构建Cache引用 **/
    public static Cache build(@NotNull final Class<?> clazz) {
        final String name = clazz.getName();
        Cache instance = GLOBAL_CACHE.get(name);
        if (null == instance) {
            instance = Instance.instance(Accessors.cache());
            GLOBAL_CACHE.put(name, instance);
        }
        return instance;
    }

    /** 全局尺寸 **/
    public static int size() {
        return GLOBAL_CACHE.size();
    }

    /** 全局缓存清除 **/
    public static void clean() {
        GLOBAL_CACHE.clean();
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
