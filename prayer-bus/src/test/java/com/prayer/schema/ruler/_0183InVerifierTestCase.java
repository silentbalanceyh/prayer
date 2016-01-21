package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.PKNullableConflictException;
import com.prayer.exception.schema.PKUniqueConflictException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0183InVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = PKUniqueConflictException.class)
    public void testP0183KeyUnique10025() throws AbstractException {
        testImport("keys/P0183field-unique10025-1.json");
        failure("[E10025] Keys ==> Primary key unique must be true.");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PKNullableConflictException.class)
    public void testP0184KeyNullable10025() throws AbstractException {
        testImport("keys/P0184field-nullable10026-1.json");
        failure("[E10026] Keys ==> Primary key unique must be true.");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
