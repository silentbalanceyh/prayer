package com.prayer.dao.record;

import java.util.List;

import com.prayer.exception.AbstractDatabaseException;
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
	Record selectById(Value<?>... uniqueId);
	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	boolean deleteById(Value<?>... uniqueId) throws AbstractDatabaseException;
	/**
	 * 
	 * @param columns
	 * @param filter
	 * @return
	 * @throws AbstractDatabaseException
	 */
	List<Record> queryByFilter(String[] columns, Expression filter) throws AbstractDatabaseException;
}
