package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0143PatternVerifierTestCase extends AbstractVerifierTool {
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
    public void testP0142Fields1columnName10003() throws AbstractException {
        testImport("fields/P0142field-columnName10003-1.json");
        failure("[E10003] Fields -> columnName ==> (Failure) Attribute 'columnName' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields2columnName10003() throws AbstractException {
        testImport("fields/P0142field-columnName10003-2.json");
        failure("[E10003] Fields -> columnName ==> (Failure) Attribute 'columnName' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields3columnName10003() throws AbstractException {
        testImport("fields/P0142field-columnName10003-3.json");
        failure("[E10003] Fields -> columnName ==> (Failure) Attribute 'columnName' does not match pattern! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
