package com.prayer.model.query;

import static com.prayer.util.debug.Log.info;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractCommonTool;
import com.prayer.facade.kernel.Expression;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class Restrictions07NullableTestCase extends AbstractCommonTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Restrictions07NullableTestCase.class);
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
    public void testE05043MisNull() {
        Restrictions.isNull(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05044MisNull() {
        Restrictions.isNull("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05045MisNull() {
        Restrictions.isNull("   ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05046isNotNull() {
        Restrictions.isNotNull(null);
        failure(TST_OVAL);
    }
    
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05047isNotNull() {
        Restrictions.isNotNull("");
        failure(TST_OVAL);
    }
    
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05048isNotNull() {
        Restrictions.isNotNull("   ");
        failure(TST_OVAL);
    }

    /** **/
    @Test
    public void testT05019MisNull() {
        final Expression expr = Restrictions.isNull(COL_NAME);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + " IS NULL", sqlSeg);
    }

    /** **/
    @Test
    public void testT05020MisNotNull() {
        final Expression expr = Restrictions.isNotNull(COL_NAME);
        final String sqlSeg = expr.toSql();
        info(LOGGER, TST_INFO_SQL, sqlSeg);
        assertEquals(message(TST_EQUAL), COL_NAME + " IS NOT NULL", sqlSeg);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
