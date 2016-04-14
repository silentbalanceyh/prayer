package com.prayer.facade.database.dao;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.business.OrderBy;
import com.prayer.model.business.Pager;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface RecordDao {
    /**
     * 
     * @param record
     * @return
     */
    @VertexApi(Api.WRITE)
    Record insert(Record record) throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.WRITE)
    Record update(Record record) throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @param uniqueId
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean delete(Record record) throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.WRITE)
    boolean purge(Record record) throws AbstractDatabaseException;

    /**
     * 
     * @param record
     * @param columns
     * @param filter
     * @return
     * @throws AbstractDatabaseException
     */
    @VertexApi(Api.READ)
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
    @VertexApi(Api.READ)
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
    @VertexApi(Api.READ)
    ConcurrentMap<Long, List<Record>> queryByPage(Record record, String[] columns, List<Value<?>> params,
            Expression filters, OrderBy orders, Pager pager) throws AbstractDatabaseException;

    /**
     * 主键策略：GUID, ASSIGNED, INCREMENT
     * 
     * @param record
     * @param uniqueId
     * @return
     */
    @VertexApi(Api.READ)
    Record selectById(Record record, Value<?> uniqueId) throws AbstractDatabaseException;

    /**
     * 主键策略：COLLECTION
     * 
     * @param record
     * @param uniqueIds
     * @return
     */
    @VertexApi(Api.READ)
    Record selectById(Record record, ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException;

}
