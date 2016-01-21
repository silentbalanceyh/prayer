package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public class _00742PatternVerifierTestCase extends AbstractVerifierTestCase {
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
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP00742Meta10005ECombinatedValue1() throws AbstractException {
        testImport("P00742meta-mappingE-COMBINATED10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> subtable. (1) ");
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP00742Meta10005ECombinatedValue2() throws AbstractException {
        testImport("P00742meta-mappingE-COMBINATED10003-2.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> subtable. (2) ");
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP00742Meta10005ECombinatedValue3() throws AbstractException {
        testImport("P00742meta-mappingE-COMBINATED10003-3.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> subtable. (3) ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
