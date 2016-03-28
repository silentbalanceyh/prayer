package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.DBMappingConflictException;
import com.prayer.exception.schema.VectorWrongException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0265MappingVerifierTestCase extends AbstractVerifierTool {
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
    @Test(expected = DBMappingConflictException.class)
    public void testP36Mapping110035() throws AbstractException{
        testImport("additional/P036mapping-10035-1.json");
        failure("[E10035] Database mapping error, please check your database mapping configuration.");
    }
    
    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = VectorWrongException.class)
    public void testP36Vector110036() throws AbstractException{
        testImport("additional/P036vector-10036-1.json");
        failure("[E10036] Database vector mapping configuration is wrong.");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
