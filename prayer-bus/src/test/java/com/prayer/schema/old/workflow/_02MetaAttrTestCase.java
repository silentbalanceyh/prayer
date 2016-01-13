package com.prayer.schema.old.workflow;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.exception.schema.UnsupportAttrException;

/**
 * 
 * @author Lang
 * @see
 */
@Ignore
public class _02MetaAttrTestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_02MetaAttrTestCase.class);

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
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Name() throws AbstractSchemaException {
        testImport("P004meta-name10001.json", "[E10001] Meta -> name ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Namespace() throws AbstractSchemaException {
        testImport("P004meta-namespace10001.json",
                "[E10001] Meta -> namespace ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Category() throws AbstractSchemaException {
        testImport("P004meta-category10001.json",
                "[E10001] Meta -> category ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Table() throws AbstractSchemaException {
        testImport("P004meta-table10001.json", "[E10001] Meta -> table ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Identifier() throws AbstractSchemaException {
        testImport("P004meta-identifier10001.json",
                "[E10001] Meta -> identifier ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Mapping() throws AbstractSchemaException {
        testImport("P004meta-mapping10001.json",
                "[E10001] Meta -> mapping ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Policy() throws AbstractSchemaException {
        testImport("P004meta-policy10001.json", "[E10001] Meta -> policy ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = UnsupportAttrException.class)
    public void testP004Meta10002Attr() throws AbstractSchemaException {
        testImport("P004meta-attr10002.json",
                "[E10001] Meta (Unsupported) ==> (Failure) There is unexpected exception!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
