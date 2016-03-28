package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.WrongTimeAttrException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0263VerifierTestCase extends AbstractVerifierTool {
    // ~ Static Fields =======================================
    /** **/
    private static final String ERR_MSG_10024 = "[E10024] Cross ==> (Failure) There is unexpected exception!";
    // ~ Instance Fields =====================================

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = WrongTimeAttrException.class)
    public void testP35Cross1Attr10024() throws AbstractException {
        testImport("zkeys/P035cross-10024-1.json");
        failure(ERR_MSG_10024);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = WrongTimeAttrException.class)
    public void testP35Cross2Attr10024() throws AbstractException {
        testImport("zkeys/P035cross-10024-2.json");
        failure(ERR_MSG_10024);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = WrongTimeAttrException.class)
    public void testP35Cross3Attr10024() throws AbstractException {
        testImport("zkeys/P035cross-10024-3.json");
        failure(ERR_MSG_10024);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = WrongTimeAttrException.class)
    public void testP35Cross4Attr10024() throws AbstractException {
        testImport("zkeys/P035cross-10024-4.json");
        failure(ERR_MSG_10024);
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
