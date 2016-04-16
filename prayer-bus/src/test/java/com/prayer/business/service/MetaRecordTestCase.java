package com.prayer.business.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.AbstractService;
import com.prayer.facade.business.service.RecordService;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.model.crucial.MetaRecord;

/**
 * 
 * @author Lang
 *
 */
public class MetaRecordTestCase extends AbstractService {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaRecordTestCase.class);

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
        final ActResponse response = this.execute(this.getService()::save, "save/meta-request1.json");
        System.out.println(response.getResult());
    }
    // ~ Private Methods =====================================

    private RecordService getService() {
        return this.getRecordSrv(MetaRecord.class);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
