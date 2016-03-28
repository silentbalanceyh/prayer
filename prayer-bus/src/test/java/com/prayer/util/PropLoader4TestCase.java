package com.prayer.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class PropLoader4TestCase extends AbstractCommonTool {
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
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetInt1() {
        final int ret = this.getLoader().getInt(null);
        failure(ret);
    }

    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetInt2() {
        final int ret = this.getLoader().getInt("");
        failure(ret);
    }

    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetInt3() {
        final int ret = this.getLoader().getInt("   ");
        failure(ret);
    }

    /**
     * 
     */
    @Test
    public void testGetInt4() {
        final int ret = this.getLoader().getInt("x.test");
        assertEquals(-1, ret);
    }

    /**
     * 
     */
    @Test
    public void testGetInt5() {
        final int ret = this.getLoader().getInt("test.intexp");
        assertEquals(-1, ret);
    }

    /**
     * 
     */
    @Test
    public void testGetInt6() {
        final int ret = this.getLoader().getInt("test.int");
        assertEquals(2015, ret);
    }
    // ~ Private Methods =====================================
}
