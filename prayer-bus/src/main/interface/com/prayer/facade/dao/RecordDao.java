package com.prayer.facade.dao;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.bus.Pager;

/**
 * 
 * @author Lang
 *
 */
public interface RecordDao {
    /**
     * 
     * @param record
     * @return
     */
    Record insert(Record record) throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    Record update(Record record) throws AbstractDatabaseException;

    /**
     * 主键策略：GUID, ASSIGNED, INCREMENT
     * 
     * @param record
     * @param uniqueId
     * @return
     */
    Record selectById(Record record, Value<?> uniqueId) throws AbstractDatabaseException;

    /**
     * 主键策略：COLLECTION
     * 
     * @param record
     * @param uniqueIds
     * @return
     */
    Record selectById(Record record, ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @param uniqueId
     * @return
     */
    boolean delete(Record record) throws AbstractDatabaseException;
    /**
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    boolean purge(Record record) throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @param columns
     * @param filter
     * @return
     * @throws AbstractDatabaseException
     */
    List<Record> queryByFilter(Record record, String[] columns, List<Value<?>> params, Expression filters)
            throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @param columns
     * @param params
     * @param filters
     * @param orders
     * @return
     * @throws AbstractDatabaseException
     */
    List<Record> queryByFilter(Record record, String[] columns, List<Value<?>> params, Expression filters,
            OrderBy orders) throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @param columns
     * @param params
     * @param filters
     * @param orders
     * @return
     * @throws AbstractDatabaseException
     */
    ConcurrentMap<Long, List<Record>> queryByPage(Record record, String[] columns, List<Value<?>> params,
            Expression filters, OrderBy orders, Pager pager) throws AbstractDatabaseException;
}
