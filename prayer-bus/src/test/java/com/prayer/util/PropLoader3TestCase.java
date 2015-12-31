package com.prayer.util;

import static com.prayer.util.Instance.reservoir;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.prayer.AbstractTestCase;
import com.prayer.util.io.PropertyKit;

import net.sf.oval.exception.ConstraintsViolatedException;
/**
 * 
 *
 * @author Lang
 * @see
 */
public class PropLoader3TestCase extends AbstractTestCase implements
        PropConstant {
    // ~ Constructors ========================================
    /**
     * 
     */
    public PropLoader3TestCase() {
        super(PropertyKit.class.getName());
        loader = reservoir(OBJ_POOLS, getClass().getName(), PropertyKit.class,
                TEST_FILE);
    }
    // ~ Methods =============================================
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetBoolean1(){
        setMethod(M_GET_BOOLEAN);
        final boolean ret = this.getLoader().getBoolean(null);
        failure(ret);
    }
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetBoolean2(){
        setMethod(M_GET_BOOLEAN);
        final boolean ret = this.getLoader().getBoolean("");
        failure(ret);
    }
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetBoolean3(){
        setMethod(M_GET_BOOLEAN);
        final boolean ret = this.getLoader().getBoolean("   ");
        failure(ret);
    }
    /**
     * 
     */
    @Test
    public void testGetBoolean4(){
        setMethod(M_GET_BOOLEAN);
        final boolean ret = this.getLoader().getBoolean("x.test");
        assertFalse(getPosition(),ret);
    }
    /**
     * 
     */
    @Test
    public void testGetBoolean5(){
        setMethod(M_GET_BOOLEAN);
        final boolean ret = this.getLoader().getBoolean("test.boolean1");
        assertTrue(getPosition(),ret);
    }
    
    /**
     * 
     */
    @Test
    public void testGetBoolean6(){
        setMethod(M_GET_BOOLEAN);
        final boolean ret = this.getLoader().getBoolean("test.boolean2");
        assertFalse(getPosition(),ret);
    }
    
    /**
     * 
     */
    @Test
    public void testGetBoolean7(){
        setMethod(M_GET_BOOLEAN);
        final boolean ret = this.getLoader().getBoolean("test.boolean3");
        assertFalse(getPosition(),ret);
    }
    // ~ hashCode,equals,toString ============================
}
