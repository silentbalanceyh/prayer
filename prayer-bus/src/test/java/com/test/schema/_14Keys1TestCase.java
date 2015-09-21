package com.test.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.exception.schema.ZeroLengthException;
import com.prayer.exception.schema.DuplicatedKeyException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.exception.schema.RequiredAttrMissingException;

/**
 * 
 * @author Lang
 *
 */
public class _14Keys1TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_14Keys1TestCase.class);
    /** **/
    private static final String ERR_MSG_10000 = "[E10001] Keys ==> (Failure) Required attributes missing!";
    /** **/
    private static final String ERR_MSG_10003 = "[E10003] keys ==> (Failure) Patterns do not match!";

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
    @Test(expected = ZeroLengthException.class)
    public void testP0251Keys1Arr10006() throws AbstractSchemaException {
        testImport("zkeys/P0251keys-10006-1.json", "[E10006] Keys ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = JsonTypeConfusedException.class)
    public void testP0252Keys1Arr10002() throws AbstractSchemaException {
        testImport("zkeys/P0252keys-10002-1.json", "[E10002] Keys ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0261Keys1Arr10001() throws AbstractSchemaException {
        testImport("zkeys/P0261keys-10001-1.json", ERR_MSG_10000);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0261Keys2Arr10001() throws AbstractSchemaException {
        testImport("zkeys/P0261keys-10001-2.json", ERR_MSG_10000);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0261Keys3Arr10001() throws AbstractSchemaException {
        testImport("zkeys/P0261keys-10001-3.json", ERR_MSG_10000);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0261Keys4Arr10001() throws AbstractSchemaException {
        testImport("zkeys/P0261keys-10001-4.json", ERR_MSG_10000);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0271Keys1Arr10003() throws AbstractSchemaException {
        testImport("zkeys/P0271keys-10003-1.json", ERR_MSG_10003);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0271Keys2Arr10003() throws AbstractSchemaException {
        testImport("zkeys/P0271keys-10003-2.json", ERR_MSG_10003);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0271Keys3Arr10003() throws AbstractSchemaException {
        testImport("zkeys/P0271keys-10003-3.json", ERR_MSG_10003);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = DuplicatedKeyException.class)
    public void testP0272Keys1Arr10018() throws AbstractSchemaException {
        testImport("zkeys/P0272keys-10018-1.json", "[E10018] Keys ==> (Failure) Attribute 'name' duplicated!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = ZeroLengthException.class)
    public void testP0281Keys1Arr10006() throws AbstractSchemaException {
        testImport("zkeys/P0281keys-10006-1.json", "[E10006] Keys ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = JsonTypeConfusedException.class)
    public void testP0282Keys1Arr10002() throws AbstractSchemaException {
        testImport("zkeys/P0282keys-10002-1.json",
                "[E10002] Keys ==> (Failure) JsonType conflicts and do mot match expected!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
