package com.prayer.facade.metadata.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author Lang
 *
 */
public interface IBatisMapper<T, ID> { // NOPMD
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
     * @param category 动态SQL参数，判断元数据信息
     * @param order
     * @param size
     * @param start
     * @return
     */
    List<T> selectByPage(@Param("category") String category, @Param("orderBy") String order, @Param("size") int size,
            @Param("start") int start);

    /**
     * 将目前系统中的数据清除
     * 
     * @return
     */
    boolean purge();

    /**
     * 统计系统中的数据总量
     * 
     * @return
     */
    long count();

    /**
     * 
     * @param whereClause
     * @return
     */
    List<T> queryList(@Param("where") String whereClause);
}
