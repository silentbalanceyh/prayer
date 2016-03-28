package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.UnsupportAttrException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0233UnsupportVerifierTestCase extends AbstractVerifierTool {
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
    @Test(expected = UnsupportAttrException.class)
    public void testP23Fields1Support10017() throws AbstractException {
        testImport("types/P024field-Type1STRING-Support10017-1.json");
        failure("[E10017] Fields ==> (Failure) There is unexpected exception!");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
