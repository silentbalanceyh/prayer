package com.prayer.dao.record.impl;

import java.util.List;

import com.prayer.dao.record.RecordDao;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
/**
 * 
 * @author Lang
 *
 */
public class RecordDaoImpl implements RecordDao{
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public Record insert(Record record) throws AbstractDatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Record update(Record record) throws AbstractDatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Record selectById(Value<?>... uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(Value<?>... uniqueId) throws AbstractDatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Record> queryByFilter(String[] columns, Expression filter) throws AbstractDatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
