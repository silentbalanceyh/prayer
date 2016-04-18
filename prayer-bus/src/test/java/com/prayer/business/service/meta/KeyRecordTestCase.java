package com.prayer.business.service.meta;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.service.AbstractMetaServiceTool;

/**
 * 
 * @author Lang
 *
 */
public class KeyRecordTestCase extends AbstractMetaServiceTool {
    // ~ Static Fields =======================================


    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyRecordTestCase.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    // ~ Methods =============================================
    /**
     * Save Action测试接口
     */
    @Test
    public void testSave(){
        this.executeTestCase("save/key-request1.json", Assert::assertTrue);
    }
    /**
     * 
     */
    @Test
    public void testUpdate(){
        this.executeTestCase("save/key-request2.json", "save/key-request2-updated.json", Assert::assertTrue);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
