package com.prayer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class PropLoader2TestCase extends AbstractCommonTool {
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
    public void testGetString1() {
        final String ret = this.getLoader().getString(null);
        failure(ret);
    }

    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetString2() {
        final String ret = this.getLoader().getString("");
        failure(ret);
    }

    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetString3() {
        final String ret = this.getLoader().getString("   ");
        failure(ret);
    }

    /**
     * 
     */
    @Test
    public void testGetString4() {
        final String ret = this.getLoader().getString("x.test");
        assertNull(ret);
    }

    /**
     * 
     */
    @Test
    public void testGetString5() {
        final String ret = this.getLoader().getString("test.null");
        assertNull(ret);
    }

    /**
     * 
     */
    @Test
    public void testGetString6() {
        final String ret = this.getLoader().getString("test.str");
        assertEquals("Hello Lang Yu", ret);
    }

    // ~ Private Methods =====================================
}
