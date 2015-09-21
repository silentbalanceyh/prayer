package com.prayer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractTestTool;
import com.prayer.Assistant;
import com.prayer.Assistant.TestEnum;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class ConverterToStrTestCase extends AbstractTestTool {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterToStrTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    protected Logger getLogger() {
        return LOGGER;
    }

    /** **/
    protected Class<?> getTarget() {
        return Converter.class;
    }
    // ~ Methods =============================================
    /**
     * Converter.toStr(null); 上边方式不可调用，错误信息：The method toStr(Set<String>) is
     * ambiguous for the type Converter 因为包含了toStr方法的重载
     */

    /** **/
    @Test
    public void testC00002Constructor() {
        assertNotNull(message(TST_CONS, getTarget().getName()), Assistant.instance(getTarget()));
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE00005MtoStr() {
        Converter.toStr(Assistant.set(0));
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE00006MtoStr() {
        Converter.toStr(Assistant.array(0));
        failure(TST_OVAL);
    }
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE00007MfromStr(){
        Converter.fromStr(null,"TEST1");
        failure(TST_OVAL);
    }
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE00008MfromStr(){
        Converter.fromStr(TestEnum.class,null);
        failure(TST_OVAL);
    }
    
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE00009MfromStr(){
        Converter.fromStr(TestEnum.class,"");
        failure(TST_OVAL);
    }
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE000010MfromStr(){
        Converter.fromStr(TestEnum.class,"   ");
        failure(TST_OVAL);
    }

    /** **/
    @Test
    public void testT00013MtoStr() {
        final Set<String> dataSet = Assistant.set(3);
        final String actual = Converter.toStr(dataSet);
        assertEquals(message(TST_EQUAL), Assistant.join(dataSet, ',', false), actual);
    }

    /** **/
    @Test
    public void testT00014MtoStr() {
        final String[] dataArr = Assistant.array(3);
        final String actual = Converter.toStr(dataArr);
        assertEquals(message(TST_EQUAL), "Set0,Set1,Set2", actual);
    }
    /** **/
    @Test
    public void testT00015MfromStr(){
        final TestEnum tEnum = Converter.fromStr(TestEnum.class,"TST");
        assertNull(message(TST_NULL),tEnum);
    }
    /** **/
    @Test
    public void testT00016MfromStr(){
        final TestEnum tEnum = Converter.fromStr(TestEnum.class,"TEST1");
        assertEquals(message(TST_EQUAL),TestEnum.TEST1,tEnum);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
