package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public class _00741ExcludeVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00741Meta10004ECombinatedExisting1() throws AbstractException {
        testImport("P00741meta-mappingE-COMBINATED10004-1.json");
        failure("[E10004] The attributes mustn't exist ! ( subkey, subtable -> subkey) (1)");
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00741Meta10004ECombinatedExisting2() throws AbstractException {
        testImport("P00741meta-mappingE-COMBINATED10004-2.json");
        failure("[E10004] The attributes mustn't exist ! ( subkey, subtable -> subtable) (2)");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
