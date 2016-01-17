package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _0253PatternVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final String ERR_MSG_10003 = "[E10003] keys ==> (Failure) Patterns do not match!";

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
    @Test(expected = PatternNotMatchException.class)
    public void testP0251Keys1Arr10003() throws AbstractException {
        testImport("zkeys/P0271keys-10003-1.json");
        failure(ERR_MSG_10003);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0251Keys2Arr10003() throws AbstractException {
        testImport("zkeys/P0271keys-10003-2.json");
        failure(ERR_MSG_10003);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0251Keys3Arr10003() throws AbstractException {
        testImport("zkeys/P0271keys-10003-3.json");
        failure(ERR_MSG_10003);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
