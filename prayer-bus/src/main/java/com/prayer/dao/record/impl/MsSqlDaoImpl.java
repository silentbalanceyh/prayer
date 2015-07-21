package com.prayer.dao.record.impl;

import static com.prayer.util.Generator.uuid;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.db.conn.JdbcContext;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.model.h2.FieldModel;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class MsSqlDaoImpl extends AbstractDaoImpl {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * INCREMENT中需要过滤ID列，这个方法用于获取ID列
	 */
	@Override
	public String getIdColumn(@NotNull final Record record){
		final MetaPolicy policy = record.schema().getMeta().getPolicy();
		String idCol = null;
		if(MetaPolicy.INCREMENT == policy){
			final FieldModel pkSchema = record.schema().getPrimaryKeys().get(Constants.ZERO);
			idCol = pkSchema.getColumnName();
		}
		return idCol;
	}
	
	/**
	 * Insert的第一个版本完成，调用共享Insert方法
	 */
	@Override
	public Record insert(@NotNull final Record record) throws AbstractDatabaseException {
		// 1.调用父类方法
		super.sharedInsert(record);
		// 2.返回修改过的record
		return record;
	}

	@Override
	public Record update(Record record) throws AbstractDatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Record selectById(final Record record, final Value<?> uniqueId) throws AbstractDatabaseException {
		// 获取主键Policy策略以及Jdbc访问器
		final MetaPolicy policy = record.schema().getMeta().getPolicy();
		if(MetaPolicy.COLLECTION == policy){
			
		}
		return null;
	}
	/** **/
	@Override
	public boolean delete(final Record record) throws AbstractDatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Record> queryByFilter(final Record record, String[] columns, Expression filter)
			throws AbstractDatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Record selectById(Record record, ConcurrentMap<String, Value<?>> uniqueIds)
			throws AbstractDatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
