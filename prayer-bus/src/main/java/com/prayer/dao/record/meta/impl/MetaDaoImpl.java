package com.prayer.dao.record.meta.impl;

import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.dao.record.RecordDao;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.kernel.model.MetaRecord;
import com.prayer.kernel.query.OrderBy;
import com.prayer.kernel.query.Pager;
import com.prayer.util.PropertyKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 为了统一和RecordDao中的接口以及反射构造，MetaDao使用延迟加载方式
 * @author Lang
 *
 */
@Guarded
public class MetaDaoImpl implements RecordDao {
    // ~ Static Fields =======================================
    /** **/
    private static final PropertyKit LOADER = new PropertyKit(MetaDaoImpl.class, Resources.META_CFG_FILE);
    /** 前置验证条件 **/
    private static final String DAO_EXPR = "_this.dao != null";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    @InstanceOf(RecordDao.class)
    private transient final RecordDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public MetaDaoImpl(@NotNull @NotBlank @NotEmpty final String identifier) {
        final String daoCls = LOADER.getString(identifier + ".dao.impl");
        this.dao = singleton(daoCls);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @InstanceOf(Record.class)
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public Record insert(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        return this.dao.insert(record);
    }

    /** **/
    @Override
    @InstanceOf(Record.class)
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public Record update(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        return this.dao.update(record);
    }

    /** **/
    @Override
    @InstanceOf(Record.class)
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public Record selectById(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull @InstanceOf(Value.class) final Value<?> uniqueId) throws AbstractDatabaseException {
        return this.dao.selectById(record, uniqueId);
    }

    /** **/
    @Override
    @InstanceOf(Record.class)
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public Record selectById(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull @MinSize(1) final ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException {
        return this.dao.selectById(record, uniqueIds);
    }

    @Override
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public boolean delete(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        return this.dao.delete(record);
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public List<Record> queryByFilter(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters) throws AbstractDatabaseException {
        return this.dao.queryByFilter(record, columns, params, filters);
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public List<Record> queryByFilter(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @InstanceOf(Expression.class) final Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders) throws AbstractDatabaseException {
        return this.dao.queryByFilter(record, columns, params, filters, orders);
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<Long, List<Record>> queryByPage(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull final String[] columns, final List<Value<?>> params,
            @NotNull @InstanceOf(Expression.class) final Expression filters,
            @NotNull @InstanceOfAny(OrderBy.class) final OrderBy orders,
            @NotNull @InstanceOfAny(Pager.class) final Pager pager) throws AbstractDatabaseException {
        return this.dao.queryByPage(record, columns, params, filters, orders, pager);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
