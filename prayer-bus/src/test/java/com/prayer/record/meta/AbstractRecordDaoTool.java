package com.prayer.record.meta;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.Assistant;
import com.prayer.dao.impl.data.MetaRecordDalor;
import com.prayer.dao.impl.data.entity.ScriptMetaDalor;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.crucial.MetaRecord;
import com.prayer.model.meta.database.PEField;
import com.prayer.record.fun.Evaluator;
import com.prayer.util.business.RecordKit;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractRecordDaoTool extends AbstractCommonTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** MetaRecord数据层接口 **/
    private transient final RecordDao dao = singleton(MetaRecordDalor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** 当前Meta对应Entity的Global Id **/
    protected abstract String identifier();
    /** 获取当前类的日志器 **/
    protected abstract Logger getLogger();
    // ~ Override Methods ====================================
    /** **/
    @Override
    protected Class<?> getTarget() {
        return ScriptMetaDalor.class;
    }

    // ~ Methods =============================================
    /** **/
    protected RecordDao getDao() {
        return this.dao;
    }

    /** 创建一个Record **/
    protected Record createRecord(final String identifier) {
        final Record record = instance(MetaRecord.class, identifier);
        for (final String field : record.fields().keySet()) {
            try {
                record.set(field, Assistant.generate(record.fields().get(field), false));
            } catch (AbstractDatabaseException ex) {
                peError(getLogger(), ex);
            }
        }
        return record;
    }

    /** 更新Record **/
    protected void updateRecord(final Record record) {
        // ID不可更新
        final List<PEField> keys = record.idschema();
        final Set<String> ids = new HashSet<>();
        for (final PEField key : keys) {
            ids.add(key.getName());
        }
        // 和添加不同，存在一个判断
        for (final String field : record.fields().keySet()) {
            try {
                if (!ids.contains(field)) {
                    record.set(field, Assistant.generate(record.fields().get(field), true));
                }
            } catch (AbstractDatabaseException ex) {
                peError(getLogger(), ex);
            }
        }
    }

    // ~ Template Method =====================================
    /** 共享插入方法 **/
    protected boolean testInsert(final String identifier, final Evaluator evaluator) throws AbstractDatabaseException {
        final Record before = this.createRecord(identifier);
        final Record after = this.getDao().insert(before);
        final boolean ret = RecordKit.equal(before, after);
        evaluator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
        // 删除刚刚插入的数据
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
