package com.prayer.business.service;

import com.prayer.business.AbstractService;
import com.prayer.facade.business.service.RecordService;
import com.prayer.facade.constant.Constants;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.model.crucial.MetaRecord;
import com.prayer.record.fun.Evaluator;
import com.prayer.util.business.Collater;
import com.prayer.util.string.StringKit;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMetaServiceTool extends AbstractService {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected void executeTestCase(final String dataFile, final Evaluator evaluator) {
        /** 1.Save Action调用 **/
        final ActResponse response = this.execute(this.getService()::save, dataFile);
        /** 2.无错误 **/
        evaluator.evalTrue("[T] MetaRecord Test (save) Act : " + dataFile, traceError(response.getError()));
        /** 3.Delete Action调用 **/
        final ActResponse deleted = this.executeWithData(response, dataFile, HttpMethod.DELETE,
                this.getService()::remove);
        /** 4.无错误 **/
        evaluator.evalTrue("[T] MetaRecord Purged by (remove) Act : " + dataFile, traceError(deleted.getError()));
    }

    /** **/
    protected void executeTestCase(final String dataFile, final String updatedFile, final Evaluator evaluator) {
        /** 1.Save Action调用 **/
        final ActResponse response = this.execute(this.getService()::save, dataFile);
        /** 2.无错误 **/
        evaluator.evalTrue("[T] MetaRecord Prepare (save) Act : " + dataFile, traceError(response.getError()));
        /** 3.Update Action调用 **/
        final ActResponse updated = this.executeWithData(response, updatedFile, HttpMethod.PUT,
                this.getService()::save);
        /** 4.无错误 **/
        evaluator.evalTrue("[T] MetaRecord Test (save) Act : " + dataFile, traceError(response.getError()));
        evaluator.evalTrue("[T] MetaRecord Test (save) Act result : " + dataFile,
                !Collater.equal(response.getResult(), updated.getResult()));
        evaluator.evalTrue("[T] MetaRecord Test (save) Act uniqueId : " + dataFile, StringKit
                .equals(response.getResult().getString(Constants.PID), updated.getResult().getString(Constants.PID)));
        /** 5.Delete Action调用 **/
        final ActResponse deleted = this.executeWithData(response, dataFile, HttpMethod.DELETE,
                this.getService()::remove);
        /** 6.无错误 **/
        evaluator.evalTrue("[T] MetaRecord Purged by (remove) Act : " + dataFile, traceError(deleted.getError()));
    }

    /** **/
    protected RecordService getService() {
        return this.getRecordSrv(MetaRecord.class);
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
