package com.prayer.schema.workflow;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.JKeyConstraintInvalidException;
import com.prayer.exception.schema.JTColumnNotExistingException;
import com.prayer.exception.schema.JTColumnTypeInvalidException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _12TypePattern1TestCase extends AbstractSchemaTestCase {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_12TypePattern1TestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = JTColumnNotExistingException.class)
    public void testP23FKey1Target10031() throws AbstractSchemaException {
            testImport("rels/P0213field-FkCType10031-1.json",
                    "[E10031] Target column does not exist in schema file, please verify the result!");
    }
    
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = JKeyConstraintInvalidException.class)
    public void testP23FKey1Target10032() throws AbstractSchemaException {
            testImport("rels/P0213field-FkCType10032-1.json",
                    "[E10032] Target column constraint is invalid in schema file, please verify the result!");
    }
    
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = JTColumnTypeInvalidException.class)
    public void testP23FKey1Target10033() throws AbstractSchemaException {
            testImport("rels/P0213field-FkCType10033-1.json",
                    "[E10033] Target column data type is invalid in schema file, please verify the result!");
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP23Fields1Pattern10003() throws AbstractSchemaException {
        testImport("types/P023field-Type1STRING-Pattern10003-1.json",
                "[E10003] Fields ==> (Failure) There is unexpected exception!");
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
