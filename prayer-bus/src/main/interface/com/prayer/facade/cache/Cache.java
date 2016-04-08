package com.prayer.facade.cache;

import static com.prayer.constant.Accessors.cache;
import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Cache {
    /**
     * 当前Cache Manager的名称
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String name();

    /**
     * 将T放入到Cache中
     * 
     * @param key
     * @param cached
     */

    @VertexApi(Api.WRITE)
    <T> void put(String key, T cached);

    /**
     * 从系统中获取T
     * 
     * @param key
     * @return
     */

    @VertexApi(Api.READ)
    <T> T get(String key);

    /** 不属于Interface **/
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
