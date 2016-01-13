package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _0145PatternVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields1type10003() throws AbstractException {
        testImport("fields/P0142field-type10003-1.json");
        failure("[E10003] Fields -> columnType ==> (Failure) Attribute 'type' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields2type10003() throws AbstractException {
        testImport("fields/P0142field-type10003-2.json");
        failure("[E10003] Fields -> columnType ==> (Failure) Attribute 'type' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields3type10003() throws AbstractException {
        testImport("fields/P0142field-type10003-3.json");
        failure("[E10003] Fields -> columnType ==> (Failure) Attribute 'type' does not match pattern! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
