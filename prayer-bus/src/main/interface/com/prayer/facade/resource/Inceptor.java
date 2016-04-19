package com.prayer.facade.resource;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 公共接口，主要用于配置数据读取
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Inceptor {
    /**
     * 读取Long类型的数据
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.READ)
    long getLong(String key);

    /**
     * 读取Int类型的数据
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.READ)
    int getInt(String key);

    /**
     * 读取Boolean类型的数据
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.READ)
    boolean getBoolean(String key);

    /**
     * 读取String类型的数据
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.READ)
    String getString(String key);

    /**
     * 读取Array类型的数据
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.READ)
    String[] getArray(String key);

    /**
     * 读取一个实例的类信息
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.READ)
    Class<?> getClass(String key);
    /**
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String getFile();
    /**
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.READ)
    boolean contains(String key);
}
