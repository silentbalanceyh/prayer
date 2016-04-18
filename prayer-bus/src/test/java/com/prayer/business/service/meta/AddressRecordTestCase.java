package com.prayer.business.service.meta;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.service.AbstractMetaTool;

/**
 * 
 * @author Lang
 *
 */
public class AddressRecordTestCase extends AbstractMetaTool {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressRecordTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Save Action对应的接口测试
     */
    @Test
    public void testSave() {
        this.executeTestCase("save/address-request1.json", Assert::assertTrue);
    }

    /**
     * Update对应的接口测试
     */
    @Test
    public void testUpdate() {
        this.executeTestCase("save/address-request2.json", "save/address-request2-updated.json", Assert::assertTrue);
    }

    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
