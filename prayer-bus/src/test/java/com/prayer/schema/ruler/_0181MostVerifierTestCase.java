package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.PKPolicyConflictException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0181MostVerifierTestCase extends AbstractVerifierTool {
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
    @Test(expected = PKPolicyConflictException.class)
    public void testP0181Fields1PKeyPolicy10011() throws AbstractException {
        testImport("keys/P0181field-pKEYpolicy10011-1.json");
        failure("[E10011] Fields -> primarykey ==> (Failure) Attribute 'primarykey' does not match the policy definition! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PKPolicyConflictException.class)
    public void testP0181Fields2PKeyPolicy10011() throws AbstractException {
        testImport("keys/P0181field-pKEYpolicy10011-2.json");
        failure("[E10011] Fields -> primarykey ==> (Failure) Attribute 'primarykey' does not match the policy definition! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
