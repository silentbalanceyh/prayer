package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0051PatternVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * name -> [A-Z]{1}[A-Za-z0-9]+
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP005Meta10003Name1() throws AbstractException {
        testImport("P005meta-name10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> name. (1) ");
    }

    /**
     * name -> [A-Z]{1}[A-Za-z0-9]+
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP005Meta10003Name2() throws AbstractException {
        testImport("P005meta-name10003-2.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> name. (2) ");
    }

    /**
     * name -> [A-Z]{1}[A-Za-z0-9]+
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP005Meta10003Name3() throws AbstractException {
        testImport("P005meta-name10003-3.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> name. (3) ");
    }

    /**
     * name -> [A-Z]{1}[A-Za-z0-9]+
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP005Meta10003Name4() throws AbstractException {
        testImport("P005meta-name10003-4.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> name. (4) ");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
