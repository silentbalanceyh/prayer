package com.prayer.schema.workflow;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.InvalidValueException;
import com.prayer.exception.schema.OptionalAttrMorEException;

/**
 * 
 * @author Lang
 * @see
 */
public class _05MetaER1TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_05MetaER1TestCase.class);
    /** **/
    private static final String ERR_OPTIONAL_MSG = "[E10004] Meta -> category (RELATION) ==> Optional {subkey,subtable} Attribute Error!";

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
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00711Meta10004RelationExisting1() throws AbstractSchemaException {
        testImport("P00711meta-categoryRELATION10004-1.json", ERR_OPTIONAL_MSG);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00711Meta10004RelationExisting2() throws AbstractSchemaException {
        testImport("P00711meta-categoryRELATION10004-2.json", ERR_OPTIONAL_MSG);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00721Meta10004EPartialExisting1() throws AbstractSchemaException {
        testImport("P00721meta-mappingE-PARTIAL10004-1.json",
                "[E10004] Meta -> category (ENTITY), mapping (PARTIAL) ==> Optional {subkey,subtable} Attribute Error!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00722Meta10004EPartialExisting2() throws AbstractSchemaException {
        testImport("P00722meta-mappingE-PARTIAL10004-2.json",
                "[E10004] Meta -> category (ENTITY), mapping (PARTIAL) ==> Optional {seqname} Attribute Error!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00722Meta10004EPartialExisting3() throws AbstractSchemaException {
        testImport("P00722meta-mappingE-PARTIAL10004-3.json",
                "[E10004] Meta -> category (ENTITY), mapping (PARTIAL) ==> Optional {seqstep} Attribute Error!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00722Meta10004EPartialExisting4() throws AbstractSchemaException {
        testImport("P00722meta-mappingE-PARTIAL10004-4.json",
                "[E10004] Meta -> category (ENTITY), mapping (PARTIAL) ==> Optional {seqinit} Attribute Error!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = InvalidValueException.class)
    public void testP00712Meta10005RelationValue1() throws AbstractSchemaException {
        testImport("P00712meta-mappingRELATION10005-1.json",
                "[E10005] Meta -> mapping (RELATION) ==> Attribute mapping must be DIRECT!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = InvalidValueException.class)
    public void testP00713Meta10005RelationValue2() throws AbstractSchemaException {
        testImport("P00713meta-mappingRELATION10005-2.json",
                "[E10005] Meta -> policy (RELATION) ==> Attribute policy mustn't be COLLECTION, ASSIGNED!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = InvalidValueException.class)
    public void testP00723Meta10005EPartialValue1() throws AbstractSchemaException {
        testImport("P00723meta-mappingE-PARTIAL10005-1.json",
                "[E10005] Meta -> category (ENTITY), mapping (PARTIAL) ==> Attribute policy must be ASSIGNED!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
