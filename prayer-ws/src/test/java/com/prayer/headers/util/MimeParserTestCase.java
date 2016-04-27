package com.prayer.headers.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.prayer.vertx.util.MimeParser;

/**
 * 
 * @author Lang
 *
 */
public class MimeParserTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test
    public void testMatch1() {
        assertTrue(MimeParser.wideMatch("application/json", "application/json"));
    }
    /** **/
    @Test
    public void testMatch2() {
        assertTrue(MimeParser.wideMatch("application/json", "application/*"));
    }
    /** **/
    @Test
    public void testMatch3() {
        assertTrue(MimeParser.wideMatch("application/json", "*/json"));
    }
    /** **/
    @Test
    public void testMatch4(){
        assertFalse(MimeParser.wideMatch("xxx", "test"));
    }
    /** **/
    @Test
    public void testMatch5(){
        assertTrue(MimeParser.wideMatch("application/json", "*/*"));
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
