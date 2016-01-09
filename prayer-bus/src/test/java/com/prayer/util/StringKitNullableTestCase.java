package com.prayer.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractTestTool;
import com.prayer.util.string.StringKit;

/**
 * 
 * @author Lang
 *
 */
public class StringKitNullableTestCase extends AbstractTestTool {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StringKitNullableTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    protected Logger getLogger() {
        return LOGGER;
    }

    /** **/
    protected Class<?> getTarget() {
        return StringKit.class;
    }

    // ~ Methods =============================================

    /** **/
    @Test
    public void testT00005MisNil() {
        assertTrue(message(TST_TF,Boolean.TRUE),StringKit.isNil(null));
    }
    /** **/
    @Test
    public void testT00006MisNil() {
        assertTrue(message(TST_TF,Boolean.TRUE),StringKit.isNil(" "));
    }
    /** **/
    @Test
    public void testT00007MisNil() {
        assertTrue(message(TST_TF,Boolean.TRUE),StringKit.isNil(""));
    }
    /** **/
    @Test
    public void testT00008MisNil() {
        assertFalse(message(TST_TF,Boolean.FALSE),StringKit.isNil("NULL"));
    }
    /** **/
    @Test
    public void testT00009MisNonNil() {
        assertFalse(message(TST_TF,Boolean.FALSE),StringKit.isNonNil(null));
    }
    /** **/
    @Test
    public void testT00010MisNonNil() {
        assertFalse(message(TST_TF,Boolean.FALSE),StringKit.isNonNil(" "));
    }
    /** **/
    @Test
    public void testT00011MisNonNil() {
        assertFalse(message(TST_TF,Boolean.FALSE),StringKit.isNonNil(""));
    }
    /** **/
    @Test
    public void testT00012MisNonNil() {
        assertTrue(message(TST_TF,Boolean.TRUE),StringKit.isNonNil("NULL"));
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
