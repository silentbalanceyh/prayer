package com.prayer.model.query;

import static com.prayer.util.debug.Log.info;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractTestTool;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.model.type.IntType;
import com.prayer.model.type.StringType;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class Restrictions04GTTestCase extends AbstractTestTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Restrictions04GTTestCase.class);
    /** **/
    private static final Value<?> STR_VAL = new StringType("Value");
    /** **/
    private static final Value<?> INT_VAL = new IntType(22);
    /** **/
    private static final String COL_NAME = "COL";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    protected Logger getLogger() {
        return LOGGER;
    }

    /** **/
    protected Class<?> getTarget() {
        return Restrictions.class;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05022Mgt() {
        Restrictions.gt(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05023Mgt() {
        Restrictions.gt("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05024Mgt() {
        Restrictions.gt("   ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05025Mgt() {
        Restrictions.gt(null, STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05026Mgt() {
        Restrictions.gt("", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05027Mgt() {
        Restrictions.gt("   ", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05028Mgt() {
        Restrictions.gt(COL_NAME, null);
        failure(TST_OVAL);
    }

    /** **/
    @Test
    public void testT05010Mgt() {
        final Expression expr = Restrictions.gt(COL_NAME);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + ">?", sqlSeg);
    }

    /** **/
    @Test
    public void testT05011Mgt() {
        final Expression expr = Restrictions.gt(COL_NAME, STR_VAL);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + ">'Value'", sqlSeg);
    }

    /** **/
    @Test
    public void testT05012Mgt() {
        final Expression expr = Restrictions.gt(COL_NAME, INT_VAL);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + ">22", sqlSeg);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
