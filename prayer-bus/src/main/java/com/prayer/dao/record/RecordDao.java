package com.prayer.dao.record;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;

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
	Record insert(Record record) throws AbstractMetadataException;

	/**
	 * 
	 * @param record
	 * @return
	 * @throws AbstractMetadataException
	 */
	Record update(Record record) throws AbstractMetadataException;

	/**
	 * 主键策略：GUID, ASSIGNED, INCREMENT
	 * 
	 * @param record
	 * @param uniqueId
	 * @return
	 */
	Record selectById(Record record, Value<?> uniqueId) throws AbstractMetadataException;

	/**
	 * 主键策略：COLLECTION
	 * 
	 * @param record
	 * @param uniqueIds
	 * @return
	 */
	Record selectById(Record record, ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractMetadataException;

	/**
	 * 
	 * @param record
	 * @param uniqueId
	 * @return
	 */
	boolean delete(Record record) throws AbstractMetadataException;

	/**
	 * 
	 * @param record
	 * @param columns
	 * @param filter
	 * @return
	 * @throws AbstractMetadataException
	 */
	List<Record> queryByFilter(Record record, String[] columns, List<Value<?>> params, Expression filters)
			throws AbstractMetadataException;
}
