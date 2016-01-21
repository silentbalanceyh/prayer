package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.DuplicatedKeyException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0258DuplicatedVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = DuplicatedKeyException.class)
    public void testP0272Keys1Arr10018() throws AbstractException {
        testImport("zkeys/P0272keys-10018-1.json");
        failure("[E10018] Keys ==> (Failure) Attribute 'name' duplicated!");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
