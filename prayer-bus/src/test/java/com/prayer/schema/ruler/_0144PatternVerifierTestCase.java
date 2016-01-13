package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _0144PatternVerifierTestCase extends AbstractVerifierTestCase {
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
    public void testP0142Fields1columnType10003() throws AbstractException {
        testImport("fields/P0142field-columnType10003-1.json");
        failure("[E10003] Fields -> columnType ==> (Failure) Attribute 'columnType' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields2columnType10003() throws AbstractException {
        testImport("fields/P0142field-columnType10003-2.json");
        failure("[E10003] Fields -> columnType ==> (Failure) Attribute 'columnType' does not match pattern! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
