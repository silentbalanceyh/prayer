package com.prayer.business.service;

import static com.prayer.util.debug.Log.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.business.AbstractPerformer;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;

import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class WrPerformer extends AbstractPerformer {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(WrPerformer.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 传入EntityCls **/
    public WrPerformer(@NotNull final Class<?> entityCls) {
        super(entityCls);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================

    /** 执行删除动作 **/
    public boolean performDelete(@NotNull final Record record) throws AbstractException {
        debug(LOGGER, " Deleting mode : Deleted => " + record.toString());
        return this.performer().delete(record);
    }

    /** 执行插入动作 **/
    public Record performInsert(@NotNull final Record record, @NotNull final String[] filters)
            throws AbstractException {
        debug(LOGGER, " Inserting mode : Inserted => " + record.toString());
        final Record inserted = this.performer().insert(record);
        this.filter(inserted, filters);
        return inserted;
    }

    /** 执行更新动作 **/
    public Record performUpdate(@NotNull final Record record, @NotNull final String[] filters)
            throws AbstractException {
        /** 1.先检查主键是否提供了值，更新必须 **/
        final AbstractException error = Interruptor.jerquePK(record);
        if (null == error) {
            /** 2.更新遍历，从数据库中拿到原始Record **/
            Record stored = this.findByIds(record);
            debug(LOGGER, " Updating mode : Queried => " + (null == stored ? "null" : stored.toString()));
            /** 3.执行数据更新操作 **/
            this.updateData(record, stored);
            debug(LOGGER, " Updating mode : Updated => " + (null == stored ? "null" : stored.toString()));
            /** 4.执行最终返回 **/
            final Record updated = (null == stored ? null : this.performer().update(stored));
            this.filter(updated, filters);
            return updated;
        } else {
            throw error;
        }
    }

    // ~ Private Methods =====================================
    /**
     * 将From中的数据拷贝到To中
     * 
     * @param from
     * @param to
     * @throws AbstractException
     */
    private void updateData(final Record from, final Record to) throws AbstractException {
        if (null != to) {
            for (final String field : from.fields().keySet()) {
                final Value<?> value = from.get(field);
                // TODO: 标记为NU的不更新
                if (null != value && !StringUtil.equals(value.literal(), "$NU$")) {
                    to.set(field, from.get(field));
                }
            }
        }
    }

    /**
     * Insert和Update必须的Filter操作
     * 
     * @param record
     * @param filters
     */
    private void filter(final Record record, final String[] filters) throws AbstractDatabaseException {
        for (String filter : filters) {
            if (null != filter && null != record.get(filter)) {
                /** 1.用null代替 **/
                record.set(filter, Constants.EMPTY_STR);
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
