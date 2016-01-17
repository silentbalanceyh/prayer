package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.RequiredAttrMissingException;

/**
 * 
 * @author Lang
 *
 */
public class _023RequiredTestCase extends AbstractVerifierTestCase {
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
    public void testP22Fields1String10001() throws AbstractException {
        testImport("types/P022field-Type1STRING-length10001.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP22Fields2Xml10001() throws AbstractException {
        testImport("types/P022field-Type2XML-length10001.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP22Fields3Json10001() throws AbstractException {
        testImport("types/P022field-Type3JSON-length10001.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception!");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
