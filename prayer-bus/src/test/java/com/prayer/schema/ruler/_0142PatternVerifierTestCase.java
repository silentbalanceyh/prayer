package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0142PatternVerifierTestCase extends AbstractVerifierTestCase {
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
    public void testP0142Fields1name10003() throws AbstractException {
        testImport("fields/P0142field-name10003-1.json");
        failure("[E10003] Fields -> name ==> (Failure) Attribute 'name' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields2name10003() throws AbstractException {
        testImport("fields/P0142field-name10003-2.json");
        failure("[E10003] Fields -> name ==> (Failure) Attribute 'name' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields3name10003() throws AbstractException {
        testImport("fields/P0142field-name10003-3.json");
        failure("[E10003] Fields -> name ==> (Failure) Attribute 'name' does not match pattern! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
