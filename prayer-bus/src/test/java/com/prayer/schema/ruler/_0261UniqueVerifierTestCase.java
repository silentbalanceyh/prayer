package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.MultiForPKPolicyException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0261UniqueVerifierTestCase extends AbstractVerifierTool {
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
    @Test(expected = MultiForPKPolicyException.class)
    public void testP31Cross1Spec10022() throws AbstractException {
        testImport("zkeys/P031cross-10022-1.json");
        failure("[E10022] Cross ==> (Failure) Primary Key policy conflict with 'multi'!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = MultiForPKPolicyException.class)
    public void testP31Cross2Spec10022() throws AbstractException {
        testImport("zkeys/P031cross-10022-2.json");
        failure("[E10022] Cross ==> (Failure) Primary Key policy conflict with 'multi'!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
