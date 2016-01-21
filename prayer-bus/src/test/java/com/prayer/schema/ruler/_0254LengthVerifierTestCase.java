package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.ZeroLengthException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0254LengthVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = ZeroLengthException.class)
    public void testP0281Keys1Arr10006() throws AbstractException {
        testImport("zkeys/P0281keys-10006-1.json");
        failure("[E10006] Keys ==> (Failure) There is unexpected exception!");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
