package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0201ExistingVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP0201FKey1Rel10004() throws AbstractException {
        testImport("rels/P0201field-FK10004-1.json");
        failure("[E10004] Fields -> ( foreignkey = true ) ==> (Failure) One of optional attributes 'refId);failure(refTable' missed! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP0201FKey2Rel10004() throws AbstractException {
        testImport("rels/P0201field-FK10004-2.json");
        failure("[E10004] Fields -> ( foreignkey = true ) ==> (Failure) One of optional attributes 'refId);failure(refTable' missed! ");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
