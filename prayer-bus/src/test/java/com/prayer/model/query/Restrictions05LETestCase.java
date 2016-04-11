package com.prayer.model.query;

import static com.prayer.util.debug.Log.info;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractCommonTool;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.crucial.Value;
import com.prayer.model.type.IntType;
import com.prayer.model.type.StringType;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class Restrictions05LETestCase extends AbstractCommonTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Restrictions05LETestCase.class);
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
    public void testE05029Mle() {
        Restrictions.le(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05030Mle() {
        Restrictions.le("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05031Mle() {
        Restrictions.le("   ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05032Mle() {
        Restrictions.le(null, STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05033Mle() {
        Restrictions.le("", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05034Mle() {
        Restrictions.le("   ", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05035Mle() {
        Restrictions.le(COL_NAME, null);
        failure(TST_OVAL);
    }

    /** **/
    @Test
    public void testT05013Mle() {
        final Expression expr = Restrictions.le(COL_NAME);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + "<=?", sqlSeg);
    }

    /** **/
    @Test
    public void testT05014Mle() {
        final Expression expr = Restrictions.le(COL_NAME, STR_VAL);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + "<='Value'", sqlSeg);
    }

    /** **/
    @Test
    public void testT05015Mle() {
        final Expression expr = Restrictions.le(COL_NAME, INT_VAL);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + "<=22", sqlSeg);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
