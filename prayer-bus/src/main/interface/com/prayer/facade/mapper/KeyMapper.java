package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.database.KeyModel;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface KeyMapper extends H2TMapper<KeyModel, String> {
    /**
     * 根据Meta的metaId删除Key记录
     * 
     * @param metaId
     * @return
     */
    boolean deleteByMeta(@Param("metaId") String metaId);

    /**
     * 根据Meta的metaId获取Key记录的集合
     * 
     * @param metaId
     * @return
     */
    List<KeyModel> selectByMeta(@Param("metaId") String metaId);
}
