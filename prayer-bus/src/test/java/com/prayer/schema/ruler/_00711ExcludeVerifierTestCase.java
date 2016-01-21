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
public class _00711ExcludeVerifierTestCase extends AbstractVerifierTestCase {
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
    public void testP00711Meta10004RelationExisting1() throws AbstractException {
        testImport("P00711meta-categoryRELATION10004-1.json");
        failure("[E10004] The attributes mustn't exist ! ( subkey, subtable -> subkey) (1)");
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00711Meta10004RelationExisting2() throws AbstractException {
        testImport("P00711meta-categoryRELATION10004-2.json");
        failure("[E10004] The attributes mustn't exist ! ( subkey, subtable -> subtable) (2)");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
