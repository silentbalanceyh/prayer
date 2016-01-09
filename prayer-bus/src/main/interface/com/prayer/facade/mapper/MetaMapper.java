package com.prayer.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.database.PEMeta;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface MetaMapper extends H2TMapper<PEMeta, String> { // NOPMD

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
    PEMeta selectByModel(@Param("namespace") String namespace, @Param("name") String name);

    /**
     * 
     * @param globalId
     * @return
     */
    PEMeta selectByGlobalId(@Param("globalId") String globalId);
}
