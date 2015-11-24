package com.prayer.dao.record.impl;

import static com.prayer.util.Instance.instance;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.dao.record.RecordDao;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.kernel.query.OrderBy;
import com.prayer.kernel.query.Pager;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordDaoImpl implements RecordDao {
    // ~ Static Fields =======================================
    /** 前置验证条件 **/
    private static final String DAO_EXPR = "_this.dao != null";
    // ~ Instance Fields =====================================
    /** 抽象Dao **/
    @NotNull
    private transient final RecordDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public RecordDaoImpl() {
        this.dao = instance(Resources.DB_DAO);
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
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public Record insert(@NotNull @InstanceOf(Record.class) final Record record) throws AbstractDatabaseException {
        return this.dao.insert(record);
    }

    /**
     * 
     */
    @Override
    @InstanceOf(Record.class)
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public Record update(@NotNull @InstanceOf(Record.class) final Record record) throws AbstractDatabaseException {
        return this.dao.update(record);
    }

    /**
     * 
     */
    @Override
    @InstanceOf(Record.class)
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public Record selectById(@NotNull @InstanceOf(Record.class) final Record record,
            @NotNull @InstanceOf(Value.class) final Value<?> uniqueId) throws AbstractDatabaseException {
        return this.dao.selectById(record, uniqueId);
    }

    /**
     * 
     */
    @Override
    @InstanceOf(Record.class)
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public Record selectById(@NotNull @InstanceOf(Record.class) final Record record,
            @NotNull @MinSize(1) final ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException {
        return this.dao.selectById(record, uniqueIds);
    }

    /**
     * 
     */
    @Override
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public boolean delete(@NotNull @InstanceOf(Record.class) final Record record) throws AbstractDatabaseException {
        return this.dao.delete(record);
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
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public List<Record> queryByFilter(@NotNull @InstanceOf(Record.class) final Record record,
            @NotNull @MinSize(1) final String[] columns, final List<Value<?>> params,
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
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public List<Record> queryByFilter(@NotNull @InstanceOf(Record.class) final Record record,
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
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<Long, List<Record>> queryByPage(@NotNull @InstanceOf(Record.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            final @InstanceOf(Expression.class) Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders,
            @NotNull @InstanceOfAny(Pager.class) final Pager pager) throws AbstractDatabaseException {
        return this.dao.queryByPage(record, columns, params, filters, orders, pager);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
