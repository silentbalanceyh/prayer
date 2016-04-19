package com.prayer.record.data;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.prayer.AbstractCommonTool;
import com.prayer.Assistant;
import com.prayer.business.deployment.impl.SchemaBllor;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.dao.data.DataRecordDalor;
import com.prayer.facade.business.instantor.schema.SchemaInstantor;
import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.crucial.DataRecord;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.type.StringType;
import com.prayer.record.fun.Evaluator;
import com.prayer.resource.Resources;
import com.prayer.util.business.Collater;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractRecordDaoTool extends AbstractCommonTool {
    // ~ Static Fields =======================================
    /** **/
    protected static final String DATA_PATH = "/schema/data/json/dao/";
    /** **/
    protected static final Value<?> V_ID = new StringType("ID");
    // ~ Instance Fields =====================================
    /** Schema服务层接口 **/
    private transient final SchemaInstantor service;
    /** Record数据访问层 **/
    private transient final RecordDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractRecordDaoTool() {
        this.service = singleton(SchemaBllor.class);
        this.dao = singleton(DataRecordDalor.class);
    }

    // ~ Abstract Methods ====================================
    /** 获取数据库类型 **/
    protected abstract String getCategory();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Override
    protected Class<?> getTarget() {
        return DataRecordDalor.class;
    }

    /** 当前访问的Database是否匹配 **/
    protected boolean isValidDB() {
        return StringUtil.equals(getCategory(), Resources.Data.CATEGORY);
    }

    /** 获取服务层访问接口 **/
    protected SchemaInstantor getService() {
        return this.service;
    }

    /** 获取数据层访问接口 **/
    protected RecordDao getDao() {
        return this.dao;
    }
    // ~ Template Method =====================================

    /** Json -> H2 -> Database **/
    protected Schema prepareSchema(final String filePath, final String identifier) throws AbstractException {
        Schema ret = null;
        if (this.isValidDB()) {
            // Json -> H2
            ret = this.getService().importSchema(DATA_PATH + filePath);
            if (null != ret) {
                ret = this.getService().syncMetadata(ret);
            }
        }
        return ret;
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

    /** 创建新的Record **/
    protected Record createRecord(final String identifier) {
        final Record record = instance(DataRecord.class, identifier);
        for (final String field : record.fields().keySet()) {
            try {
                record.set(field, Assistant.generate(record.fields().get(field), false));
            } catch (AbstractDatabaseException ex) {
                peError(getLogger(), ex);
            }
        }
        return record;
    }

    // ~ Template Method =====================================
    /** 共享插入方法 **/
    protected boolean testInsert(final String identifier, final Evaluator evaluator) throws AbstractDatabaseException {
        if (this.isValidDB()) {
            final Record before = this.createRecord(identifier);
            final Record after = this.getDao().insert(before);
            final boolean ret = Collater.equal(before, after); // NOPMD
            evaluator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
            // 删除刚刚插入的数据
            this.getDao().delete(after);
            return ret;
        } else {
            return true;
        }
    }

    /** **/
    protected boolean testUpdate(final String identifier, final Evaluator evaluator) throws AbstractDatabaseException {
        if (this.isValidDB()) {
            // 准备数据
            final Record before = this.createRecord(identifier);
            final Record after = this.getDao().insert(before);
            // 更新数据
            this.updateRecord(after);
            final Record updateR = this.getDao().update(after);
            // 循环内equals检查
            final boolean ret = Collater.equal(after, updateR);
            evaluator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
            // 检查完将新数据删除掉
            this.getDao().delete(updateR);
            return ret;
        } else {
            return true;
        }
    }

    /** **/
    protected boolean testSelect(final String identifier, final Evaluator evaluator) throws AbstractDatabaseException {
        if (this.isValidDB()) {
            // 准备数据
            final Record before = this.createRecord(identifier);
            final Record after = this.getDao().insert(before);
            final MetaPolicy policy = after.policy();
            // 只有非COLLECTION才能调用其中一个方法
            boolean ret = true;
            if (MetaPolicy.COLLECTION == policy) {
                final Record selectR = this.getDao().selectById(after, after.idKV());
                // 检查最终结果
                ret = Collater.equal(after, selectR);
                evaluator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
                // 检查完毕将新插入的数据删除掉
                this.getDao().delete(selectR);
            } else {
                final Value<?> uniqueId = after.idKV().values().iterator().next();
                if (null != uniqueId) { // NOPMD
                    final Record selectR = this.getDao().selectById(after, uniqueId);
                    // 检查最终结果
                    ret = Collater.equal(after, selectR);
                    evaluator.evalTrue(message(TST_TF, Boolean.TRUE), ret);
                    // 检查完成，删除插入信息
                    this.getDao().delete(selectR);
                }
            }
            return ret;
        } else {
            return true;
        }
    }

    /** **/
    protected boolean testInvalidSelect(final String identifier, final Evaluator evaluator)
            throws AbstractDatabaseException {
        if (this.isValidDB()) {
            // 准备数据
            final Record before = this.createRecord(identifier);
            final MetaPolicy policy = before.policy();
            // 读取Record
            boolean ret;
            if (MetaPolicy.COLLECTION == policy) {
                final ConcurrentMap<String, Value<?>> ids = before.idKV();
                for (final String key : ids.keySet()) {
                    ids.put(key, V_ID);
                }
                final Record selectR = this.getDao().selectById(before, ids);
                ret = null == selectR;
            } else {
                final Record selectR = this.getDao().selectById(before, V_ID);
                ret = null == selectR;
            }
            // 判断结果
            evaluator.evalTrue(message(TST_NULL), ret);
            // 检查完成，删除插入数据
            return ret;
        } else {
            return true;
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
