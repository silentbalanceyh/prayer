package com.prayer.dao.impl.std.record;

import static com.prayer.util.Generator.uuid;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;

import com.prayer.base.dao.AbstractRDaoImpl;
import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Constants;
import com.prayer.constant.SqlSegment;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.record.Record;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.bus.Pager;
import com.prayer.model.crucial.GenericRecord;
import com.prayer.model.meta.database.PEField;
import com.prayer.util.exception.Interrupter.Policy;
import com.prayer.util.exception.Interrupter.PrimaryKey;
import com.prayer.util.exception.Interrupter.Response;
import com.prayer.util.jdbc.QueryHelper;
import com.prayer.util.jdbc.SqlDML;


/**
 * 
 * @author Lang
 *
 */
final class OracleRDaoImpl extends AbstractRDaoImpl { // NOPMD
	// ~ Static Fields =======================================
	/* get next seq value */
	private final static String SQL_NEXT_SEQ = "SELECT {0}.NEXTVAL FROM DUAL";

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
	public Set<String> getPKFilters(final Record record)
			throws AbstractDatabaseException {
		final MetaPolicy policy = record.policy();
		if (MetaPolicy.INCREMENT == policy) {
			return record.idKV().keySet();// QueryHelper.prepPKWhere(record).keySet();
		}
		return new HashSet<>();
	}

	/**
	 * Insert的第一个版本完成，调用共享Insert方法
	 */
	@Override
	public Record insert(final Record record) throws AbstractDatabaseException {
		// 1.调用自身方法
		final boolean ret = this.oracleInsert(record);
		// 2.后期执行检查
		Response.interrupt(getClass(), ret);
		// 3.返回修改过的record
		return record;
	}

	/** **/
	@Override
	public Record update(final Record record) throws AbstractDatabaseException {
		// 1.主键值验证
		PrimaryKey.interrupt(getClass(), record);
		// 2.调用父类函数
		final boolean ret = super.sharedUpdate(record);
		// 3.后期执行检查
		Response.interrupt(getClass(), ret);
		// 4.返回最终修改过的record
		return record;
	}

	/**
     * 
     */
	@Override
	public Record selectById(final Record record, final Value<?> uniqueId)
			throws AbstractDatabaseException {
		// ERR.主键值验证
		PrimaryKey.interrupt(getClass(), record);
		// ERR.Policy验证，只有这种会验证Policy，另外一种方式不验证Policy
		Policy.interrupt(getClass(), record.policy(), false);
		// 1.填充主键参数
		final PEField pkField = record.idschema().get(Constants.ZERO);
		final ConcurrentMap<String, Value<?>> uniqueIds = new ConcurrentHashMap<>();
		uniqueIds.put(pkField.getColumnName(), uniqueId);
		// 2.调用内部函数
		final List<Record> records = this.sharedSelect(record, uniqueIds);
		// 3.响应结果检查
		return Response.interrupt(getClass(), records, record.table());
	}

	/** **/
	@Override
	public boolean delete(final Record record) throws AbstractDatabaseException {
		// 1.主键值验证
		PrimaryKey.interrupt(getClass(), record);
		// 2.调用父类函数
		final boolean ret = super.sharedDelete(record, record.idKV());
		// 3.后期执行检查
		Response.interrupt(getClass(), ret);
		return ret;
	}
	
    /** **/
    @Override
    public boolean purge(final Record record) throws AbstractDatabaseException{
        // 1.调用父类函数
        final boolean ret = super.sharedPurge(record);
        // 2.后期执行检查
        Response.interrupt(getClass(), ret);
        return ret;
    }

	/** **/
	@Override
	public List<Record> queryByFilter(final Record record,
			final String[] columns, final List<Value<?>> params,
			final Expression filters) throws AbstractDatabaseException {
		return super.sharedSelect(record, columns, params, filters);
	}

	/** **/
	@Override
	public List<Record> queryByFilter(final Record record,
			final String[] columns, final List<Value<?>> params,
			final Expression filters, final OrderBy orders)
			throws AbstractDatabaseException {
		return super.sharedSelect(record, columns, params, filters, orders);
	}

	/**
     * 
     */
	@Override
	public Record selectById(final Record record,
			final ConcurrentMap<String, Value<?>> uniqueIds)
			throws AbstractDatabaseException {
		// 0.Policy验证，只有这种会验证Policy，另外一种方式不验证Policy，这个地方必须过滤
		Policy.interrupt(getClass(), record.policy(), true);
		// 1.调用内部函数
		final List<Record> records = this.sharedSelect(record, uniqueIds);
		// 2.响应结果检查
		return Response.interrupt(getClass(), records, record.table());
	}

