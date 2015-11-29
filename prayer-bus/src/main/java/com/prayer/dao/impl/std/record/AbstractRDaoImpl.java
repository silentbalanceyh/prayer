package com.prayer.dao.impl.std.record; // NOPMD

import static com.prayer.util.Calculator.diff;
import static com.prayer.util.Generator.uuid;
import static com.prayer.util.Instance.reservoir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentMap;

import com.prayer.dao.impl.jdbc.JdbcConnImpl;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.h2.schema.FieldModel;
import com.prayer.model.kernel.GenericRecord;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.MemoryPool;
import com.prayer.util.cv.SystemEnum.MetaPolicy;
import com.prayer.util.dao.Interrupter.PrimaryKey;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
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
public abstract class AbstractRDaoImpl implements RecordDao { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /**
     * 获取Increment中需要过滤的ID列
     **/
    protected abstract Set<String> getPKFilters(Record record) throws AbstractDatabaseException;

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 获取JDBC访问器
     * 
     * @return
     */
    @NotNull
    @InstanceOf(JdbcContext.class)
    protected JdbcContext getContext(@NotNull @NotEmpty @NotBlank final String identifier) {
        JdbcContext context = MemoryPool.POOL_JDBC.get(identifier);
        if (null == context) {
            context = reservoir(MemoryPool.POOL_JDBC, identifier, JdbcConnImpl.class);
        }
        return context;
    }

    // ~ Major Logical =======================================
    /**
     * 
     * @param record
     * @throws AbstractDatabaseException
     */
    protected boolean sharedUpdate(@NotNull @InstanceOfAny(GenericRecord.class) final Record record)
            throws AbstractDatabaseException {
        // ERR：检查主键定义
        PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema().size());
        // 2.获取参数列表
        final List<Value<?>> paramValues = new ArrayList<>();
        final Set<String> pkCols = new TreeSet<>(record.idKV().keySet());

        // 3.准备Update语句的Where部分
        final Expression whereExpr = SqlHelper.getAndExpr(pkCols);
        // 4.移除主键本身的更新
        final Collection<String> columns = diff(record.columns(), pkCols);
        final List<Value<?>> updatedValues = SqlHelper.prepParam(record, pkCols.toArray(Constants.T_STR_ARR));
        // 5.SQL语句
        final String sql = SqlDmlStatement.prepUpdateSQL(record.table(), columns, whereExpr);
        // 6.最终参数表，先添加基本参数，再添加主键
        paramValues.addAll(updatedValues);
        /**
         * 注意：JDBC中主键放在SQL语句的参数表后边，因为WHERE在后边，所以其Index索引是根据SQL语句中的定义来的
         */
        for (final String column : pkCols) {
            paramValues.add(record.column(column));
        }
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
    protected boolean sharedInsert(@NotNull @InstanceOfAny(GenericRecord.class) final Record record)
            throws AbstractDatabaseException {
        // ERR：检查主键定义
        PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema().size());
        // 获取主键Policy策略以及Jdbc访问器
        final MetaPolicy policy = record.policy();
        final JdbcContext jdbc = this.getContext(record.identifier());
        if (MetaPolicy.INCREMENT == policy) {
            /**
             * 如果主键是自增长的，在插入数据的时候不需要传参，并且插入成功过后需要获取返回值
             */
            final FieldModel pkSchema = record.idschema().get(Constants.ZERO);
            // 父类方法，过滤掉主键传参
            final String sql = SqlHelper.prepInsertSQL(record, getPKFilters(record).toArray(Constants.T_STR_ARR));
            final List<Value<?>> params = SqlHelper.prepParam(record,
                    getPKFilters(record).toArray(Constants.T_STR_ARR));

            final Value<?> ret = jdbc.insert(sql, params, true, pkSchema.getType());
            // <== 填充返回主键
            record.set(pkSchema.getName(), ret);
        } else {
            if (MetaPolicy.GUID == policy) {
                // 如果主键是GUID的策略，则需要预处理主键的赋值
                final FieldModel pkSchema = record.idschema().get(Constants.ZERO);
                record.set(pkSchema.getName(), uuid());
            }

            // 父类方法，不过滤任何传参流程
            final String sql = SqlHelper.prepInsertSQL(record, Constants.T_STR_ARR);
            final List<Value<?>> params = SqlHelper.prepParam(record, Constants.T_STR_ARR);

            jdbc.insert(sql, params, false, null);
        }
        return true;
    }

    /**
     * 共享主键查询语句
     * 
     * @param record
     * @param paramMap
     * @throws AbstractDatabaseException
     */
    @NotNull
    protected List<Record> sharedSelect(@NotNull @InstanceOfAny(GenericRecord.class) final Record record,
            @NotNull final ConcurrentMap<String, Value<?>> paramMap) throws AbstractDatabaseException {
        // 1.生成Expression所需要的主键Where子句列，验证查询条件是否主键列
        final Set<String> paramCols = new TreeSet<>(paramMap.keySet());
        PrimaryKey.interrupt(getClass(), record, paramCols);
        // 2.生成参数表
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
    @NotNull
    protected List<Record> sharedSelect(@NotNull @InstanceOfAny(GenericRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params, final Expression filters)
                    throws AbstractDatabaseException {
        return sharedSelect(record, columns, params, filters, null);
        /*
         * // 1.获取JDBC访问器 final JdbcContext jdbc =
         * this.getContext(record.identifier()); // 2.生成SQL语句 final String sql =
         * SqlDmlStatement.prepSelectSQL(record.table(), Arrays.asList(columns),
         * filters, null); // 3.根据参数表生成查询结果集 final String[] cols =
         * columns.length > 0 ? columns :
         * record.columns().toArray(Constants.T_STR_ARR); return
         * SqlHelper.extractData(record, jdbc.select(sql, params,
         * record.columnTypes(), cols));
         */
    }

    /**
     * 生成查询结果集
     * 
     * @param record
     * @param columns
     * @param params
     * @param filters
     * @param orders
     * @return
     * @throws AbstractDatabaseException
     */
    @NotNull
    protected List<Record> sharedSelect(@NotNull @InstanceOfAny(GenericRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters, @InstanceOfAny(OrderBy.class) final OrderBy orders)
                    throws AbstractDatabaseException {
        // 1.获取JDBC访问器
        final JdbcContext jdbc = this.getContext(record.identifier());
        // 2.生成SQL语句
        final String sql = SqlDmlStatement.prepSelectSQL(record.table(), Arrays.asList(columns), filters, orders);
        // 3.根据参数表生成查询结果集
        final String[] cols = columns.length > 0 ? columns : record.columns().toArray(Constants.T_STR_ARR);
        return SqlHelper.extractData(record, jdbc.select(sql, params, record.columnTypes(), cols));
    }

    /**
     * 共享主键删除语句
     * 
     * @param record
     * @param paramMap
     * @return
     * @throws AbstractDatabaseException
     */
    protected boolean sharedDelete(@NotNull @InstanceOfAny(GenericRecord.class) final Record record,
            @NotNull final ConcurrentMap<String, Value<?>> paramMap) throws AbstractDatabaseException {
        // 1.获取JDBC访问器
        final JdbcContext jdbc = this.getContext(record.identifier());
        // 2.生成Expression
        final Set<String> paramCols = new TreeSet<>(paramMap.keySet());
        final Expression whereExpr = SqlHelper.getAndExpr(paramCols);
        // 3.生成SQL语句
        final String sql = SqlDmlStatement.prepDeleteSQL(record.table(), whereExpr);
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
    // ~ Exception Throws ====================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
