package com.prayer;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prayer.business.impl.deployment.SchemaBllor;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.dao.impl.data.DataRecordDalor;
import com.prayer.facade.business.deployment.SchemaService;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.record.Record;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.business.ServiceResult;
import com.prayer.model.crucial.DataRecord;
import com.prayer.model.meta.database.PEField;

import jodd.util.StringUtil;

/**
 * Dao数据层测试基类
 * 
 * @author Lang
 *
 */
public abstract class AbstractRDaoTestTool extends AbstractTestTool {
    // ~ Static Fields =======================================
    /** **/
    protected static final String DAO_DATA_PATH = "/schema/data/json/dao/";
    // ~ Instance Fields =====================================
    /** Schema服务层接口 **/
    private transient final SchemaService service;
    /** Record数据访问层 **/
    private transient final RecordDao recordDao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractRDaoTestTool() {
        super();
        this.service = instance(SchemaSevImpl.class.getName());
        this.recordDao = singleton(DataRecordDalor.class);
    }

    // ~ Abstract Methods ====================================
    /** 获取数据库类型 **/
    protected abstract String getDbCategory();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 当前访问的Database是否合法 **/
    protected boolean isValidDB() {
        return StringUtil.equals(getDbCategory(), Resources.DB_CATEGORY);
    }

    /** 获取服务层访问接口 **/
    protected SchemaService getService() {
        return this.service;
    }

    /** 从Json到H2并且读取Metadata同步到对应数据库 **/
    protected ServiceResult<Schema> syncMetadata(final String filePath, final String identifier) {
        ServiceResult<Schema> finalRet = new ServiceResult<>();
        if (this.isValidDB()) {
            // 基础数据
            final ServiceResult<Schema> syncRet = this.getService().importSchema(DAO_DATA_PATH + filePath);
            if (ResponseCode.SUCCESS == syncRet.getResponseCode()) {
                finalRet = this.getService().syncMetadata(syncRet.getResult());
            } else {
                peError(getLogger(), syncRet.getServiceError());
                finalRet = syncRet;
            }
        }
        return finalRet;
    }

    /** **/
    protected Record getRecord(final String identifier) {
        final Record record = instance(DataRecord.class.getName(), identifier);
        for (final String field : record.fields().keySet()) {
            try {
                record.set(field, Assistant.generate(record.fields().get(field), false));
            } catch (AbstractDatabaseException ex) {
                peError(getLogger(), ex);
            }
        }
        return record;
    }

    /** **/
    protected void updateRecord(final Record record) {
        // ID不可更新
        final List<PEField> pkeys = record.idschema();
        final Set<String> ids = new HashSet<>();
        for (final PEField pkey : pkeys) {
            ids.add(pkey.getName());
        }
        // 和添加不一样，这里面存在一个判断
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

    /** **/
    protected RecordDao getRecordDao() {
        return this.recordDao;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
