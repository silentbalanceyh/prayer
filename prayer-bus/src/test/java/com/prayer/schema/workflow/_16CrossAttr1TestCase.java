package com.prayer.schema.workflow;

import static com.prayer.constant.Accessors.validator;
import static com.prayer.util.reflection.Instance.singleton;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.BTColumnTypeInvalidException;
import com.prayer.exception.schema.WrongTimeAttrException;
import com.prayer.facade.schema.verifier.DataValidator;

/**
 * 
 * @author Lang
 *
 */
public class _16CrossAttr1TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_16CrossAttr1TestCase.class);
    /** **/
    private static final String ERR_MSG_10024 = "[E10024] Cross ==> (Failure) There is unexpected exception!";

    // ~ Instance Fields =====================================
    /** **/
    private transient final DataValidator verifier;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public _16CrossAttr1TestCase() {
        this.verifier = singleton(validator());
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /**
     * 
     */
    @After
    public void setUp() {
        // For testP019Subtable1Rel10013();
        // 如果存在该表就删除
        if (null == verifier.verifyTable("TST_SUB_TST1")) {
            this.context.executeBatch("DROP TABLE TST_SUB_TST1;");
        }
    }

    // ~ Methods =============================================
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = WrongTimeAttrException.class)
    public void testP35Cross1Attr10024() throws AbstractSchemaException {
        testImport("zkeys/P035cross-10024-1.json", ERR_MSG_10024);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = WrongTimeAttrException.class)
    public void testP35Cross2Attr10024() throws AbstractSchemaException {
        testImport("zkeys/P035cross-10024-2.json", ERR_MSG_10024);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = WrongTimeAttrException.class)
    public void testP35Cross3Attr10024() throws AbstractSchemaException {
        testImport("zkeys/P035cross-10024-3.json", ERR_MSG_10024);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = WrongTimeAttrException.class)
    public void testP35Cross4Attr10024() throws AbstractSchemaException {
        testImport("zkeys/P035cross-10024-4.json", ERR_MSG_10024);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTColumnTypeInvalidException.class)
    public void testP35Cross5Attr10030() throws AbstractSchemaException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != verifier.verifyTable("TST_SUB_TST1")) {
            ret = this.context.executeBatch("CREATE TABLE TST_SUB_TST1( K_ID BIGINT PRIMARY KEY );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("zkeys/P035cross-10024-5.json",
                    "[E10030] Target column data type is invalid please verify the result!");
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
