package com.prayer.plugin.mapper;

import java.util.List;

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
    boolean deleteById(ID uniqueId);
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
    T selectById(ID uniqueId);
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
    List<T> selectByPage(String order, int size, int start);
    /**
     * 
     * @return
     */
    boolean purgeData();
}
