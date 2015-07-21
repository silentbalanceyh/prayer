package com.prayer.dao.record.impl;

import static com.prayer.util.Calculator.diff;
import static com.prayer.util.Generator.uuid;
import static com.prayer.util.Instance.reservoir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.dao.record.RecordDao;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.model.h2.FieldModel;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
abstract class AbstractDaoImpl implements RecordDao { // NOPMD
	// ~ Static Fields =======================================
	/** JDBC的Context的延迟池化技术 **/
	private static final ConcurrentMap<String, JdbcContext> JDBC_POOLS = new ConcurrentHashMap<>();

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	/**
	 * 获取Increment中需要过滤的ID列
	 **/
	protected abstract String getIdColumn(Record record);

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @return
	 */
	@NotNull
	protected JdbcContext getContext(@NotNull @NotEmpty @NotBlank final String identifier) {
		JdbcContext context = JDBC_POOLS.get(identifier);
		if (null == context) {
			context = reservoir(JDBC_POOLS, identifier, JdbcConnImpl.class);
		}
		return context;
	}

	/**
	 * 共享Inert语句，根据不同的Policy设置SQL语句并且实现共享传参 这个方法必然会修改传入参数Record
	 * 
	 * @param record
	 * @throws AbstractDatabaseException
	 */
	protected void sharedInsert(@NotNull final Record record) throws AbstractDatabaseException {
		// 获取主键Policy策略以及Jdbc访问器
		final MetaPolicy policy = record.schema().getMeta().getPolicy();
		final JdbcContext jdbc = this.getContext(record.identifier());
		if (MetaPolicy.INCREMENT == policy) {
			/**
			 * 如果主键是自增长的，在插入数据的时候不需要传参，并且插入成功过后需要获取返回值
			 */
			final FieldModel pkSchema = record.schema().getPrimaryKeys().get(Constants.ZERO);
			// 父类方法，过滤掉主键传参
			final String sql = this.prepInsertSQL(record, getIdColumn(record));
			final List<Value<?>> params = this.prepInsertParam(record, getIdColumn(record));

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
			final String sql = this.prepInsertSQL(record, Constants.T_STR_ARR);
			final List<Value<?>> params = this.prepInsertParam(record, Constants.T_STR_ARR);

			jdbc.insert(sql, params, false, null);
		}
	}
	
	

	// ~ Private Methods =====================================
	/**
	 * 生成Insert语句
	 * 
	 * @param schema
	 * @param filters
	 * @return
	 */
	private String prepInsertSQL(final Record record, final String... filters) {
		final Collection<String> columns = diff(record.columns(), Arrays.asList(filters));
		return SqlDmlStatement.prepInsertSQL(record.table(), columns);
	}

	/**
	 * 生成Insert语句的参数表
	 * 
	 * @param record
	 * @param filters
	 * @return
	 */
	private List<Value<?>> prepInsertParam(final Record record, final String... filters) {
		final Collection<String> columns = diff(record.columns(), Arrays.asList(filters));
		final List<Value<?>> retParam = new ArrayList<>();
		for (final String column : columns) {
			retParam.add(record.column(column));
		}
		return retParam;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
