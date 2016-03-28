package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0231RequiredTestCase extends AbstractVerifierTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP23Fields1String10001() throws AbstractException {
        testImport("types/P022field-Type1STRING-length10001.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP23Fields2Xml10001() throws AbstractException {
        testImport("types/P022field-Type2XML-length10001.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP23Fields3Json10001() throws AbstractException {
        testImport("types/P022field-Type3JSON-length10001.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception!");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
