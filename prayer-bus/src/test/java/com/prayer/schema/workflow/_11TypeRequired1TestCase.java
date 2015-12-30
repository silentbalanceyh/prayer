package com.prayer.schema.workflow;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.cv.Accessors.validator;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.BFKConstraintInvalidException;
import com.prayer.exception.schema.BTColumnNotExistingException;
import com.prayer.exception.schema.BTColumnTypeInvalidException;
import com.prayer.exception.schema.BTableNotExistingException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.facade.schema.DataValidator;
import com.prayer.util.cv.Constants;

/**
 * 
 * @author Lang
 *
 */
public class _11TypeRequired1TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_11TypeRequired1TestCase.class);

    // ~ Instance Fields =====================================
    /** **/
    private transient final DataValidator verifier;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public _11TypeRequired1TestCase() {
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

    // ~ Methods =============================================
    /**
     * 
     */
    @After
    public void setUp() {
        // For testP019Subtable1Rel10013();
        // 如果存在该表就删除
        if (null == verifier.verifyTable("TST_SUB_ROLE")) {
            this.context.executeBatch("DROP TABLE TST_SUB_ROLE;");
        }
        if (null == verifier.verifyTable("TST_SUB_ROLE1")) {
            this.context.executeBatch("DROP TABLE TST_SUB_ROLE1;");
        }
        if (null == verifier.verifyTable("TST_SUB_ROLE2")){
            this.context.executeBatch("DROP TABLE TST_SUB_ROLE2;");
        }
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTableNotExistingException.class)
    public void testP23FKey1Target10027() throws AbstractSchemaException {
        testImport("rels/P0213field-FkCType10027-1.json",
                "[E10027] Target table does not exist, please verify the result!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTColumnNotExistingException.class)
    public void testP23FKey1Target10028() throws AbstractSchemaException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != verifier.verifyTable("TST_SUB_ROLE")) {
            ret = this.context.executeBatch("CREATE TABLE TST_SUB_ROLE( K_ID VARCHAR(236) );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("rels/P0213field-FkCType10028-1.json",
                    "[E10028] Target column does not exist, please verify the result!");
        }
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BFKConstraintInvalidException.class)
    public void testP23FKey1Target10029() throws AbstractSchemaException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != verifier.verifyTable("TST_SUB_ROLE1")) {
            ret = this.context.executeBatch("CREATE TABLE TST_SUB_ROLE1( R_ID VARCHAR(236) );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("rels/P0213field-FkCType10029-1.json",
                    "[E10029] Target column constraint is invalid please verify the result!");
        }
    }
    
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTColumnTypeInvalidException.class)
    public void testP23FKey1Target10030() throws AbstractSchemaException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != verifier.verifyTable("TST_SUB_ROLE2")) {
            ret = this.context.executeBatch("CREATE TABLE TST_SUB_ROLE2( R_ID BIGINT PRIMARY KEY );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("rels/P0213field-FkCType10030-1.json",
                    "[E10030] Target column data type is invalid please verify the result!");
        }
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP22Fields1String10001() throws AbstractSchemaException {
        testImport("types/P022field-Type1STRING-length10001.json",
                "[E10001] Fields ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP22Fields2Xml10001() throws AbstractSchemaException {
        testImport("types/P022field-Type2XML-length10001.json",
                "[E10001] Fields ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP22Fields3Json10001() throws AbstractSchemaException {
        testImport("types/P022field-Type3JSON-length10001.json",
                "[E10001] Fields ==> (Failure) There is unexpected exception!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
