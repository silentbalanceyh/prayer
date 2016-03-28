package com.prayer.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
public class PropLoader3TestCase extends AbstractCommonTool {
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
    public void testGetBoolean1(){
        final boolean ret = this.getLoader().getBoolean(null);
        failure(ret);
    }
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetBoolean2(){
        final boolean ret = this.getLoader().getBoolean("");
        failure(ret);
    }
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetBoolean3(){
        final boolean ret = this.getLoader().getBoolean("   ");
        failure(ret);
    }
    /**
     * 
     */
    @Test
    public void testGetBoolean4(){
        final boolean ret = this.getLoader().getBoolean("x.test");
        assertFalse(ret);
    }
    /**
     * 
     */
    @Test
    public void testGetBoolean5(){
        final boolean ret = this.getLoader().getBoolean("test.boolean1");
        assertTrue(ret);
    }
    
    /**
     * 
     */
    @Test
    public void testGetBoolean6(){
        final boolean ret = this.getLoader().getBoolean("test.boolean2");
        assertFalse(ret);
    }
    
    /**
     * 
     */
    @Test
    public void testGetBoolean7(){
        final boolean ret = this.getLoader().getBoolean("test.boolean3");
        assertFalse(ret);
    }
    // ~ hashCode,equals,toString ============================
}
