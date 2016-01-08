package com.prayer.facade.cache;

import static com.prayer.constant.Accessors.cache;
import static com.prayer.util.Instance.singleton;

/**
 * 
 * @author Lang
 *
 */
public interface Cache {
    /**
     * 当前Cache Manager的名称
     * 
     * @return
     */
    String name();

    /**
     * 将T放入到Cache中
     * 
     * @param key
     * @param cached
     */
    <T> void put(String key, T cached);

    /**
     * 从系统中获取T
     * 
     * @param key
     * @return
     */
    <T> T get(String key);

    class I {
        /**
         * 通过名称获取Cache的实例
         * 
         * @return
         */
        public static Cache instance(final String name) {
            return singleton(cache(), name);
        }
    }
}
