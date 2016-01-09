package com.prayer.facade.dao.metadata;

import java.io.Serializable;
import java.util.List;

import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.facade.dao.JdbcConnection;

/**
 * 
 * @author Lang
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public interface TemplateDao<T, ID extends Serializable> {    // NOPMD
    /**
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    List<T> insert(T... entity) throws AbstractTransactionException;
    /**
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    T update(T entity) throws AbstractTransactionException;

    /**
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    boolean deleteById(ID uniqueId) throws AbstractTransactionException;

    /**
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    T getById(ID uniqueId);

    /**
     * 
     * @return
     */
    List<T> getAll();
    /**
     * 
     * @param index 从1开始，第几页
     * @param size
     * @param orderBy
     * @return
     */
    List<T> getByPage(int index, int size, String orderBy);

    /**
     * 
     * @return
     */
    boolean clear() throws AbstractTransactionException;
    /**
     * 从底层获取H2的连接信息
     * @param identifier
     * @return
     */
    JdbcConnection getContext(String identifier);
}
