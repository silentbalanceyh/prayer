package com.prayer.kernel.query;

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
public class Restrictions02NEQTestCase extends AbstractTestTool {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Restrictions02NEQTestCase.class);
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
    public void testE05008Mneq() {
        Restrictions.neq(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05009Mneq() {
        Restrictions.neq("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05010Mneq() {
        Restrictions.neq("   ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05011Mneq() {
        Restrictions.neq(null, STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05012Mneq() {
        Restrictions.neq("", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05013Mneq() {
        Restrictions.neq("   ", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05014Mneq() {
        Restrictions.neq(COL_NAME, null);
        failure(TST_OVAL);
    }
    /** **/
    @Test
    public void testT05004Mneq(){
        final Expression expr = Restrictions.neq(COL_NAME);
        final String sqlSeg = expr.toSql();
        info(LOGGER,TST_INFO_SQL,sqlSeg);
        assertEquals(message(TST_EQUAL),COL_NAME + "<>?",sqlSeg);
    }
    /** **/
    @Test
    public void testT05005Mneq(){
        final Expression expr = Restrictions.neq(COL_NAME,STR_VAL);
        final String sqlSeg = expr.toSql();
        info(LOGGER,TST_INFO_SQL,sqlSeg);
        assertEquals(message(TST_EQUAL),COL_NAME + "<>'Value'",sqlSeg);
    }
    /** **/
    @Test
    public void testT05006Mneq(){
        final Expression expr = Restrictions.neq(COL_NAME,INT_VAL);
        final String sqlSeg = expr.toSql();
        info(LOGGER,TST_INFO_SQL,sqlSeg);
        assertEquals(message(TST_EQUAL),COL_NAME + "<>22",sqlSeg);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
