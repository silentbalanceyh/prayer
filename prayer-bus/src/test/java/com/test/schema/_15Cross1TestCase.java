package com.test.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.ColumnsMissingException;
import com.prayer.exception.schema.FKNotOnlyOneException;
import com.prayer.exception.schema.KeysNameSpecificationException;
import com.prayer.exception.schema.MultiForFKPolicyException;
import com.prayer.exception.schema.MultiForPKPolicyException;
import com.prayer.exception.schema.PKNotOnlyOneException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _15Cross1TestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_15Cross1TestCase.class);

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
    public void testP291Keys1Multi10003() throws AbstractSchemaException {
        testImport("zkeys/P0291keys-10003-1.json", "[E10003] Keys ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PatternNotMatchException.class)
    public void testP292Keys1Multi10003() throws AbstractSchemaException {
        testImport("zkeys/P0292keys-10003-2.json", "[E10003] Keys ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = MultiForFKPolicyException.class)
    public void testP293Keys1Multi10021() throws AbstractSchemaException {
        testImport("zkeys/P0293keys-10021-1.json", "[E10003] Keys ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = KeysNameSpecificationException.class)
    public void testP30Keys1Spec10019() throws AbstractSchemaException {
        testImport("zkeys/P030keys-10019-1.json",
                "[E10019] Keys ==> (Failure) Name specification error for this case!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = KeysNameSpecificationException.class)
    public void testP30Keys2Spec10019() throws AbstractSchemaException {
        testImport("zkeys/P030keys-10019-2.json",
                "[E10019] Keys ==> (Failure) Name specification error for this case!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = KeysNameSpecificationException.class)
    public void testP30Keys3Spec10019() throws AbstractSchemaException {
        testImport("zkeys/P030keys-10019-3.json",
                "[E10019] Keys ==> (Failure) Name specification error for this case!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = MultiForPKPolicyException.class)
    public void testP31Cross1Spec10022() throws AbstractSchemaException {
        testImport("zkeys/P031cross-10022-1.json",
                "[E10022] Cross ==> (Failure) Primary Key policy conflict with 'multi'!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = MultiForPKPolicyException.class)
    public void testP31Cross2Spec10022() throws AbstractSchemaException {
        testImport("zkeys/P031cross-10022-2.json",
                "[E10022] Cross ==> (Failure) Primary Key policy conflict with 'multi'!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = PKNotOnlyOneException.class)
    public void testP32Cross1Spec10009() throws AbstractSchemaException {
        testImport("zkeys/P032cross-10009-1.json",
                "[E10009] Cross ==> (Failure) Primary Key must appears once in __keys__ definition!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = FKNotOnlyOneException.class)
    public void testP33Cross1Spec10016() throws AbstractSchemaException {
        testImport("zkeys/P033cross-10016-1.json",
                "[E10016] Cross ==> (Failure) Foreign Key must appears once in __keys__ definition!");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = ColumnsMissingException.class)
    public void testP34Cross1Spec10023() throws AbstractSchemaException {
        testImport("zkeys/P034cross-10023-1.json",
                "[E10023] Cross ==> (Failure) Column missing in '__fields__' definition.");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
