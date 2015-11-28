package com.prayer.facade.mapper;

import com.prayer.model.h2.schema.MetaModel;

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
    boolean deleteByModel(String namespace, String name);

    /**
     * 根据Meta的namespace和name获取Meta记录
     * 
     * @param namespace
     * @param name
     * @return
     */
    MetaModel selectByModel(String namespace, String name);

    /**
     * 
     * @param globalId
     * @return
     */
    MetaModel selectByGlobalId(String globalId);
}
