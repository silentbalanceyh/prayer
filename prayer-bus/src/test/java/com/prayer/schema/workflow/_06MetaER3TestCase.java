package com.prayer.schema.workflow;

import static com.prayer.constant.Accessors.validator;
import static com.prayer.util.reflection.Instance.singleton;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.BKeyConstraintInvalidException;
import com.prayer.exception.schema.BTColumnNotExistingException;
import com.prayer.exception.schema.BTableNotExistingException;
import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.facade.schema.DataValidator;
/**
 * 
 * @author Lang
 *
 */
public class _06MetaER3TestCase extends AbstractSchemaTestCase{
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_06MetaER3TestCase.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient final DataValidator verifier;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public _06MetaER3TestCase() {
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
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTableNotExistingException.class)
    public void testP00744Meta10027ECombinatedTargetTable() throws AbstractSchemaException {
        testImport("P00744meta-mappingE-COMBINATED10027-1.json",
                "[E10027] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute Error: Table Does Not Exist!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTColumnNotExistingException.class)
    public void testP00745Meta10028ECombinatedTargetColumn() throws AbstractSchemaException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != verifier.verifyTable("TST_SUB_ROLE")) {
            ret = this.context.executeBatch("CREATE TABLE TST_SUB_ROLE( R_ID VARCHAR(236) );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("P00745meta-mappingE-COMBINATED10028-1.json",
                    "[E10028] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute Error: Column Does Not Exist!");
        }
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BKeyConstraintInvalidException.class)
    public void testP00746Meta10029ECombinatedConstraints() throws AbstractSchemaException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != verifier.verifyTable("TST_SUB_ROLE1")) {
            ret = this.context.executeBatch("CREATE TABLE TST_SUB_ROLE1( K_ID VARCHAR(236) );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("P00746meta-mappingE-COMBINATED10029-1.json",
                    "[E10029] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute Error: Constraint Invalid!");
        }
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP010Meta10004PolicyIncrement1() throws AbstractSchemaException {
        testImport("P010meta-policyINCREMENT10004-1.json",
                "[E10004] Meta -> policy (INCREMENT) ==> Optional {seqinit,seqstep} Attribute Error!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP010Meta10004PolicyIncrement2() throws AbstractSchemaException {
        testImport("P010meta-policyINCREMENT10004-2.json",
                "[E10004] Meta -> policy (INCREMENT) ==> Optional {seqinit,seqstep} Attribute Error!");
    }

    /**
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP011Meta10005PolicyIncrement1() throws AbstractSchemaException {
        testImport("P011meta-policyINCREMENT10003-1.json",
                "[E10003] Meta -> policy (INCREMENT) ==> Optional {seqinit, seqstep} Attribute must be matching!");
    }

    /**
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP011Meta10005PolicyIncrement2() throws AbstractSchemaException {
        testImport("P011meta-policyINCREMENT10003-2.json",
                "[E10003] Meta -> policy (INCREMENT) ==> Optional {seqinit, seqstep} Attribute must be matching!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
