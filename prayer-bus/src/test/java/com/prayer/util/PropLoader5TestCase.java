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
public class PropLoader5TestCase extends AbstractCommonTool {
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
    public void testGetLong1() {
        final long ret = this.getLoader().getLong(null);
        failure(ret);
    }
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetLong2() {
        final long ret = this.getLoader().getLong("");
        failure(ret);
    }
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetLong3() {
        final long ret = this.getLoader().getLong("   ");
        failure(ret);
    }
    /**
     * 
     */
    @Test
    public void testGetLong4() {
        final long ret = this.getLoader().getLong("x.test");
        assertEquals(-1,ret);
    }
    /**
     * 
     */
    @Test
    public void testGetLong5() {
        final long ret = this.getLoader().getLong("test.longexp");
        assertEquals(-1,ret);
    }
    
    /**
     * 
     */
    @Test
    public void testGetLong6() {
        final long ret = this.getLoader().getLong("test.long");
        assertEquals(20150109L,ret);
    }
    // ~ Private Methods =====================================
}
