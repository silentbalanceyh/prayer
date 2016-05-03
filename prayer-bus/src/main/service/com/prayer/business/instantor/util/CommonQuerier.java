package com.prayer.business.instantor.util;

import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.reservoir;

import java.util.ArrayList;
import java.util.List;

import com.prayer.business.service.Epsilon;
import com.prayer.exception.database.ReturnMoreException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.crucial.Transducer.V;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.crucial.DataRecord;
import com.prayer.model.query.Restrictions;

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
public class CommonQuerier {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 真正调用数据层的位置 **/
    private transient final RecordDao performer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public CommonQuerier() {
        final Class<?> daoCls = Epsilon.getDalor(DataRecord.class);
        this.performer = reservoir(daoCls.getName(), daoCls);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 读取唯一记录集
     * 
     * @param identifier
     * @param field
     * @param value
     * @return
     */
    public Record querySole(@NotNull @NotBlank @NotEmpty final String identifier,
            @NotNull @NotBlank @NotEmpty final String field, @NotNull @NotBlank @NotEmpty final String value)
            throws AbstractException {
        final List<Record> records = this.queryMuster(identifier, field, value);
        Record record = null;
        if (Constants.ONE == records.size()) {
            record = records.get(Constants.IDX);
        } else {
            throw new ReturnMoreException(getClass(), records.size());
        }
        return record;
    }

    /**
     * 根据某一列的多个值读取所有符合条件的identifier
     * 
     * @param identifier
     * @param field
     * @param value
     * @return
     * @throws AbstractException
     */
    public List<Record> queryMuster(@NotNull @NotBlank @NotEmpty final String identifier,
            @NotNull @NotBlank @NotEmpty final String field, @NotNull @NotBlank @NotEmpty final String... value)
            throws AbstractException {
        /** 1.实例化Record **/
        final Record record = instance(DataRecord.class, identifier);
        Expression expr = null;
        /** 2.构造Expression **/
        if (Constants.ONE == value.length) {
            expr = Restrictions.eq(record.toColumn(field));
        } else if (Constants.TWO == value.length) {
            final String column = record.toColumn(field);
            expr = Restrictions.or(Restrictions.eq(column), Restrictions.eq(column));
        } else if (Constants.TWO < value.length) {
            final String column = record.toColumn(field);
            Expression finalExpr = Restrictions.eq(column);
            for (int idx = 1; idx < value.length; idx++) {
                finalExpr = Restrictions.or(finalExpr, Restrictions.eq(column));
            }
            expr = finalExpr;
        }
        /** 3.Values **/
        final List<Value<?>> values = new ArrayList<>();
        for (final String item : value) {
            values.add(V.get().getValue(record.fields().get(field), item));
        }
        /** 5.读取Record列表 **/
        return this.performer.queryByFilter(record, Constants.T_STR_ARR, values, expr);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
