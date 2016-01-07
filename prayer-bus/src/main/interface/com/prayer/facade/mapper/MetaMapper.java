package com.prayer.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.database.MetaModel;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface MetaMapper extends H2TMapper<MetaModel, String> { // NOPMD

    /**
     * 根据Meta的namespace和name删除Meta记录
     * 
     * @param namespace
     * @param name
     * @return
     */
    boolean deleteByModel(@Param("namespace") String namespace, @Param("name") String name);

    /**
     * 根据Meta的namespace和name获取Meta记录
     * 
     * @param namespace
     * @param name
     * @return
     */
    MetaModel selectByModel(@Param("namespace") String namespace, @Param("name") String name);

    /**
     * 
     * @param globalId
     * @return
     */
    MetaModel selectByGlobalId(@Param("globalId") String globalId);
}
