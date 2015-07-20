package com.prayer.dao.record.impl;

import static com.prayer.util.Generator.uuid;

import java.util.List;

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
	 * 
	 */
	@Override
	public Record insert(@NotNull final Record record) throws AbstractDatabaseException {
		final JdbcContext jdbc = this.getContext(record.identifier());
		// 获取主键Policy策略
		final MetaPolicy policy = record.schema().getMeta().getPolicy();
		String sql = null;
		List<Value<?>> params = null;
		if (MetaPolicy.INCREMENT == policy) {
			// 如果主键是自增长字段，则需要填充返回值
			final FieldModel pkSchema = record.schema().getPrimaryKeys().get(Constants.ZERO);
			final String idCol = pkSchema.getColumnName();

			// 父类方法，过滤掉主键传参
			sql = this.prepInsertSQL(record, idCol);
			params = this.prepInsertParam(record, idCol);

			final Value<?> ret = jdbc.insert(sql, params, true, pkSchema.getType());
			// <== 填充返回主键
			record.set(pkSchema.getName(), ret);
		} else {
			if (MetaPolicy.GUID == policy) {
				// 如果主键是GUID的策略，则需要预处理主键的赋值
				final FieldModel pkSchema = record.schema().getPrimaryKeys().get(Constants.ZERO);
				record.set(pkSchema.getName(), uuid());
			}

			// 父类方法，不过滤任何传参流程
			sql = this.prepInsertSQL(record, Constants.T_STR_ARR);
			params = this.prepInsertParam(record, Constants.T_STR_ARR);

			jdbc.insert(sql, params, false, null);
		}
		return record;
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
