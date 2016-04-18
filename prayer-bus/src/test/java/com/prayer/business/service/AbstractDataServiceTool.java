package com.prayer.business.service;

import com.prayer.business.AbstractService;
import com.prayer.facade.business.service.RecordService;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.model.crucial.DataRecord;
import com.prayer.record.fun.Evaluator;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractDataServiceTool extends AbstractService {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param schemaFile
     * @param dataFile
     * @param evaluator
     */
    protected void executeTestCase(final String identifier, final String id, final String schemaFile,
            final String dataFile, final Evaluator evaluator) {
        /** 1.准备Schema **/
        boolean ret = this.prepareData(schemaFile);
        evaluator.evalTrue("[TS] Schema Preparing (save) Result : " + ret, ret);
        if (ret) {
            /** 2.执行Save操作 **/
            final ActResponse response = this.execute(this.getService()::save, dataFile);
            /** 3.无错误 **/
            evaluator.evalTrue("[T] DataRecord Test (save) Act : " + dataFile, traceError(response.getError()));
            /** 4.Delete Action调用 **/
            final ActResponse deleted = this.executeWithData(response, id, dataFile, HttpMethod.DELETE,
                    this.getService()::remove);
            /** 5.无错误 **/
            evaluator.evalTrue("[T] DataRecord Purged by (remove) Act : " + dataFile, traceError(deleted.getError()));
            /** 6.删除Schema **/
            ret = this.purgeData(identifier);
            evaluator.evalTrue("[TS] Schema Purging (save) Result : " + ret, ret);
        }
    }

    /** **/
    protected RecordService getService() {
        return this.getRecordSrv(DataRecord.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
