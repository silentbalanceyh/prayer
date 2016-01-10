package com.prayer.facade.accessor;

import java.io.Serializable;
import java.util.List;

import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.facade.entity.Entity;

/**
 * 泛型的Mapper，用于访问元数据所有的IBatisAccessor
 * 
 * @author Lang
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public interface MetaAccessor<T extends Entity, ID extends Serializable> { // NOPMD
    /**
     * 添加Entity实体操作
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    // 1.支持单个Entity的插入
    // 2.支持批量的Entity的插入
    List<T> insert(T... entity) throws AbstractTransactionException;

    /**
     * 单个Entity的更新操作
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    T update(T entity) throws AbstractTransactionException;

    /**
     * 删除单个Entity实体
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    boolean deleteById(ID uniqueId) throws AbstractTransactionException;

    /**
     * 按照ID从系统中读取单个Entity
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    T getById(ID uniqueId);

    /**
     * 读取所有的Entity
     * 
     * @return
     */
    List<T> getAll();

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
    List<T> getByPage(int index, int size, String orderBy);

    /**
     * 提供WHERE子句，实现动态查询
     * @param where
     * @return
     */
    List<T> queryList(String whereClause);

    /**
     * 删除元数据表中所有的数据
     * 
     * @return
     */
    boolean purge() throws AbstractTransactionException;
}
