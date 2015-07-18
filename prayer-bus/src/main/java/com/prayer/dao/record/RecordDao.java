package com.prayer.dao.record;

import com.prayer.exception.AbstractDatabaseException;
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
	Record insert(Record record) throws AbstractDatabaseException;
	/**
	 * 
	 * @param record
	 * @return
	 * @throws AbstractDatabaseException
	 */
	Record update(Record record) throws AbstractDatabaseException;
	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	Record getById(Value<?>... uniqueId);
	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	boolean deleteById(Value<?>... uniqueId);
}
