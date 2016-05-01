package com.prayer.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.exception.web.ServiceOrderByException;
import com.prayer.exception.web.ServiceReturnSizeException;
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
            throw new ServiceOrderByException(getClass());
        }
        /** 3.构造结果 **/
        if (Constants.ONE != data.size()) {
            // Return Size Exception
            throw new ServiceReturnSizeException(getClass(), String.valueOf(Constants.ONE));
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
        /** 1.获取所有定义过的Field **/
        final Set<String> fields = record.fields().keySet();
        /** 3.构造最终列的返回 **/
        final List<String> columns = new ArrayList<>();
        for (final String field : filters) {
            fields.remove(field);
        }
        /** 4.将Field转换成列 **/
        for (final String field : fields) {
            columns.add(record.toColumn(field));
        }
        return columns.toArray(Constants.T_STR_ARR);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
