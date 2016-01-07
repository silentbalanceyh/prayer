package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author Lang
 *
 */
public interface H2TMapper<T, ID> {    // NOPMD
    /**
     * 
     * @param entity
     * @return
     */
    int insert(T entity);
    /**
     * 
     * @param entities
     * @return
     */
    int batchInsert(List<T> entities);
    /**
     * 
     * @param entity
     * @return
     */
    int update(T entity);
    /**
     * 
     * @param uniqueId
     * @return
     */
    boolean deleteById(@Param("uniqueId") ID uniqueId);
    /**
     * 
     * @param ids
     * @return
     */
    boolean batchDelete(List<ID> ids);
    /**
     * 
     * @param uniqueId
     * @return
     */
    T selectById(@Param("uniqueId") ID uniqueId);
    /**
     * 
     * @return
     */
    List<T> selectAll();
    /**
     * 
     * @param order
     * @param size
     * @param start
     * @return
     */
    List<T> selectByPage(@Param("order") String order, @Param("size") int size, @Param("start") int start);
    /**
     * 
     * @return
     */
    boolean purgeData();
}
