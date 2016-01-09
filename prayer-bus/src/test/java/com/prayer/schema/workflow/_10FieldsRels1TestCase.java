package com.prayer.schema.workflow;

import static com.prayer.constant.Accessors.validator;
import static com.prayer.util.reflection.Instance.singleton;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.exception.schema.SubtableWrongException;
import com.prayer.facade.schema.DataValidator;

/**
 * 
 * @author Lang
 *
 */
public class _10FieldsRels1TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_10FieldsRels1TestCase.class);
    /** **/
    private static final String FK_PATTERN = "[E10003] Fields -> ( foreignkey = true ) ==> (Failure) One of optional attributes 'refId,refTable' does not match the pattern! ";
    /** **/
    private transient final DataValidator verifier;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    public _10FieldsRels1TestCase() {
        super();
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
        if (null == verifier.verifyTable("TST_SUBROLE")) {
            this.context.executeBatch("DROP TABLE TST_SUBROLE;");
        }
        if (null == verifier.verifyTable("TST_SUBROLE1")) {
            this.context.executeBatch("DROP TABLE TST_SUBROLE1;");
        }
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = SubtableWrongException.class)
    public void testP019Subtable1Rel10013() throws AbstractSchemaException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != verifier.verifyTable("TST_SUBROLE")) {
            ret = this.context.executeBatch("CREATE TABLE TST_SUBROLE( R_ID VARCHAR(256) PRIMARY KEY );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("rels/P019field-Subtable10013-1.json",
                    "[E10013] Fields -> subtalbe ==> (Failure) Attribute 'subtalbe' must exist because mapping = COMBINATED! ");
        }
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = SubtableWrongException.class)
    public void testP019Subtable2Rel10013() throws AbstractSchemaException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != verifier.verifyTable("TST_SUBROLE1")) {
            ret = this.context.executeBatch("CREATE TABLE TST_SUBROLE1( R_ID VARCHAR(256) UNIQUE );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("rels/P019field-Subtable10013-2.json",
                    "[E10013] Fields -> subtalbe ==> (Failure) Attribute 'subtalbe' must exist because mapping = COMBINATED! ");
        }
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP0201FKey1Rel10004() throws AbstractSchemaException {
        testImport("rels/P0201field-FK10004-1.json",
                "[E10004] Fields -> ( foreignkey = true ) ==> (Failure) One of optional attributes 'refId,refTable' missed! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = OptionalAttrMorEException.class)
    public void testP0201FKey2Rel10004() throws AbstractSchemaException {
        testImport("rels/P0201field-FK10004-2.json",
                "[E10004] Fields -> ( foreignkey = true ) ==> (Failure) One of optional attributes 'refId,refTable' missed! ");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0202FKey1Rel10003() throws AbstractSchemaException {
        testImport("rels/P0202field-FK10003-1.json", FK_PATTERN);
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP0202FKey2Rel10003() throws AbstractSchemaException {
        testImport("rels/P0202field-FK10003-2.json", FK_PATTERN);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
