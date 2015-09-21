package com.test.db.conn.impl;

import static com.prayer.util.Instance.instance;
import static org.junit.Assert.assertNotNull;
import net.sf.oval.exception.ConstraintsViolatedException;

import org.junit.Test;

import com.prayer.constant.Resources;
import com.prayer.db.pool.AbstractDbPool;
import com.prayer.db.pool.BoneCPPool;
import com.test.AbstractTestCase;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class BoneCPPoolTestCase extends AbstractTestCase {
    // ~ Constructors ========================================
    /** **/
    public BoneCPPoolTestCase() {
        super(AbstractDbPool.class.getName());
        // instance("xxx",null); Compiler High Level Warning
    }

    // ~ Methods =============================================
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testCon1() {
        setMethod("Constructor with exception.");
        final AbstractDbPool pool = instance(BoneCPPool.class.getName(), "  ");
        failure(pool);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testCon2() {
        setMethod("Constructor with exception.");
        final AbstractDbPool pool = instance(BoneCPPool.class.getName(), "");
        failure(pool);
    }

    /** **/
    @Test
    public void testCon3() {
        setMethod("Constructor.");
        final AbstractDbPool pool = instance(BoneCPPool.class.getName());
        assertNotNull(getPosition(), pool);
    }

    /** **/
    @Test
    public void testCon4() {
        setMethod("Constructor.");
        final AbstractDbPool pool = instance(BoneCPPool.class.getName(),
                Resources.DB_CATEGORY);
        assertNotNull(getPosition(), pool);
    }

    /** **/
    @Test
    public void testGetJdbc() {
        setMethod("getJdbc()");
        final AbstractDbPool pool = instance(BoneCPPool.class.getName());
        if (null != pool) {
            assertNotNull(getPosition(), pool.getJdbc());
        }
    }
    // ~ hashCode,equals,toString ============================
}
