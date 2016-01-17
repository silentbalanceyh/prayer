package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.OptionalAttrMorEException;

/**
 * 
 * @author Lang
 *
 */
public class _00731ExcludeVerifierTestCase extends AbstractVerifierTestCase {
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
    public void testP00731Meta10004EDirectExisting1() throws AbstractException {
        testImport("P00731meta-mappingE-DIRECT10004-1.json");
        failure("[E10004] The attributes mustn't exist ! ( subkey, subtable -> subkey) (1)");
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00731Meta10004EDirectExisting2() throws AbstractException {
        testImport("P00731meta-mappingE-DIRECT10004-2.json");
        failure("[E10004] The attributes mustn't exist ! ( subkey, subtable -> subtable) (2)");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}