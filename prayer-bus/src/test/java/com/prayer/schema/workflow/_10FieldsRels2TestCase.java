package com.prayer.schema.workflow;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.FKAttrTypeException;
import com.prayer.exception.schema.FKColumnTypeException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _10FieldsRels2TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_10FieldsRels2TestCase.class);
    /** **/
    private static final String FK_PATTERN = "[E10003] Fields -> ( foreignkey = true ) ==> (Failure) One of optional attributes 'refId,refTable' does not match the pattern! ";
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
    @Test(expected = PatternNotMatchException.class)
    public void testP0202FKey3Rel10003() throws AbstractSchemaException {
        testImport("rels/P0202field-FK10003-3.json", FK_PATTERN);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0202FKey4Rel10003() throws AbstractSchemaException {
        testImport("rels/P0202field-FK10003-4.json", FK_PATTERN);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = FKAttrTypeException.class)
    public void testP0211FKey1Type10014() throws AbstractSchemaException {
        testImport("rels/P0211field-FkType10014-1.json",
                "[E10014] Fields -> ( foreignkey = true ) ==> (Failure) Attribute 'type' of FK is wrong! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = FKAttrTypeException.class)
    public void testP0211FKey2Type10014() throws AbstractSchemaException {
        testImport("rels/P0211field-FkType10014-2.json",
                "[E10014] Fields -> ( foreignkey = true ) ==> (Failure) Attribute 'type' of FK is wrong! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = FKColumnTypeException.class)
    public void testP0212FKey1Type10015() throws AbstractSchemaException {
        testImport("rels/P0212field-FkCType10015-1.json",
                "[E10015] Fields -> ( foreignkey = true ) ==> (Failure) Attribute 'columnType' of FK is wrong! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = FKColumnTypeException.class)
    public void testP0212FKey2Type10015() throws AbstractSchemaException {
        testImport("rels/P0212field-FkCType10015-2.json",
                "[E10015] Fields -> ( foreignkey = true ) ==> (Failure) Attribute 'columnType' of FK is wrong! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
