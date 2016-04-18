package com.prayer.business.service.record;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.service.AbstractDataServiceTool;

/**
 * 
 * @author Lang
 *
 */
public class Record1TestCase extends AbstractDataServiceTool {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Record1TestCase.class);

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
    /** **/
    @Test
    public void testSave() {
        this.executeTestCase("tst.mod.record1", "tId", "record/request1/schema.json", "record/request1/data.json",
                Assert::assertTrue);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
