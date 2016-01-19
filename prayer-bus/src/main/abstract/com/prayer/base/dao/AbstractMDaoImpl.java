package com.prayer.base.dao; // NOPMD

import static com.prayer.util.Generator.uuid;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.base.model.AbstractEntity;
import com.prayer.constant.Constants;
import com.prayer.constant.SqlSegment;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.entity.Entity;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.record.Record;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.bus.Pager;
import com.prayer.model.crucial.MetaRecord;
import com.prayer.model.meta.database.PEField;
import com.prayer.util.bus.RecordSerializer;
import com.prayer.util.exception.Interrupter.Api;
import com.prayer.util.exception.Interrupter.Policy;
import com.prayer.util.exception.Interrupter.PrimaryKey;
import com.prayer.util.exception.Interrupter.Response;
import com.prayer.util.jdbc.QueryHelper;
import com.prayer.util.jdbc.SqlDML;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
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
public abstract class AbstractMDaoImpl <T extends AbstractEntity, ID extends Serializable> implements RecordDao { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final RecordSerializer serializer; // NOPMD

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractMDaoImpl() {
        this.serializer = singleton(RecordSerializer.class);
    }

    // ~ Abstract Methods ====================================
    /** Overwrite by sub class **/
    public abstract MetaAccessor<T, ID> getDao();

    /** Entity **/
    public abstract T newT();

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
        final T entity = this.toEntity(record);
        // 5.直接插入
        List<T> retList = new ArrayList<>();
        try {
            retList = this.getDao().insert(entity);
        } catch (AbstractTransactionException ex) {
            peError(getLogger(),ex);
            Response.interrupt(getClass(), false);
        }
        // 6.处理返回值
        return this.fromEntity(record.identifier(), retList.get(Constants.ZERO));
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
        final T ret = this.getDao().getById(recordId);
        // 3.将获取到的数据执行转换
        return this.fromEntity(record.identifier(), ret);
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
        return this.getDao().deleteById(recordId);
    }
    /** **/
    @Override
    public boolean purge(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException{
        // 1.直接删除，底层的Dao拥有了clear()方法
        return this.getDao().purge();
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
        final T entity = this.toEntity(record);
        // 2.直接插入
        T retT = null;
        try {
            retT = this.getDao().update(entity);
        } catch (AbstractTransactionException ex) {
            peError(getLogger(),ex);
            Response.interrupt(getClass(), false);
        }
        return this.fromEntity(record.identifier(), retT);
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
        // 1.获取JDBC访问器
        final JdbcConnection jdbc = null; // this.getDao().getContext(record.identifier());
        // 2.生成SQL语句
        final String sql = SqlDML.prepSelectSQL(record.table(), Arrays.asList(columns), filters, orders);
        // 3.根据参数表生成查询结果集
        final String[] cols = columns.length > 0 ? columns : record.columns().toArray(Constants.T_STR_ARR);
        return QueryHelper.extractData(record, jdbc.select(sql, params, record.columnTypes(), cols));
    }

    /** **/
    @Override
    @NotNull
    public ConcurrentMap<Long, List<Record>> queryByPage(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders,
            @NotNull @InstanceOfAny(Pager.class) final Pager pager) throws AbstractDatabaseException {
        // 1.获取JDBC访问器
        final JdbcConnection jdbc = null; // this.getDao().getContext(record.identifier());
        // 2.生成SQL Count语句
        final String countSql = SqlDML.prepCountSQL(record.table(), filters);
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

    /** **/
    @NotNull
    protected RecordSerializer serializer() {
        return this.serializer;
    }

    /** **/
    protected T toEntity(@NotNull final Record record) throws AbstractDatabaseException {
        T ret = this.newT();
        final JsonObject data = this.serializer.extractRecord(record);
        final Entity entity = ret.fromJson(data);
        if (null == entity) {
            ret = null; // NOPMD
        } else {
            ret = (T) entity;
        }
        return ret;
    }

    /** **/
    protected Record fromEntity(@NotNull @NotBlank @NotEmpty final String identifier, final Entity entity)
            throws AbstractDatabaseException {
        Record ret = null;
        if (null != entity) {
            final JsonObject data = entity.toJson();
            ret = this.serializer().encloseRecord(identifier, MetaRecord.class, data);
        }
        return ret;
    }
    // ~ Private Methods =====================================

    private String prepSelectPageSQL(final Record record, final String[] columns, final Expression filters,
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
            retSql.append(Symbol.SPACE).append(MessageFormat.format(SqlSegment.TB_WHERE, filters.toSql()))
                    .append(Symbol.SPACE);
        }
        // 4.Order By
        retSql.append(Symbol.SPACE).append(SqlSegment.ORDER_BY).append(Symbol.SPACE).append(orders.toSql())
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
