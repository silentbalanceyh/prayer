package com.prayer.db.conn.impl;

import static com.prayer.util.reflection.Instance.instance;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.constant.Resources;
import com.prayer.database.pool.impl.jdbc.BoneCPPool;
import com.prayer.fantasm.pool.AbstractJdbcPool;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class BoneCPPoolTestCase extends AbstractCommonTool {
    // ~ Constructors ========================================
    /** 获取当前类的日志器 **/
    protected Logger getLogger() {
        return null;
    }

    /** 获取被测试类类名 **/
    protected Class<?> getTarget() {
        return null;
    }

    // ~ Methods =============================================
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testCon1() {
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName(), "  ");
        failure(pool);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testCon2() {
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName(), "");
        failure(pool);
    }

    /** **/
    @Test
    public void testCon3() {
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName());
        assertNotNull(pool);
    }

    /** **/
    @Test
    public void testCon4() {
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName(), Resources.DB_CATEGORY);
        assertNotNull(pool);
    }

    /** **/
    @Test
    public void testGetJdbc() {
        final AbstractJdbcPool pool = instance(BoneCPPool.class.getName());
        if (null != pool) {
            assertNotNull(pool.getExecutor());
        }
    }
    // ~ hashCode,equals,toString ============================
}
