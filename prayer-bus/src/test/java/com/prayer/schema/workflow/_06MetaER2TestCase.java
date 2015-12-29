package com.prayer.schema.workflow;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.DuplicatedTablesException;
import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 * @see
 */
public class _06MetaER2TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_06MetaER2TestCase.class);

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
    public void testP00731Meta10004EDirectExisting1() throws AbstractSchemaException {
        testImport("P00731meta-mappingE-DIRECT10004-1.json",
                "[E10004] Meta -> category (ENTITY), mapping (DIRECT) ==> Optional {subkey,subtable} Attribute Error!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00731Meta10004EDirectExisting2() throws AbstractSchemaException {
        testImport("P00731meta-mappingE-DIRECT10004-2.json",
                "[E10004] Meta -> category (ENTITY), mapping (DIRECT) ==> Optional {subkey,subtable} Attribute Error!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00741Meta10004ECombinatedExisting1() throws AbstractSchemaException {
        testImport("P00741meta-mappingE-COMBINATED10004-1.json",
                "[E10004] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subkey,subtable} Attribute Error!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP00741Meta10004ECombinatedExisting2() throws AbstractSchemaException {
        testImport("P00741meta-mappingE-COMBINATED10004-2.json",
                "[E10004] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subkey,subtable} Attribute Error!");
    }

    /**
     * 表名以下划线开头
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP00742Meta10005ECombinatedValue1() throws AbstractSchemaException {
        testImport("P00742meta-mappingE-COMBINATED10003-1.json",
                "[E10003] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute must be matching!");
    }

    /**
     * 表名前缀长度不对
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP00742Meta10005ECombinatedValue2() throws AbstractSchemaException {
        testImport("P00742meta-mappingE-COMBINATED10003-2.json",
                "[E10003] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute must be matching!");
    }

    /**
     * 包含了特殊符号
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP00742Meta10005ECombinatedValue3() throws AbstractSchemaException {
        testImport("P00742meta-mappingE-COMBINATED10003-3.json",
                "[E10003] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute must be matching!");
    }

    /**
     * 包含了特殊符号
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = DuplicatedTablesException.class)
    public void testP00743Meta10020ECombinatedValue4() throws AbstractSchemaException {
        testImport("P00743meta-mappingE-COMBINATED10020-1.json",
                "[E10020] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute mustn't be the same as {table}!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
