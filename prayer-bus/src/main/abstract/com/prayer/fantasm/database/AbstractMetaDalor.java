package com.prayer.fantasm.database; // NOPMD

import static com.prayer.util.Generator.uuid;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.clazz;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.dao.ObjectTransferer;
import com.prayer.database.util.sql.SqlDMLBuilder;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.database.accessor.MetaAccessor;
import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.database.pool.JdbcConnection;
import com.prayer.facade.database.sql.SQLStatement;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.entity.Entity;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.facade.util.Transferer;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractTransactionException;
import com.prayer.model.business.OrderBy;
import com.prayer.model.business.Pager;
import com.prayer.model.crucial.MetaRecord;
import com.prayer.model.meta.database.PEField;
import com.prayer.resource.InceptBus;
import com.prayer.resource.Injections;
import com.prayer.util.exception.Interrupter.Api;
import com.prayer.util.exception.Interrupter.Policy;
import com.prayer.util.exception.Interrupter.PrimaryKey;
import com.prayer.util.exception.Interrupter.Response;
import com.prayer.util.jdbc.QueryHelper;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
@SuppressWarnings("unchecked")
public abstract class AbstractMetaDalor<ID extends Serializable> implements RecordDao { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 转换器 **/
    private transient final Transferer transferer;
    /** 构造SQL语句构造器 **/
    private transient final SqlDMLBuilder builder;
    /** 构造JDBC直接的Connection连接 **/
    private transient final JdbcConnection connection;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractMetaDalor() {
        this.transferer = singleton(ObjectTransferer.class);
        this.builder = SqlDMLBuilder.create();
        this.connection = singleton(Injections.Meta.CONNECTION);
    }

    // ~ Abstract Methods ====================================

