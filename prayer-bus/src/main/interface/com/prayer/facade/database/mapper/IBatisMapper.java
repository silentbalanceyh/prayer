package com.prayer.facade.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.THIRD_PART)
public interface IBatisMapper<T, ID> { // NOPMD
    /**
     * 
     * @param entity
     * @return
     */
    @VertexApi(Api.WRITE)
    int insert(T entity);

    /**
     * 
     * @param entities
     * @return
     */
    @VertexApi(Api.WRITE)
    int batchInsert(List<T> entities);

    /**
     * 
     * @param entity
     * @return
     */
    @VertexApi(Api.WRITE)
    int update(T entity);

    /**
     * 
     * @param uniqueId
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean deleteById(@Param("uniqueId") ID uniqueId);

    /**
     * 
     * @param ids
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean batchDelete(List<ID> ids);

    /**
     * 将目前系统中的数据清除
     * 
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean purge();


    /**
     * 
     * @param whereClause
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean deleteList(@Param("where") String whereClause);
    /**
     * 
     * @param uniqueId
     * @return
     */
    @VertexApi(Api.READ)
    T selectById(@Param("uniqueId") ID uniqueId);

    /**
     * 
     * @return
     */
    @VertexApi(Api.READ)
    List<T> selectAll();

    /**
     * @param category
     *            动态SQL参数，判断元数据信息
     * @param order
     * @param size
     * @param start
     * @return
     */
    @VertexApi(Api.READ)
    List<T> selectByPage(@Param("category") String category, @Param("orderBy") String order, @Param("size") int size,
            @Param("start") int start);

    /**
     * 统计系统中的数据总量
     * 
     * @return
     */
    @VertexApi(Api.READ)
    long count();

    /**
     * 
     * @param whereClause
     * @return
     */
    @VertexApi(Api.READ)
    List<T> queryList(@Param("where") String whereClause);
}
