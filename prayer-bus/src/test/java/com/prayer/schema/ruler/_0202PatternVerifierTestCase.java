package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _0202PatternVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final String FK_PATTERN = "[E10003] Fields -> ( foreignkey = true ) ==> (Failure) One of optional attributes 'refId);failure(refTable' does not match the pattern! ";

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
    public void testP0202FKey1Rel10003() throws AbstractException {
        testImport("rels/P0202field-FK10003-1.json");
        failure(FK_PATTERN);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0202FKey2Rel10003() throws AbstractException {
        testImport("rels/P0202field-FK10003-2.json");
        failure(FK_PATTERN);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0202FKey3Rel10003() throws AbstractException {
        testImport("rels/P0202field-FK10003-3.json");
        failure(FK_PATTERN);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0202FKey4Rel10003() throws AbstractException {
        testImport("rels/P0202field-FK10003-4.json");
        failure(FK_PATTERN);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
