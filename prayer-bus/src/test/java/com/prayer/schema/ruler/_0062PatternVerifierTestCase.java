package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _0062PatternVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * policy -> (GUID|INCREMENT|ASSIGNED|COLLECTION){1}
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0061Meta10003Policy1() throws AbstractException {
        testImport("P006meta-policy10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> policy. (1) ");
    }

    /**
     * table -> [A-Z]{2,4}\\_[A-Z\\_0-9]*
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0061Meta10003Table1() throws AbstractException {
        testImport("P006meta-table10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> table. (1) ");
    }

    /**
     * table -> [A-Z]{2,4}\\_[A-Z\\_0-9]*
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0061Meta10003Table2() throws AbstractException {
        testImport("P006meta-table10003-2.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> table. (2) ");
    }
    /**
     * table -> [A-Z]{2,4}\\_[A-Z\\_0-9]*
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0061Meta10003Table3() throws AbstractException {
        testImport("P006meta-table10003-3.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> table. (3) ");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
