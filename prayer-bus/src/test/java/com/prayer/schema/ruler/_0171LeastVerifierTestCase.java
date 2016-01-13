package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.PKMissingException;

/**
 * 
 * @author Lang
 *
 */
public class _0171LeastVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = PKMissingException.class)
    public void testP017Fields1PrimaryKey10010() throws AbstractException {
        testImport("keys/P017field-primarykey10010-1.json");
        failure("[E10010] Fields -> primarykey ==> (Failure) Attribute 'primarykey' missing in the definition! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PKMissingException.class)
    public void testP017Fields2PrimaryKey10010() throws AbstractException {
        testImport("keys/P017field-primarykey10010-2.json");
        failure("[E10010] Fields -> primarykey ==> (Failure) All attributes 'primarykey' are conflict with specification to cause PK missing! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
