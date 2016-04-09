package com.prayer.model.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.cache.Cache;

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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MemoryCache implements Cache {
    // ~ Static Fields =======================================
    /** **/
    private transient ConcurrentMap<String, Object> CACHE = new ConcurrentHashMap();
    // ~ Instance Fields =====================================

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Memory方式，即HashMap中操作
     */
    @Override
    public <V> V get(@NotNull @NotBlank @NotEmpty final String key) {
        return (V) CACHE.get(key);
    }

    /**
     * Memory方式，将内容放到Cache中
     */
    @Override
    public <V> void put(@NotNull @NotBlank @NotEmpty final String key, @NotNull final V value) {
        CACHE.put(key, value);
    }

    /**
     * 返回当前Cache的尺寸
     */
    @Override
    public int size() {
        return CACHE.size();
    }

    /**
     * 
     */
    @Override
    public void clean() {
        CACHE.clear();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
