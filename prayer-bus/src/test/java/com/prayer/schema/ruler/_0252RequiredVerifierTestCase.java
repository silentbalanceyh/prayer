package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0252RequiredVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final String ERR_MSG_10000 = "[E10001] Keys ==> (Failure) Required attributes missing!";

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
    public void testP0251Keys1Arr10001() throws AbstractException {
        testImport("zkeys/P0261keys-10001-1.json");
        failure(ERR_MSG_10000);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0251Keys2Arr10001() throws AbstractException {
        testImport("zkeys/P0261keys-10001-2.json");
        failure(ERR_MSG_10000);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0251Keys3Arr10001() throws AbstractException {
        testImport("zkeys/P0261keys-10001-3.json");
        failure(ERR_MSG_10000);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0251Keys4Arr10001() throws AbstractException {
        testImport("zkeys/P0261keys-10001-4.json");
        failure(ERR_MSG_10000);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
