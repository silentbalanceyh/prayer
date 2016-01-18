package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PKNotOnlyOneException;

/**
 * 
 * @author Lang
 *
 */
public class _0259MostVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = PKNotOnlyOneException.class)
    public void testP32Cross1Spec10009() throws AbstractException {
        testImport("zkeys/P032cross-10009-1.json");
        failure("[E10009] Cross ==> (Failure) Primary Key must appears once in __keys__ definition!");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
