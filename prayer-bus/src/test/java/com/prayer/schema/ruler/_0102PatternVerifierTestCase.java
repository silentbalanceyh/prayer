package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _0102PatternVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test(expected = PatternNotMatchException.class)
    public void testP011Meta10005PolicyIncrement1() throws AbstractException {
        testImport("P011meta-policyINCREMENT10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> seqinit. (1) ");
    }
    /** **/
    @Test(expected = PatternNotMatchException.class)
    public void testP011Meta10005PolicyIncrement2() throws AbstractException {
        testImport("P011meta-policyINCREMENT10003-2.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> seqstep. (1) ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
