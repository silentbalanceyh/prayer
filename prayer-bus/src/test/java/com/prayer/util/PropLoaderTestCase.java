package com.prayer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.util.io.PropertyKit;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 测试com.lyra.util.prop.PropLoader
 *
 * @author Lang
 * @see
 */
public class PropLoaderTestCase extends AbstractCommonTool {
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
     * 构造函数测试
     */
    @Test
    public void testPropLoader1() {
        final PropertyKit instance = new PropertyKit(getClass(), "/proploader.properties");// singleton(PropertyKit.class,getClass(),TEST_FILE);

        assertNotNull(instance);
    }

    /**
     * 构造函数的PostValidateThis测试
     * 
     * @return
     */
    @Test
    public void testPropLoader2() {
        final PropertyKit instance = new PropertyKit(getClass(), "x.properties");// singleton(PropertyKit.class,getClass(),"x.properties");
        assertNotNull(instance.getProp());
    }

    /**
     * 
     */
    @Test
    public void testGetProp1() {
        final Properties prop = this.getLoader().getProp();
        assertNotNull(prop);
    }

    /**
     * 
     */
    @Test
    public void testGetProp2() {
        final Properties prop = this.getLoader().getProp("/proploader.properties");
        final Properties prop1 = this.getLoader().getProp();
        assertEquals(prop, prop1);
    }

    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetProp3() {
        final Properties prop = this.getLoader().getProp(null);
        failure(prop);
    }

    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetProp4() {
        final Properties prop = this.getLoader().getProp("");
        failure(prop);
    }

    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetProp5() {
        final Properties prop = this.getLoader().getProp("   ");
        failure(prop);
    }
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
}
