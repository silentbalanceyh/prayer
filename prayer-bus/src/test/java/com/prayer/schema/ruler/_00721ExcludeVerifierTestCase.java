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
public class _00721ExcludeVerifierTestCase extends AbstractVerifierTestCase {
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
    public void testP00721Meta10004EPartialExisting1() throws AbstractException {
        testImport("P00721meta-mappingE-PARTIAL10004-1.json");
        failure("[E10004] The attributes mustn't exist ! ( subkey, subtable -> subkey,subtable ) (1)");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00722Meta10004EPartialExisting2() throws AbstractException {
        testImport("P00722meta-mappingE-PARTIAL10004-2.json");
        failure("[E10004] The attributes mustn't exist ! ( seqname, seqinit, seqstep -> seqname ) (1)");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00722Meta10004EPartialExisting3() throws AbstractException {
        testImport("P00722meta-mappingE-PARTIAL10004-3.json");
        failure("[E10004] The attributes mustn't exist ! ( seqname, seqinit, seqstep -> seqinit ) (2)");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00722Meta10004EPartialExisting4() throws AbstractException {
        testImport("P00722meta-mappingE-PARTIAL10004-4.json");
        failure("[E10004] The attributes mustn't exist ! ( seqname, seqinit, seqstep -> seqstep ) (3)");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
