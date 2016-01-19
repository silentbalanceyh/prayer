package com.prayer.dao.impl.std.meta;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Resources;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.bus.Pager;
import com.prayer.model.crucial.MetaRecord;
import com.prayer.util.io.PropertyKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 为了统一和RecordDao中的接口以及反射构造，MetaDao使用延迟加载方式
 * 
 * @author Lang
 *
 */
@Guarded
public class MetaDaoImpl implements RecordDao {
    // ~ Static Fields =======================================
    /** **/
    private static final PropertyKit LOADER = new PropertyKit(MetaDaoImpl.class, Resources.OOB_SCHEMA_FILE);
    /** 前置验证条件 **/
    // ~ Instance Fields =====================================
    /** **/
    @InstanceOf(RecordDao.class)
    private transient RecordDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @InstanceOf(Record.class)
    public Record insert(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        this.initLazyDao(record);
        return this.dao.insert(record);
    }

    /** **/
    @Override
    @InstanceOf(Record.class)
    public Record update(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        this.initLazyDao(record);
        return this.dao.update(record);
    }

    /** **/
    @Override
    @InstanceOf(Record.class)
    public Record selectById(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull @InstanceOf(Value.class) final Value<?> uniqueId) throws AbstractDatabaseException {
        this.initLazyDao(record);
        return this.dao.selectById(record, uniqueId);
    }

    /** **/
    @Override
    @InstanceOf(Record.class)
    public Record selectById(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull @MinSize(1) final ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException {
        this.initLazyDao(record);
        return this.dao.selectById(record, uniqueIds);
    }
    /** **/
    @Override
    public boolean delete(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        this.initLazyDao(record);
        return this.dao.delete(record);
    }
    /** **/
    @Override
    public boolean purge(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException{
        this.initLazyDao(record);
        return this.dao.purge(record);
    }

    /** **/
    @Override
    @NotNull
    public List<Record> queryByFilter(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters) throws AbstractDatabaseException {
        this.initLazyDao(record);
        return this.dao.queryByFilter(record, columns, params, filters);
    }

    /** **/
    @Override
    @NotNull
    public List<Record> queryByFilter(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders) throws AbstractDatabaseException {
        this.initLazyDao(record);
        return this.dao.queryByFilter(record, columns, params, filters, orders);
    }

    /** **/
    @Override
    @NotNull
    public ConcurrentMap<Long, List<Record>> queryByPage(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders,
            @NotNull @InstanceOfAny(Pager.class) final Pager pager) throws AbstractDatabaseException {
        this.initLazyDao(record);
        return this.dao.queryByPage(record, columns, params, filters, orders, pager);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private RecordDao initLazyDao(final Record record) {
        if (null == this.dao) {
            this.dao = singleton(LOADER.getString(record.identifier() + ".dao.impl"));
        }
        return this.dao;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
