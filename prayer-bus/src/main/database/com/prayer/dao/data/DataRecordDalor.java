package com.prayer.dao.data;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.business.OrderBy;
import com.prayer.model.business.Pager;
import com.prayer.model.crucial.DataRecord;
import com.prayer.resource.Injections;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 注意OVal验证了对象的实际类型，这个Dao只能针对GenericRecord类型
 * 
 * @author Lang
 *
 */
@Guarded
public class DataRecordDalor implements RecordDao {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 抽象Dao **/
    @NotNull
    private transient final RecordDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public DataRecordDalor() {
        this.dao = singleton(Injections.Data.DATA_DALOR);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    @Override
    @InstanceOf(Record.class)
    public Record insert(@NotNull @InstanceOfAny(DataRecord.class) final Record record)
            throws AbstractDatabaseException {
        return this.dao.insert(record);
    }

    /**
     * 
     */
    @Override
    @InstanceOf(Record.class)
    public Record update(@NotNull @InstanceOfAny(DataRecord.class) final Record record)
            throws AbstractDatabaseException {
        return this.dao.update(record);
    }

    /**
     * 
     */
    @Override
    @InstanceOf(Record.class)
    public Record selectById(@NotNull @InstanceOfAny(DataRecord.class) final Record record,
            @NotNull @InstanceOf(Value.class) final Value<?> uniqueId) throws AbstractDatabaseException {
        return this.dao.selectById(record, uniqueId);
    }

    /**
     * 
     */
    @Override
    @InstanceOf(Record.class)
    public Record selectById(@NotNull @InstanceOfAny(DataRecord.class) final Record record,
            @NotNull @MinSize(1) final ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException {
        return this.dao.selectById(record, uniqueIds);
    }

    /**
     * 
     */
    @Override
    public boolean delete(@NotNull @InstanceOfAny(DataRecord.class) final Record record)
            throws AbstractDatabaseException {
        return this.dao.delete(record);
    }
    /**
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    @Override
    public boolean purge(@NotNull @InstanceOfAny(DataRecord.class) final Record record)
            throws AbstractDatabaseException{
        return this.dao.purge(record);
    }

    /**
     * 
     * @param record
     * @param columns
     * @param params
     * @param filters
     * @return
     * @throws AbstractDatabaseException
     */
    @Override
    @NotNull
    public List<Record> queryByFilter(@NotNull @InstanceOfAny(DataRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters) throws AbstractDatabaseException {
        return this.dao.queryByFilter(record, columns, params, filters);
    }

    /**
     * 
     * @param record
     * @param columns
     * @param params
     * @param filters
     * @param orders
     * @return
     * @throws AbstractDatabaseException
     */
    @Override
    @NotNull
    public List<Record> queryByFilter(@NotNull @InstanceOfAny(DataRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders) throws AbstractDatabaseException {
        return this.dao.queryByFilter(record, columns, params, filters, orders);
    }

    /**
     * 
     * @param record
     * @param columns
     * @param params
     * @param filters
     * @param orders
     * @param pager
     * @return
     * @throws AbstractDatabaseException
     */
    @Override
    @NotNull
    public ConcurrentMap<Long, List<Record>> queryByPage(
            @NotNull @InstanceOfAny(DataRecord.class) final Record record, @NotNull final String[] columns,
            final List<Value<?>> params, final @InstanceOf(Expression.class) Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders,
            @NotNull @InstanceOfAny(Pager.class) final Pager pager) throws AbstractDatabaseException {
        return this.dao.queryByPage(record, columns, params, filters, orders, pager);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
