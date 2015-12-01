package com.prayer.model.query;

import static com.prayer.util.Instance.instance;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractTestTool;
import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.model.query.Restrictions;
import com.prayer.model.type.IntType;
import com.prayer.model.type.StringType;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class Restrictions10PrOrTestCase extends AbstractTestTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Restrictions10PrOrTestCase.class);
    /** **/
    private static final Value<?> STR_VAL = new StringType("Value");
    /** **/
    private static final Value<?> INT_VAL = new IntType(22);
    /** **/
    private static final String COL_NAME = "COL";
    /** **/
    private static final String COL_NAME1 = "COL1";

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
    public void testE05054Mor() {
        try {
            Restrictions.or(null, Restrictions.eq(COL_NAME, STR_VAL));
        } catch (AbstractDatabaseException ex) {
            info(LOGGER, ex.getErrorMessage());
        }
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05055Mor() {
        try {
            Restrictions.or(Restrictions.eq(COL_NAME, STR_VAL), null);
        } catch (AbstractDatabaseException ex) {
            info(LOGGER, ex.getErrorMessage());
        }
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = AbstractDatabaseException.class)
    public void testE05056Mor() throws AbstractDatabaseException {
        final Expression columnExpr = instance("com.prayer.model.query.ColumnLeafNode", COL_NAME);
        Restrictions.or(columnExpr, Restrictions.eq(COL_NAME, STR_VAL));
        failure(TST_PR);
    }

    /** **/
    @Test(expected = AbstractDatabaseException.class)
    public void testE05057Mor() throws AbstractDatabaseException {
        final Expression columnExpr = instance("com.prayer.model.query.ColumnLeafNode", COL_NAME);
        Restrictions.or(Restrictions.eq(COL_NAME, STR_VAL), columnExpr);
        failure(TST_PR);
    }

    /** **/
    @Test(expected = AbstractDatabaseException.class)
    public void testE05058Mor() throws AbstractDatabaseException {
        final Expression valueExpr = instance("com.prayer.model.query.ValueLeafNode");
        Restrictions.or(Restrictions.eq(COL_NAME, STR_VAL), valueExpr);
        failure(TST_PR);
    }

    /** **/
    @Test(expected = AbstractDatabaseException.class)
    public void testE05059Mor() throws AbstractDatabaseException {
        final Expression valueExpr = instance("com.prayer.model.query.ValueLeafNode");
        Restrictions.or(valueExpr, Restrictions.eq(COL_NAME, STR_VAL));
        failure(TST_PR);
    }

    /** **/
    @Test
    public void testT05025Mor() throws AbstractDatabaseException {
        final Expression expr = Restrictions.or(Restrictions.eq(COL_NAME, STR_VAL),
                Restrictions.lt(COL_NAME1, STR_VAL));
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + "='Value' OR " + COL_NAME1 + "<'Value'", sqlSeg);
    }
    /** **/
    @Test
    public void testT05026Mor() throws AbstractDatabaseException {
        final Expression expr = Restrictions.or(Restrictions.eq(COL_NAME, INT_VAL),
                Restrictions.lt(COL_NAME1, INT_VAL));
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + "=22 OR " + COL_NAME1 + "<22", sqlSeg);
    }
    /** **/
    @Test
    public void testT05027Mor() throws AbstractDatabaseException {
        Expression expr = Restrictions.or(Restrictions.eq(COL_NAME, INT_VAL),
                Restrictions.lt(COL_NAME1, INT_VAL));
        expr = Restrictions.or(expr, Restrictions.eq(COL_NAME, STR_VAL));
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), "(COL=22 OR COL1<22) OR COL='Value'", sqlSeg);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
