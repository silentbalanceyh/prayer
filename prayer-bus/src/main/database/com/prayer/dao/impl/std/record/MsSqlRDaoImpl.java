package com.prayer.dao.impl.std.record;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.dao.AbstractRDaoImpl;
import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Constants;
import com.prayer.constant.SqlSegment;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.bus.Pager;
import com.prayer.model.database.FieldModel;
import com.prayer.util.dao.SqlDmlStatement;
import com.prayer.util.dao.QueryHelper;
import com.prayer.util.dao.Interrupter.Policy;
import com.prayer.util.dao.Interrupter.PrimaryKey;
import com.prayer.util.dao.Interrupter.Response;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
final class MsSqlRDaoImpl extends AbstractRDaoImpl { // NOPMD
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
    public Set<String> getPKFilters(final Record record) throws AbstractDatabaseException {
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
        // 1.调用父类方法
        final boolean ret = super.sharedInsert(record);
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
    public Record selectById(final Record record, final Value<?> uniqueId) throws AbstractDatabaseException {
        // ERR.主键值验证
        PrimaryKey.interrupt(getClass(), record);
        // ERR.Policy验证，只有这种会验证Policy，另外一种方式不验证Policy
        Policy.interrupt(getClass(), record.policy(), false);
        // 1.填充主键参数
        final FieldModel pkField = record.idschema().get(Constants.ZERO);
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
    public List<Record> queryByFilter(final Record record, final String[] columns, final List<Value<?>> params,
            final Expression filters) throws AbstractDatabaseException {
        return super.sharedSelect(record, columns, params, filters);
    }

    /** **/
    @Override
    public List<Record> queryByFilter(final Record record, final String[] columns, final List<Value<?>> params,
            final Expression filters, final OrderBy orders) throws AbstractDatabaseException {
        return super.sharedSelect(record, columns, params, filters, orders);
    }

    /**
     * 
     */
    @Override
    public Record selectById(final Record record, final ConcurrentMap<String, Value<?>> uniqueIds)
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
    public ConcurrentMap<Long, List<Record>> queryByPage(final Record record, final String[] columns,
            final List<Value<?>> params, final Expression filters, final OrderBy orders, final Pager pager)
                    throws AbstractDatabaseException {
        // 1.获取JDBC访问器
        final JdbcContext jdbc = this.getContext(record.identifier());
        // 2.生成SQL Count语句
        final String countSql = SqlDmlStatement.prepCountSQL(record.table(), filters);
        // 3.返回Sql Count
        final Long count = jdbc.count(countSql);
        // 4.生成Page语句
        final String pageSql = this.prepSelectPageSQL(record, columns, filters, orders, pager);
        // 5.列信息
        final String[] cols = columns.length > 0 ? columns : record.columns().toArray(Constants.T_STR_ARR);
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
    private String prepSelectPageSQL(final Record record, final String[] columns, final Expression filters,
            final OrderBy orders, final Pager pager) throws AbstractDatabaseException {
        // 1.构造Page的SQL语句
        final StringBuilder retSql = new StringBuilder(Constants.BUFFER_SIZE);
        final String T1 = "T1"; // NOPMD
        final String T2 = "T2"; // NOPMD
        final String ROW = "N"; // NOPMD
        // 2.Select部分
        retSql.append("SELECT").append(Symbol.SPACE);
        if (Constants.ZERO < columns.length) {
            for (int idx = 0; idx < columns.length; idx++) {
                retSql.append(T1).append(Symbol.DOT).append(columns[idx]);
                if (idx < (columns.length - 1)) {
                    retSql.append(Symbol.COMMA);
                }
            }
        } else {
            retSql.append(T1).append(Symbol.DOT).append('*');
        }
        retSql.append(Symbol.SPACE).append("FROM").append(Symbol.SPACE).append(record.table()).append(Symbol.SPACE)
                .append(T1).append(Symbol.COMMA);
        // 3.Primary Keys
        final List<String> primaryKeys = new ArrayList<>();
        final Iterator<String> pkIt = record.idKV().keySet().iterator();
        while (pkIt.hasNext()) {
            primaryKeys.add(pkIt.next());
        }
        // 3.ROW_NUMBER 部分
        final Integer start = (pager.getPageIndex() - 1) * pager.getPageSize();
        final Integer end = pager.getPageIndex() * pager.getPageSize();
        {
            retSql.append(Symbol.BRACKET_SL).append("SELECT").append(Symbol.SPACE).append("TOP").append(Symbol.SPACE);
            retSql.append(end).append(Symbol.SPACE).append("ROW_NUMBER()").append(Symbol.SPACE).append("OVER")
                    .append(Symbol.SPACE);
            retSql.append(Symbol.BRACKET_SL).append(SqlSegment.ORDER_BY).append(Symbol.SPACE).append(orders.toSql())
                    .append(Symbol.BRACKET_SR);
            retSql.append(Symbol.SPACE).append(ROW).append(Symbol.COMMA);
            retSql.append(StringUtil.join(primaryKeys.toArray(Constants.T_STR_ARR), Symbol.COMMA)).append(Symbol.SPACE)
                    .append("FROM").append(Symbol.SPACE).append(record.table()).append(Symbol.BRACKET_SR);
            retSql.append(Symbol.SPACE).append(T2);
        }
        // 4.Where子句
        if (null == filters) {
            retSql.append(Symbol.SPACE).append("WHERE").append(Symbol.SPACE);
        } else {
            retSql.append(Symbol.SPACE).append(MessageFormat.format(SqlSegment.TB_WHERE, filters.toSql()))
                    .append(Symbol.SPACE);
        }
        // 5.最后一部分Where子句
        final List<String> lastWhere = new ArrayList<>();
        lastWhere.add(T2 + Symbol.DOT + ROW + " > " + start);
        for (int idx = 0; idx < primaryKeys.size(); idx++) {
            lastWhere.add(T1 + Symbol.DOT + primaryKeys.get(idx) + " = " + T2 + Symbol.DOT + primaryKeys.get(idx));
        }
        // 6.拼接最后一部分
        retSql.append(
                StringUtil.join(lastWhere.toArray(Constants.T_STR_ARR), Symbol.SPACE + SqlSegment.AND + Symbol.SPACE));
        return retSql.toString();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
