package com.prayer.fantasm.business;

import static com.prayer.util.reflection.Instance.reservoir;

import org.slf4j.Logger;

import com.prayer.business.service.Epsilon;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractException;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractPerformer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 真正调用数据层的位置 **/
    private transient final RecordDao performer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    public AbstractPerformer(@NotNull final Class<?> entityCls) {
        /**
         * 实例化当前的Dao
         */
        final Class<?> daoCls = Epsilon.getDalor(entityCls);
        this.performer = reservoir(daoCls.getName(), daoCls);
    }

    // ~ Abstract Methods ====================================
    /** 子类实现方法 **/
    protected abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 读取数据库访问器 **/
    protected RecordDao performer(){
        return this.performer;
    }
    /** 从数据库中读取Record **/
    protected Record findByIds(final Record record) throws AbstractException {
        Record stored = null;
        if (MetaPolicy.COLLECTION == record.policy()) {
            stored = this.performer.selectById(record, record.idKV());
        } else {
            final Value<?> id = record.idKV().values().iterator().next();
            stored = this.performer.selectById(record, id);
        }
        return stored;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
