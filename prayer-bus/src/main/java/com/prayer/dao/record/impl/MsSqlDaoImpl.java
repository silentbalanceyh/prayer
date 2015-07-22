package com.prayer.dao.record.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.database.ExecuteFailureException;
import com.prayer.exception.database.MoreThanOneException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.model.h2.FieldModel;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class MsSqlDaoImpl extends AbstractDaoImpl { // NOPMD
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
	public Set<String> getPKFilters(@NotNull final Record record) {
		final MetaPolicy policy = record.schema().getMeta().getPolicy();
		if (MetaPolicy.INCREMENT == policy) {
			return SqlHelper.prepPKWhere(record).keySet();
		}
		return new HashSet<>();
	}

	/**
	 * Insert的第一个版本完成，调用共享Insert方法
	 */
	@Override
	public Record insert(@NotNull final Record record) throws AbstractDatabaseException {
		// 1.调用父类方法
		final boolean ret = super.sharedInsert(record);
		// 2.后期执行检查
		interrupt(ret);
		// 3.返回修改过的record
		return record;
	}

	/** **/
	@Override
	public Record update(@NotNull final Record record) throws AbstractDatabaseException {
		// 1.主键值验证
		this.interrupt(record);
		// 2.调用父类函数
		final boolean ret = super.sharedUpdate(record);
		// 3.后期执行检查
		interrupt(ret);
		// 4.返回最终修改过的record
		return record;
	}

	/**
	 * 
	 */
	@Override
	public Record selectById(@NotNull final Record record, @NotNull final Value<?> uniqueId)
			throws AbstractDatabaseException {
		// 1.填充主键参数
		final FieldModel pkField = record.schema().getPrimaryKeys().get(Constants.ZERO);
		final ConcurrentMap<String, Value<?>> paramMap = new ConcurrentHashMap<>();
		paramMap.put(pkField.getColumnName(), uniqueId);
		// 2.调用内部函数
		return this.selectById(record, paramMap);
	}

	/** **/
	@Override
	public boolean delete(@NotNull final Record record) throws AbstractDatabaseException {
		// 1.主键值验证
		this.interrupt(record);
		// 2.获取主键表达式
		final ConcurrentMap<String, Value<?>> paramMap = SqlHelper.prepPKWhere(record);
		// 3.调用父类函数
		final boolean ret = super.sharedDelete(record, paramMap);
		// 4.后期执行检查
		interrupt(ret);
		return ret;
	}

	/** **/
	@Override
	public List<Record> queryByFilter(@NotNull final Record record, @NotNull @MinSize(1) final String[] columns,
			final List<Value<?>> params, final Expression filters) throws AbstractDatabaseException {
		return super.sharedSelect(record, columns, params, filters);
	}

	/**
	 * 
	 */
	@Override
	public Record selectById(@NotNull final Record record,
			@NotNull @MinSize(1) final ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException {
		// 1.填充主键参数
		final List<Record> records = this.sharedSelect(record, uniqueIds);
		if (Constants.ONE < records.size()) {
			throw new MoreThanOneException(getClass(), record.schema().getMeta().getTable());
		}
		// 2.根据查询结果返回
		return Constants.ZERO == records.size() ? null : records.get(Constants.ZERO);
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private void interrupt(final boolean retFlag) throws AbstractDatabaseException {
		if (!retFlag) {
			throw new ExecuteFailureException(getClass());
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
