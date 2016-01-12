package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _0052PatternVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /**
     * namespace -> [a-z]+(\\.[a-z]+)*
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP005Meta10003Namespace1() throws AbstractException {
        importFile("P005meta-namespace10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> namespace. (1) ");
    }

    /**
     * namespace -> [a-z]+(\\.[a-z]+)*
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP005Meta10003Namespace2() throws AbstractException {
        importFile("P005meta-namespace10003-2.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> namespace. (2) ");
    }

    /**
     * namespace -> [a-z]+(\\.[a-z]+)*
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP005Meta10003Namespace3() throws AbstractException {
        importFile("P005meta-namespace10003-3.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> namespace. (3) ");
    }

    /**
     * category -> (ENTITY|RELATION){1}
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP005Meta10003Category1() throws AbstractException {
        importFile("P005meta-category10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> category. (1) ");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
