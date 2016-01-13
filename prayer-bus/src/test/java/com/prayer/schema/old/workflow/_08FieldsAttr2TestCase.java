package com.prayer.schema.old.workflow;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.DuplicatedAttrException;
import com.prayer.exception.schema.DuplicatedColumnException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 * @see
 */
@Ignore
public class _08FieldsAttr2TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_08FieldsAttr2TestCase.class);

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
    public void testP0142Fields1columnName10003() throws AbstractSchemaException {
        testImport("fields/P0142field-columnName10003-1.json",
                "[E10003] Fields -> columnName ==> (Failure) Attribute 'columnName' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields2columnName10003() throws AbstractSchemaException {
        testImport("fields/P0142field-columnName10003-2.json",
                "[E10003] Fields -> columnName ==> (Failure) Attribute 'columnName' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields3columnName10003() throws AbstractSchemaException {
        testImport("fields/P0142field-columnName10003-3.json",
                "[E10003] Fields -> columnName ==> (Failure) Attribute 'columnName' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields1columnType10003() throws AbstractSchemaException {
        testImport("fields/P0142field-columnType10003-1.json",
                "[E10003] Fields -> columnType ==> (Failure) Attribute 'columnType' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields2columnType10003() throws AbstractSchemaException {
        testImport("fields/P0142field-columnType10003-2.json",
                "[E10003] Fields -> columnType ==> (Failure) Attribute 'columnType' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = DuplicatedAttrException.class)
    public void testP015Fields1name10007() throws AbstractSchemaException {
        testImport("fields/P015field-name10007-1.json",
                "[E10007] Fields -> name ==> (Failure) Attribute 'name' exists more than once ( Duplicated )! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = DuplicatedColumnException.class)
    public void testP016Fields1columnName10008() throws AbstractSchemaException {
        testImport("fields/P016field-columnName10008-1.json",
                "[E10008] Fields -> columnName ==> (Failure) Database column 'columnName' exists more than once ( Duplicated )! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields1type10003() throws AbstractSchemaException {
        testImport("fields/P0142field-type10003-1.json",
                "[E10003] Fields -> columnType ==> (Failure) Attribute 'type' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields2type10003() throws AbstractSchemaException {
        testImport("fields/P0142field-type10003-2.json",
                "[E10003] Fields -> columnType ==> (Failure) Attribute 'type' does not match pattern! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0142Fields3type10003() throws AbstractSchemaException {
        testImport("fields/P0142field-type10003-3.json",
                "[E10003] Fields -> columnType ==> (Failure) Attribute 'type' does not match pattern! ");
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
