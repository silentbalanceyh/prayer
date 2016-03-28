package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.KeysNameSpecificationException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0257PatternVerifierTestCase extends AbstractVerifierTool {
    // ~ Static Fields =======================================
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
    @Test(expected = KeysNameSpecificationException.class)
    public void testP30Keys1Spec10019() throws AbstractException {
        testImport("zkeys/P030keys-10019-1.json");
        failure("[E10019] Keys ==> (Failure) Name specification error for this case!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = KeysNameSpecificationException.class)
    public void testP30Keys2Spec10019() throws AbstractException {
        testImport("zkeys/P030keys-10019-2.json");
        failure("[E10019] Keys ==> (Failure) Name specification error for this case!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = KeysNameSpecificationException.class)
    public void testP30Keys3Spec10019() throws AbstractException {
        testImport("zkeys/P030keys-10019-3.json");
        failure("[E10019] Keys ==> (Failure) Name specification error for this case!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
