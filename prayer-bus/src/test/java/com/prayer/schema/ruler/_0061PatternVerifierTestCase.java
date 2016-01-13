package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _0061PatternVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * identifier -> [a-z]{2,4}(\\.[a-z0-9]{2,})+
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0061Meta10003Id1() throws AbstractException {
        testImport("P006meta-id10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> identifier. (1) ");
    }

    /**
     * identifier -> [a-z]{2,4}(\\.[a-z0-9]{2,})+
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0061Meta10003Id2() throws AbstractException {
        testImport("P006meta-id10003-2.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> identifier. (2) ");
    }

    /**
     * identifier -> [a-z]{2,4}(\\.[a-z0-9]{2,})+
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0061Meta10003Id3() throws AbstractException {
        testImport("P006meta-id10003-3.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> identifier. (3) ");
    }

    /**
     * mapping -> (DIRECT|COMBINATED|PARTIAL){1}
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0061Meta10003Mapping1() throws AbstractException {
        testImport("P006meta-mapping10003-1.json");
        failure("[E10003] Pattern are not matching ! __meta__ -> mapping. (1) ");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
