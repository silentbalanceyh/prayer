package com.prayer.business.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.exception.web._400ServiceOrderByException;
import com.prayer.exception.web._500ServiceReturnSizeException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.business.AbstractPerformer;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.Eidolon;
import com.prayer.model.business.OrderBy;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RdPerformer extends AbstractPerformer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 传入EntityCls **/
    public RdPerformer(@NotNull final Class<?> entityCls) {
        super(entityCls);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 执行Find操作
     * 
     * @param record
     * @param marchal
     * @return
     */
    @NotNull
    public List<Record> performFind(@NotNull final Eidolon marchal) throws AbstractException {
        /** 1.生成响应结果 **/
        List<Record> retList = null;
        /** 2.读取Marchal中的Order By **/
        final OrderBy orders = marchal.getOrder();
        final Record record = marchal.getRecord();
        if (null != orders && orders.valid()) {
            // Order By Processing...
            retList = this.performer().queryByFilter(record, getColumns(record, marchal.getFilters()),
                    marchal.getValues(), marchal.getExpr(), marchal.getOrder());
        } else {
            // Non Order By Processing...
            retList = this.performer().queryByFilter(record, getColumns(record, marchal.getFilters()),
                    marchal.getValues(), marchal.getExpr());
        }
        return retList;
    }

    /**
     * 执行Page操作
     * 
     * @param record
     * @param marchal
     * @return
     * @throws AbstractException
     */
    @NotNull
    public ConcurrentMap<Long, List<Record>> performPage(@NotNull final Eidolon marchal) throws AbstractException {
        /** 1.生成响应结果 **/
        ConcurrentMap<Long, List<Record>> data = new ConcurrentHashMap<>();
        /** 2.读取Marchal中的Order By **/
        final OrderBy orders = marchal.getOrder();
        final Record record = marchal.getRecord();
        if (null != orders && orders.valid()) {
            // Page Action
            data = this.performer().queryByPage(record, getColumns(record, marchal.getFilters()), marchal.getValues(),
                    marchal.getExpr(), marchal.getOrder(), marchal.getPager());
        } else {
            // Page必须带Order By
            throw new _400ServiceOrderByException(getClass());
        }
        /** 3.构造结果 **/
        if (Constants.ONE != data.size()) {
            // Return Size Exception
            throw new _500ServiceReturnSizeException(getClass(), String.valueOf(Constants.ONE));
        }
        return data;
    }

    // ~ Private Methods =====================================
    /**
     * 
     * @param record
     * @param projections
     * @return
     */
    private String[] getColumns(final Record record, final String[] filters) throws AbstractDatabaseException {
        /** 1.获取所有定义过的Columns **/
        final Set<String> columns = new HashSet<>(record.columns());
        /** 2.构造最终列的返回 **/
        for (final String field : filters) {
            final String column = record.toColumn(field);
            columns.remove(column);
        }
        return columns.toArray(Constants.T_STR_ARR);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
