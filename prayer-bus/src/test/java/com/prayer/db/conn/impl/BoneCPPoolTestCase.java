package com.prayer.db.conn.impl;

import static com.prayer.util.reflection.Instance.instance;
import static org.junit.Assert.assertNotNull;
import net.sf.oval.exception.ConstraintsViolatedException;

import org.junit.Test;

import com.prayer.AbstractUtilTool;
import com.prayer.constant.Resources;
import com.prayer.database.pool.impl.jdbc.BoneCPPool;
import com.prayer.fantasm.pool.AbstractJdbcPool;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class BoneCPPoolTestCase extends AbstractUtilTool {
    // ~ Constructors ========================================
    /** **/
    public BoneCPPoolTestCase() {
        super(AbstractJdbcPool.class.getName());
        // instance("xxx",null); Compiler High Level Warning
    }

    // ~ Methods =============================================
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testCon1() {
        setMethod("Constructor with exception.");
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName(), "  ");
        failure(pool);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testCon2() {
        setMethod("Constructor with exception.");
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName(), "");
        failure(pool);
    }

    /** **/
    @Test
    public void testCon3() {
        setMethod("Constructor.");
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName());
        assertNotNull(getPosition(), pool);
    }

    /** **/
    @Test
    public void testCon4() {
        setMethod("Constructor.");
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName(),
                Resources.DB_CATEGORY);
        assertNotNull(getPosition(), pool);
    }

    /** **/
    @Test
    public void testGetJdbc() {
        setMethod("getJdbc()");
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName());
        if (null != pool) {
            assertNotNull(getPosition(), pool.getExecutor());
        }
    }
    // ~ hashCode,equals,toString ============================
}
