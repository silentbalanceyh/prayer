package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.PKColumnTypePolicyException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0182InVerifierTestCase extends AbstractVerifierTool {
    // ~ Static Fields =======================================
    private static final String E10012_STR = "[E10012] Fields ==> primary key type conflicts! ";

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
    @Test(expected = PKColumnTypePolicyException.class)
    public void testP0182FieldsT1PKeyPolicy10012() throws AbstractException {
        testImport("keys/P0182field-TpKEYpolicy10012-1.json");
        failure(E10012_STR);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PKColumnTypePolicyException.class)
    public void testP0182FieldsT2PKeyPolicy10012() throws AbstractException {
        testImport("keys/P0182field-TpKEYpolicy10012-2.json");
        failure(E10012_STR);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PKColumnTypePolicyException.class)
    public void testP0182FieldsT3PKeyPolicy10012() throws AbstractException {
        testImport("keys/P0182field-TpKEYpolicy10012-3.json");
        failure(E10012_STR);
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = PKColumnTypePolicyException.class)
    public void testP0182FieldsT4PKeyPolicy10012() throws AbstractException {
        testImport("keys/P0182field-TpKEYpolicy10012-4.json");
        failure(E10012_STR);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