	/** **/
	@Override
	public ConcurrentMap<Long, List<Record>> queryByPage(final Record record,
			final String[] columns, final List<Value<?>> params, final Expression filters,
			final OrderBy orders, final Pager pager) throws AbstractDatabaseException {
		// 1.获取JDBC访问器
		final JdbcConnection jdbc = this.getContext(record.identifier());
		// 2.生成SQL Count语句
		final String countSql = SqlDML.prepCountSQL(record.table(),
				filters);
		// 3.返回Sql Count
		final Long count = jdbc.count(countSql);
		// 4.生成Page语句
		final String pageSql = this.prepSelectPageSQL(record, columns, params,
				filters, orders, pager);
		// 5.列信息
		final String[] cols = columns.length > 0 ? columns : record.columns()
				.toArray(Constants.T_STR_ARR);
		// 6.结果
		final List<Record> list = QueryHelper.extractData(record,
				jdbc.select(pageSql, params, record.columnTypes(), cols));
		// 7 封装结果集
		final ConcurrentMap<Long, List<Record>> retMap = new ConcurrentHashMap<>();
		retMap.put(count, list);
		return retMap;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 分页查询的SQL
	 * 
	 * @param record
	 * @param columns
	 * @param params
	 * @param filters
	 * @param orders
	 * @return
	 */
	private String prepSelectPageSQL(final Record record,
			final String[] columns, final List<Value<?>> params,
			final Expression filters, final OrderBy orders, final Pager pager)
			throws AbstractDatabaseException {

		final StringBuilder retSql = new StringBuilder(1000);
		final StringBuilder Sql1L = new StringBuilder(1000);
		final StringBuilder Sql2L = new StringBuilder(1000);
		final StringBuilder Sql3L = new StringBuilder(1000);

		// 0.0 oracle分页采用4层select的方法
		// 1.0 第一层Select，根据条件选出所有候选记录
		Sql1L.append("SELECT").append(Symbol.SPACE).append("ROWID rid")
				.append(Symbol.SPACE).append("FROM").append(Symbol.SPACE)
				.append(record.table());
		// 1.1 where clause
		if (null != filters) {
			Sql1L.append(Symbol.SPACE)
					.append(MessageFormat.format(SqlSegment.TB_WHERE,
							filters.toSql())).append(Symbol.SPACE);
		}
		// 1.2 order clause
		if (null != orders) {
			Sql1L.append(Symbol.SPACE).append(SqlSegment.ORDER_BY).append(Symbol.SPACE).append(orders.toSql()).append(Symbol.SPACE);
		}
		
		// 2.0 第二层select, 在上层基础上限制大限
		final Integer start = (pager.getPageIndex() - 1) * pager.getPageSize();
		final Integer end = pager.getPageIndex() * pager.getPageSize();
		
		Sql2L.append("SELECT").append(Symbol.SPACE).append("rid").append(Symbol.COMMA).append("ROWNUM rno")
		.append(Symbol.SPACE).append("FROM").append(Symbol.SPACE).append(Symbol.BRACKET_SL).append(Sql1L).append(Symbol.BRACKET_SR)
		.append(Symbol.SPACE).append("WHERE ROWNUM<=").append(end);
		
		// 3.0 第三层select, 在上层基础上限制最小限
		Sql3L.append("SELECT").append(Symbol.SPACE).append("rid").append(Symbol.SPACE).append("FROM").append(Symbol.SPACE).
		append(Symbol.BRACKET_SL).append(Sql2L).append(Symbol.BRACKET_SR).append(Symbol.SPACE).append("WHERE rno>").append(start);
		
		// 4.0 第四层select，通过 where in来选出最终的结果
		retSql.append("SELECT").append(Symbol.SPACE);
		if (Constants.ZERO < columns.length) {
			for (int idx = 0; idx < columns.length; idx++) {
				retSql.append(columns[idx]);
				if (idx < (columns.length - 1)) {
					retSql.append(Symbol.COMMA);
				}
			}
		} else {
			retSql.append('*');
		}
		retSql.append(Symbol.SPACE).append("FROM").append(Symbol.SPACE)
		.append(record.table()).append(Symbol.SPACE);
		// 4.1 where in clause
		retSql.append("WHERE").append(Symbol.SPACE).append("ROWID").append(Symbol.SPACE).append("IN")
        .append(Symbol.SPACE).append(Symbol.BRACKET_SL);
		// 4.2 append L3 sql and the rest
		retSql.append(Sql3L).append(Symbol.BRACKET_SR).append(Symbol.SPACE);
		// 4.3 append order clause again, as long as the order has been given
		if (null != orders) {
			retSql.append(Symbol.SPACE).append(SqlSegment.ORDER_BY).append(Symbol.SPACE).append(orders.toSql()).append(Symbol.SPACE);
		}
		
		return retSql.toString();
	}

	/**
	 * oracle Inert语句，increment特殊处理，其它部分和父类 sharedInsert一致
	 * 
	 * @param record
	 * @throws AbstractDatabaseException
	 */
	private boolean oracleInsert(
			@NotNull @InstanceOfAny(GenericRecord.class) final Record record)
			throws AbstractDatabaseException {
		// ERR：检查主键定义
		PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema()
				.size());
		// 获取主键Policy策略以及Jdbc访问器
		final MetaPolicy policy = record.policy();
		final JdbcConnection jdbc = this.getContext(record.identifier());
		if (MetaPolicy.INCREMENT == policy) {
			/**
			 * 如果主键是自增长的，需要获取相应的SEQ值
			 */
			final PEField pkSchema = record.idschema().get(Constants.ZERO);
			record.set(pkSchema.getName(), this.getSEQ(record, jdbc));
		} else if (MetaPolicy.GUID == policy) {
			// 如果主键是GUID的策略，则需要预处理主键的赋值
			final PEField pkSchema = record.idschema().get(Constants.ZERO);
			record.set(pkSchema.getName(), uuid());
		}
		// 父类方法，不过滤任何传参流程
		final String sql = QueryHelper.prepInsertSQL(record, Constants.T_STR_ARR);
		final List<Value<?>> params = QueryHelper.prepParam(record,
				Constants.T_STR_ARR);

		jdbc.insert(sql, params, false, null);
		return true;
	}

	/**
	 * This method is for Oracle only
	 */
	private String getSEQ(final Record record, final JdbcConnection jdbc) {
		final String seqSql = MessageFormat.format(SQL_NEXT_SEQ,
				record.seqname());
		final List<String> retList = jdbc.select(seqSql, "NEXTVAL");

		return retList.get(0);
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