    /** Log Reference **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    /** **/
    @Override
    @InstanceOf(Record.class)
    public Record insert(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        // ERR：检查主键定义
        PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema().size());
        // ERR: Policy Support
        Policy.interrupt(getClass(), MetaPolicy.GUID, record.policy());
        // 3.预处理UUID
        final PEField pkSchema = record.idschema().get(Constants.ZERO);
        record.set(pkSchema.getName(), uuid());
        // 4.将Record数据转换成H2 Model
        final Entity entity = this.transferer().toEntity(record);
        // 5.插入数据
        Entity ret = null;
        try {
            ret = this.getDao(record.identifier()).insert(entity);
        } catch (AbstractTransactionException ex) {
            peError(getLogger(), ex);
            Response.interrupt(getClass(), false);
        }
        // 6.处理返回值
        return this.transferer().fromEntity(record.identifier(), ret);
    }

    /** **/
    @Override
    @InstanceOf(Record.class)
    public Record selectById(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull @InstanceOf(Value.class) final Value<?> uniqueId) throws AbstractDatabaseException {
        // ERR.主键值验证
        PrimaryKey.interrupt(getClass(), record);
        // ERR: Policy Support
        Policy.interrupt(getClass(), MetaPolicy.GUID, record.policy());
        // 1.获取ID值
        final ID recordId = (ID) uniqueId.getValue();
        // 2.从数据库中获取T Entity
        final Entity ret = this.getDao(record.identifier()).getById(recordId);
        // 3.将获取到的数据执行转换
        return this.transferer().fromEntity(record.identifier(), ret);
    }

    /** **/
    @Override
    public boolean delete(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        // ERR：检查主键定义
        PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema().size());
        // ERR: 主键值验证
        PrimaryKey.interrupt(getClass(), record);
        // ERR: Policy Support
        Policy.interrupt(getClass(), MetaPolicy.GUID, record.policy());
        // 1.获取ID值
        final Value<?> uniqueId = record.get(record.idschema().get(Constants.ZERO).getName());
        final ID recordId = (ID) uniqueId.getValue();
        // 2.执行删除操作
        return this.getDao(record.identifier()).deleteById(recordId);
    }

    /** **/
    @Override
    public boolean purge(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        // 1.直接删除，底层的Dao拥有了clear()方法
        return this.getDao(record.identifier()).purge();
    }

    /** **/
    @Override
    public Record update(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        // ERR：检查主键定义
        PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema().size());
        // ERR: 主键值验证
        PrimaryKey.interrupt(getClass(), record);
        // ERR: Policy Support
        Policy.interrupt(getClass(), MetaPolicy.GUID, record.policy());
        // 1.将Record数据转换成H2 Model
        final Entity entity = this.transferer().toEntity(record);
        // 2.直接插入
        Entity retT = null;
        try {
            retT = this.getDao(record.identifier()).update(entity);
        } catch (AbstractTransactionException ex) {
            peError(getLogger(), ex);
            Response.interrupt(getClass(), false);
        }
        return this.transferer().fromEntity(record.identifier(), retT);
    }

    /** NOT SUPPORT **/
    @Override
    @InstanceOf(Record.class)
    public Record selectById(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull @MinSize(1) final ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException {
        Api.interrupt(getClass(), "selectById(Record,ConcurrentMap<String,Value<?>>)");
        return null;
    }

    /**
     * 
     */
    @Override
    @NotNull
    public List<Record> queryByFilter(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params, final Expression filters)
            throws AbstractDatabaseException {
        return this.queryByFilter(record, columns, params, filters, null);
    }

    /** **/
    @Override
    @NotNull
    public List<Record> queryByFilter(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders) throws AbstractDatabaseException {
        // 1.生成SQL语句
        // final String sql = SqlDML.prepSelectSQL(record.table(),
        // Arrays.asList(columns), filters, orders);
        final String sql = builder.buildSelect(record.table(), Arrays.asList(columns), filters, orders);
        // 2.根据参数表生成查询结果集
        final String[] cols = columns.length > 0 ? columns : record.columns().toArray(Constants.T_STR_ARR);
        // 【Accessor不能操作的语句】3.直接从连接读取
        return QueryHelper.extractData(record, this.connection.select(sql, params, record.columnTypes(), cols));
    }

    /** **/
    @Override
    @NotNull
    public ConcurrentMap<Long, List<Record>> queryByPage(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders,
            @NotNull @InstanceOfAny(Pager.class) final Pager pager) throws AbstractDatabaseException {
        // 1.生成SQL Count语句
        final String countSql = builder.buildCount(record.table(), filters);// SqlDML.prepCountSQL(record.table(),
                                                                            // filters);
        // 2.返回Sql Count
        final Long count = this.connection.count(countSql);
        // 3.生成Page语句
        final String pageSql = this.buildPager(record, columns, filters, orders, pager);
        // 4.列信息
        final String[] cols = columns.length > 0 ? columns : record.columns().toArray(Constants.T_STR_ARR);
        // 5.结果
        final List<Record> list = QueryHelper.extractData(record,
                this.connection.select(pageSql, params, record.columnTypes(), cols));
        // 6 封装结果集
        final ConcurrentMap<Long, List<Record>> retMap = new ConcurrentHashMap<>();
        retMap.put(count, list);
        return retMap;
    }

    // ~ Methods =============================================

    /** **/
    @NotNull
    protected Transferer transferer() {
        return this.transferer;
    }

    // ~ Private Methods =====================================
    /** 读取元数据的Accessor **/
    private MetaAccessor getDao(final String identifier) {
        final Inceptor LOADER = InceptBus.build(Point.Schema.class);
        final String clsName = LOADER.getString(identifier + ".instance");
        return reservoir(clsName, Injections.Meta.ACCESSOR, clazz(clsName));
    }

    private String buildPager(final Record record, final String[] columns, final Expression filters,
            final OrderBy orders, final Pager pager) throws AbstractDatabaseException {
        // 1.构Page的SQL语句
        final StringBuilder retSql = new StringBuilder(Constants.BUFFER_SIZE);
        // 2.Select部分
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
        retSql.append(Symbol.SPACE).append("FROM").append(Symbol.SPACE).append(record.table());
        // 3.Where子句
        if (null != filters) {
            retSql.append(Symbol.SPACE).append(MessageFormat.format(SQLStatement.OP_WHERE, filters.toSql()))
                    .append(Symbol.SPACE);
        }
        // 4.Order By
        retSql.append(Symbol.SPACE).append(SQLStatement.OP_ORDER_BY).append(Symbol.SPACE).append(orders.toSql())
                .append(Symbol.SPACE);
        // 5.Limit & Offset
        final Integer start = (pager.getPageIndex() - 1) * pager.getPageSize();
        retSql.append("LIMIT").append(Symbol.SPACE).append(pager.getPageSize()).append(Symbol.SPACE).append("OFFSET")
                .append(Symbol.SPACE).append(start);
        return retSql.toString();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
