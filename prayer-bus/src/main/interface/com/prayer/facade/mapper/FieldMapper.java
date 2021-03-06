package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.database.FieldModel;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface FieldMapper extends H2TMapper<FieldModel, String> {
    /**
     * 根据Field的metaId删除记录
     * 
     * @param metaId
     * @return
     */
    boolean deleteByMeta(@Param("metaId") String metaId);

    /**
     * 根据Field的metaId获取Field记录
     * 
     * @param metaId
     * @return
     */
    List<FieldModel> selectByMeta(@Param("metaId") String metaId);
}
