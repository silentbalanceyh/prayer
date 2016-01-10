package com.prayer.facade.metadata.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.database.PEKey;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface KeyMapper extends IBatisMapper<PEKey, String> {
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
    List<PEKey> selectByMeta(@Param("metaId") String metaId);
}
