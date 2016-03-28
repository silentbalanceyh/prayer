package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.FKReferenceSameException;
import com.prayer.exception.schema.JKeyConstraintInvalidException;
import com.prayer.exception.schema.JTColumnNotExistingException;
import com.prayer.exception.schema.JTColumnTypeInvalidException;
import com.prayer.fantasm.exception.AbstractException;

/** **/
public class _0225SchemaVerifierTestCase extends AbstractVerifierTool {
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
    @Test(expected = JTColumnNotExistingException.class)
    public void testP22FKey1Target10031() throws AbstractException {
        testImport("rels/P0213field-FkCType10031-1.json");
        failure("[E10031] Target column does not exist in schema file);failure( please verify the result!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = JKeyConstraintInvalidException.class)
    public void testP22FKey1Target10032() throws AbstractException {
        testImport("rels/P0213field-FkCType10032-1.json");
        failure("[E10032] Target column constraint is invalid in schema file);failure( please verify the result!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = JTColumnTypeInvalidException.class)
    public void testP22FKey1Target10033() throws AbstractException {
        testImport("rels/P0213field-FkCType10033-1.json");
        failure("[E10033] Target column data type is invalid in schema file);failure( please verify the result!");
    }
    
    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = FKReferenceSameException.class)
    public void testP22FKey1Target10034() throws AbstractException {
        testImport("rels/P0213field-FkCType10034-1.json");
        failure("[E10033] Target column data type is invalid in schema file);failure( please verify the result!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
