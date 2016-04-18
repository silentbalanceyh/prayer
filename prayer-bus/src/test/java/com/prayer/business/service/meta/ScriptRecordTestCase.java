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
public class ScriptRecordTestCase extends AbstractMetaServiceTool {
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
        this.executeTestCase("save/script-request1.json", Assert::assertTrue);
    }

    /**
     * Update对应的接口测试
     */
    @Test
    public void testUpdate() {
        this.executeTestCase("save/script-request2.json", "save/script-request2-updated.json", Assert::assertTrue);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
