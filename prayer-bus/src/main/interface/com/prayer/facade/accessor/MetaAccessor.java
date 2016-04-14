package com.prayer.facade.accessor;

import java.io.Serializable;
import java.util.List;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractTransactionException;

/**
 * 泛型的Mapper，用于访问元数据所有的IBatisAccessor
 * 
 * @author Lang
 *
 * @param <T>
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface MetaAccessor { // NOPMD
    /**
     * 单条记录插入
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.WRITE)
    Entity insert(Entity entity) throws AbstractTransactionException;

    /**
     * 添加Entity实体操作
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    // 1.支持单个Entity的插入
    // 2.支持批量的Entity的插入
    @VertexApi(Api.WRITE)
    List<Entity> insert(Entity... entity) throws AbstractTransactionException;

    /**
     * 单个Entity的更新操作
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.WRITE)
    Entity update(Entity entity) throws AbstractTransactionException;

    /**
     * 删除单个Entity实体
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.WRITE)
    boolean deleteById(Serializable uniqueId) throws AbstractTransactionException;

    /**
     * 批量删除一个Entity
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.WRITE)
    boolean deleteById(Serializable... uniqueId) throws AbstractTransactionException;

    /**
     * 提供WHERE子句，实现动态删除
     * 
     * @param whereClause
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean deleteList(String whereClause) throws AbstractTransactionException;

    /**
     * 删除元数据表中所有的数据
     * 
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean purge() throws AbstractTransactionException;

    // -------------------接口读写分界线-----------------------
    /**
     * 提供WHERE子句，实现动态查询
     * 
     * @param where
     * @return
     */
    @VertexApi(Api.READ)
    List<Entity> queryList(String whereClause);

    /**
     * 按照ID从系统中读取单个Entity
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.READ)
    Entity getById(Serializable uniqueId);

    /**
     * 读取所有的Entity
     * 
     * @return
     */
    @VertexApi(Api.READ)
    List<Entity> getAll();

    /**
     * 分页读取Entity的集合
     * 
     * @param index
     *            从1开始，第几页
     * @param size
     *            每一页的数量
     * @param orderBy
     * @return
     */
    @VertexApi(Api.READ)
    List<Entity> getByPage(int index, int size, String orderBy);

    /**
     * 返回所有的数据的统计
     * 
     * @return
     */
    @VertexApi(Api.READ)
    long count();

    /**
     * 
     * @param content
     * @return
     */
    // -----------------初始化接口-------------------------------
    /**
     * 根据内容初始化
     * 
     * @param content
     * @return
     * @throws AbstractDatabaseException
     */
    boolean initialize(final String file) throws AbstractTransactionException;
}
