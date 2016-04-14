package com.prayer.record.meta;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.Assistant;
import com.prayer.dao.data.MetaRecordDalor;
import com.prayer.dao.data.entity.PEEntityDalor;
import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.crucial.MetaRecord;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.type.StringType;
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
    /** **/
    protected static final Value<?> V_ID = new StringType("ID");

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
        return PEEntityDalor.class;
    }

    /** 特殊值 **/
    protected ConcurrentMap<String, Value<?>> specValues() {
        return new ConcurrentHashMap<String, Value<?>>();
    }

    // ~ Methods =============================================
    /** **/
    protected RecordDao getDao() {
        return this.dao;
    }

    /** 创建一个Record **/
    protected Record createRecord(final String identifier) throws AbstractDatabaseException {
        final Record record = new MetaRecord(identifier);
        try {
            for (final String field : record.fields().keySet()) {
                record.set(field, Assistant.generate(record.fields().get(field), false));
            }
            this.injectSpecValue(record);
        } catch (AbstractDatabaseException ex) {
            peError(getLogger(), ex);
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
        try {
            for (final String field : record.fields().keySet()) {
                if (!ids.contains(field)) {
                    record.set(field, Assistant.generate(record.fields().get(field), true));
                }
            }
            this.injectSpecValue(record);
        } catch (AbstractDatabaseException ex) {
            peError(getLogger(), ex);
        }
    }

    // ~ Template Method =====================================
    /** 共享插入方法 **/
    protected boolean testInsert(final Evaluator evaluator) throws AbstractDatabaseException {
        final Record before = this.createRecord(identifier());
        final Record after = this.getDao().insert(before);
        final boolean ret = RecordKit.equal(before, after);
        evaluator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
        // 删除刚刚插入的数据
        this.getDao().delete(after);
        return ret;
    }

    /** 共享更新方法 **/
    protected boolean testUpdate(final Evaluator evaluator) throws AbstractDatabaseException {
        // 准备数据
        final Record before = this.createRecord(identifier());
        final Record after = this.getDao().insert(before);
        // 更新数据
        this.updateRecord(after);
        final Record updateR = this.getDao().update(after);
        // 循环内equals检查
        final boolean ret = RecordKit.equal(after, updateR);
        evaluator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
        // 检查完将新数据删除掉
        this.getDao().delete(updateR);
        return ret;
    }

    /** **/
    protected boolean testSelect(final Evaluator evaluator) throws AbstractDatabaseException {
        // 准备数据
        final Record before = this.createRecord(identifier());
        final Record after = this.getDao().insert(before);
        final Value<?> uniqueId = after.idKV().values().iterator().next();
        boolean ret = true;
        if (null != uniqueId) { // NOPMD
            final Record selectR = this.getDao().selectById(after, uniqueId);
            // 检查最终结果
            ret = RecordKit.equal(after, selectR);
            evaluator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
            // 检查完成，删除插入信息
            this.getDao().delete(selectR);
        }
        return ret;
    }

    /** **/
    protected boolean testInvalidSelect(final Evaluator evaluator) throws AbstractDatabaseException {
        // 准备数据
        final Record before = this.createRecord(identifier());
        final Record selectR = this.getDao().selectById(before, V_ID);
        final boolean ret = null == selectR;
        // 判断结果
        evaluator.evalTrue(message(TST_NULL), ret);
        // 检查完成，删除插入数据
        return ret;
    }

    /** **/
    protected boolean testUnsupport(final Evaluator evaulator) throws AbstractDatabaseException {
        // 准备数据
        final Record before = this.createRecord(identifier());
        final Record after = this.getDao().insert(before);
        // 调用select
        final Record selectR = this.getDao().selectById(after, after.idKV());
        // 判断最终结果
        final boolean ret = RecordKit.equal(after, selectR);
        evaulator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
        // 检查完毕将新插入的数据删除掉
        this.getDao().delete(selectR);
        return true;
    }
    // ~ Private Methods =====================================

    private void injectSpecValue(final Record record) throws AbstractDatabaseException {
        final ConcurrentMap<String,Value<?>> data = this.specValues();
        for(final String field: data.keySet()){
            final Value<?> value = data.get(field);
            if(null != value){
                record.set(field, value);
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
