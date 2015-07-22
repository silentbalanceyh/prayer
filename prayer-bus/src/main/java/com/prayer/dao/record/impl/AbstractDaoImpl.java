package com.prayer.dao.record.impl;		// NOPMD

import static com.prayer.util.Calculator.diff;
import static com.prayer.util.Generator.uuid;
import static com.prayer.util.Instance.reservoir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.dao.record.RecordDao;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.database.InvalidPKParameterException;
import com.prayer.exception.database.PKValueMissingException;
import com.prayer.exception.database.PolicyConflictCallException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.model.h2.FieldModel;
import com.prayer.model.h2.MetaModel;

import net.sf.oval.constraint.MinSize;
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
	protected abstract Set<String> getPKFilters(Record record);

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 获取JDBC访问器
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

	// ~ Major Logical =======================================
	/**
	 * 
	 * @param record
	 * @throws AbstractDatabaseException
	 */
	protected boolean sharedUpdate(@NotNull final Record record) throws AbstractDatabaseException {
		// 1.获取主键条件语句
		final ConcurrentMap<String, Value<?>> params = SqlHelper.prepPKWhere(record);
		// 2.获取参数列表
		final List<Value<?>> paramValues = new ArrayList<>();
		final Set<String> pkCols = new TreeSet<>(params.keySet());
		for (final String column : pkCols) {
			paramValues.add(record.column(column));
		}
		// 3.准备Update语句的Where部分
		final Expression whereExpr = SqlHelper.getAndExpr(pkCols);
		// 4.移除主键本身的更新
		final Collection<String> columns = diff(record.columns(), pkCols);
		final List<Value<?>> updatedValues = SqlHelper.prepParam(record, pkCols.toArray(Constants.T_STR_ARR));
		// 5.SQL语句
		final MetaModel metadata = record.schema().getMeta();
		final String sql = SqlDmlStatement.prepUpdateSQL(metadata.getTable(), columns, whereExpr);
		// 6.最终参数表
		paramValues.addAll(updatedValues);
		// 7.执行
		final JdbcContext jdbc = this.getContext(record.identifier());
		final int ret = jdbc.execute(sql, paramValues);
		return ret > Constants.RC_SUCCESS;
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
			final String sql = SqlHelper.prepInsertSQL(record, getPKFilters(record).toArray(Constants.T_STR_ARR));
			final List<Value<?>> params = SqlHelper.prepParam(record, getPKFilters(record).toArray(Constants.T_STR_ARR));

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
			final String sql = SqlHelper.prepInsertSQL(record, Constants.T_STR_ARR);
			final List<Value<?>> params = SqlHelper.prepParam(record, Constants.T_STR_ARR);

			jdbc.insert(sql, params, false, null);
		}
	}

	/**
	 * 共享主键查询语句
	 * 
	 * @param record
	 * @param paramMap
	 * @throws AbstractDatabaseException
	 */
	protected List<Record> sharedSelect(@NotNull final Record record,
			@NotNull final ConcurrentMap<String, Value<?>> paramMap) throws AbstractDatabaseException {
		// 1.获取主键Policy策略以及Jdbc访问器，验证Policy
		final MetaModel metadata = record.schema().getMeta();
		interrupt(metadata.getPolicy(), false);
		// 2.生成Expression所需要的主键Where子句列，验证查询条件是否主键列
		final Set<String> paramCols = new TreeSet<>(paramMap.keySet());
		interrupt(record, paramCols);
		// 3.生成参数表
		final List<Value<?>> paramValues = new ArrayList<>();
		for (final String column : paramCols) {
			paramValues.add(record.column(column));
		}
		return sharedSelect(record, record.columns().toArray(Constants.T_STR_ARR), paramValues,
				SqlHelper.getAndExpr(paramCols));
	}

	/**
	 * 生成查询结果集
	 * 
	 * @param record
	 * @param columns
	 * @param params
	 * @param filters
	 * @return
	 * @throws AbstractDatabaseException
	 */
	protected List<Record> sharedSelect(@NotNull final Record record, @NotNull @MinSize(1) final String[] columns,
			final List<Value<?>> params, final Expression filters) throws AbstractDatabaseException {
		// 1.获取JDBC访问器
		final MetaModel metadata = record.schema().getMeta();
		final JdbcContext jdbc = this.getContext(record.identifier());
		// 2.生成SQL语句
		final String sql = SqlDmlStatement.prepSelectSQL(metadata.getTable(), Arrays.asList(columns), filters);
		// 3.根据参数表生成查询结果集
		return SqlHelper.extractData(record, jdbc.select(sql, params, columns));
	}

	/**
	 * 共享主键删除语句
	 * 
	 * @param record
	 * @param paramMap
	 * @return
	 * @throws AbstractDatabaseException
	 */
	protected boolean sharedDelete(@NotNull final Record record,
			@NotNull final ConcurrentMap<String, Value<?>> paramMap) throws AbstractDatabaseException {
		// 1.获取JDBC访问器
		final MetaModel metadata = record.schema().getMeta();
		final JdbcContext jdbc = this.getContext(record.identifier());
		// 2.生成Expression
		final Set<String> paramCols = new TreeSet<>(paramMap.keySet());
		final Expression whereExpr = SqlHelper.getAndExpr(paramCols);
		// 3.生成SQL语句
		final String sql = SqlDmlStatement.prepDeleteSQL(metadata.getTable(), whereExpr);
		// 4.生成参数表
		final List<Value<?>> paramValues = new ArrayList<>();
		for (final String column : paramCols) {
			paramValues.add(record.column(column));
		}
		// 5.执行
		final int ret = jdbc.execute(sql, paramValues);
		return ret > Constants.RC_SUCCESS;
	}

	// ~ Assistant Methods ===================================
	/**
	 * 主键带值验证
	 * 
	 * @param record
	 * @throws AbstractDatabaseException
	 */
	protected void interrupt(@NotNull final Record record) throws AbstractDatabaseException {
		final List<FieldModel> pkSchemas = record.schema().getPrimaryKeys();
		for (final FieldModel field : pkSchemas) {
			final Value<?> value = record.get(field.getName());
			if (null == value) {
				throw new PKValueMissingException(getClass(), field.getColumnName(),
						record.schema().getMeta().getTable());
			}
		}
	}

	// ~ Exception Throws ====================================
	// ~ Private Methods =====================================
	/**
	 * 验证pkeys中的列是否主键
	 * 
	 * @param record
	 * @param pkeys
	 * @throws AbstractDatabaseException
	 */
	private void interrupt(@NotNull final Record record, @NotNull @MinSize(1) final Collection<String> pkeys)
			throws AbstractDatabaseException {
		for (final String pkey : pkeys) {
			final FieldModel pkSchema = record.schema().getColumn(pkey);
			if (!pkSchema.isPrimaryKey()) {
				throw new InvalidPKParameterException(getClass(), pkey, record.schema().getMeta().getTable());
			}
		}
	}

	/**
	 * 
	 * @param policy
	 * @param isMulti
	 * @throws AbstractDatabaseException
	 */
	private void interrupt(@NotNull final MetaPolicy policy, final boolean isMulti) throws AbstractDatabaseException {
		if (isMulti && MetaPolicy.COLLECTION != policy) {
			throw new PolicyConflictCallException(getClass(), policy.toString());
		} else if (!isMulti && MetaPolicy.COLLECTION == policy) {
			throw new PolicyConflictCallException(getClass(), policy.toString());
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
