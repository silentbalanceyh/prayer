package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.OptionalAttrMorEException;

/**
 * 
 * @author Lang
 *
 */
public class _0101ExistingVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test(expected = OptionalAttrMorEException.class)
    public void testP010Meta10004PolicyIncrement1() throws AbstractException {
        testImport("P010meta-policyINCREMENT10004-1.json");
        failure("[E10004] The attributes must exist ! ( seqinit, seqstep ) (1)");
    }
    /** **/
    @Test(expected = OptionalAttrMorEException.class)
    public void testP010Meta10004PolicyIncrement2() throws AbstractException {
        testImport("P010meta-policyINCREMENT10004-2.json");
        failure("[E10004] The attributes must exist ! ( seqinit, seqstep ) (2)");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
