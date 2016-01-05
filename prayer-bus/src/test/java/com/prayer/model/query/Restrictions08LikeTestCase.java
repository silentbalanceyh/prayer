package com.prayer.model.query;

import static com.prayer.util.debug.Log.info;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractTestTool;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.model.type.StringType;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class Restrictions08LikeTestCase extends AbstractTestTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Restrictions08LikeTestCase.class);
    /** **/
    private static final Value<?> STR_VAL = new StringType("Value");
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
    public void testE05049Mlike() {
        Restrictions.like(null,STR_VAL,MatchMode.EXACT);
        failure(TST_OVAL);
    }
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05050Mlike() {
        Restrictions.like("",STR_VAL,MatchMode.EXACT);
        failure(TST_OVAL);
    }
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05051Mlike() {
        Restrictions.like("   ",STR_VAL,MatchMode.EXACT);
        failure(TST_OVAL);
    }
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05052Mlike() {
        Restrictions.like(COL_NAME,null,MatchMode.EXACT);
        failure(TST_OVAL);
    }
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05053Mlike() {
        Restrictions.like(COL_NAME,STR_VAL,null);
        failure(TST_OVAL);
    }

    /** **/
    @Test
    public void testT05021Mlike() {
        final Expression expr = Restrictions.like(COL_NAME,STR_VAL,MatchMode.EXACT);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + " LIKE 'Value'", sqlSeg);
    }
    /** **/
    @Test
    public void testT05022Mlike() {
        final Expression expr = Restrictions.like(COL_NAME,STR_VAL,MatchMode.START);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + " LIKE 'Value%'", sqlSeg);
    }
    /** **/
    @Test
    public void testT05023Mlike() {
        final Expression expr = Restrictions.like(COL_NAME,STR_VAL,MatchMode.END);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + " LIKE '%Value'", sqlSeg);
    }
    /** **/
    @Test
    public void testT05024Mlike() {
        final Expression expr = Restrictions.like(COL_NAME,STR_VAL,MatchMode.ANYWHERE);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + " LIKE '%Value%'", sqlSeg);
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
