package com.prayer.business.service.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.AbstractService;
import com.prayer.facade.business.service.RecordService;
import com.prayer.facade.constant.Constants;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.model.crucial.MetaRecord;

/**
 * 
 * @author Lang
 *
 */
public class ScriptRecordTestCase extends AbstractService {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptRecordTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /**
     * Save Action对应的接口测试
     */
    @Test
    public void testSave() {
        /** 1.Save Action调用 **/
        final ActResponse response = this.execute(this.getService()::save, "save/script-request1.json");
        /** 2.无错误 **/
        assertNull(response.getError());
        /** 3.Delete Action调用 **/
        final ActResponse deleted = this.executeWithData(response, "save/script-request1.json",
                this.getService()::remove);
        /** 4.无错误 **/
        assertNull(deleted.getError());
    }

    /**
     * Update对应的接口测试
     */
    @Test
    public void testUpdate() {
        /** 1.Save Action调用 **/
        final ActResponse response = this.execute(this.getService()::save, "save/script-request2.json");
        /** 2.无错误 **/
        assertNull(response.getError());
        /** 3.Update Action调用 **/
        final ActResponse updated = this.executeWithData(response, "save/script-request2-updated.json",
                this.getService()::save);
        /** 4.无错误 **/
        assertNull(updated.getError());
        assertNotEquals(response.getResult(), updated.getResult());
        assertEquals(response.getResult().getString(Constants.PID), updated.getResult().getString(Constants.PID));
        /** 5.Delete Action调用 **/
        final ActResponse deleted = this.executeWithData(response, "save/script-request2.json",
                this.getService()::remove);
        /** 6.无错误 **/
        assertNull(deleted.getError());
    }
    // ~ Private Methods =====================================

    private RecordService getService() {
        return this.getRecordSrv(MetaRecord.class);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
