package com.test.util;

import static com.prayer.util.Instance.reservoir;
import static org.junit.Assert.assertEquals;
import net.sf.oval.exception.ConstraintsViolatedException;

import org.junit.Test;

import com.prayer.util.PropertyKit;
import com.test.AbstractTestCase;
/**
 * 
 *
 * @author Lang
 * @see
 */
public class PropLoader5TestCase extends AbstractTestCase implements
        PropConstant {
    // ~ Constructors ========================================
    /**
     * 
     */
    public PropLoader5TestCase() {
        super(PropertyKit.class.getName());
        loader = reservoir(OBJ_POOLS, getClass().getName(), PropertyKit.class,
                TEST_FILE);
    }

    // ~ Methods =============================================
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetLong1() {
        setMethod(M_GET_LONG);
        final long ret = this.getLoader().getLong(null);
        failure(ret);
    }
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetLong2() {
        setMethod(M_GET_LONG);
        final long ret = this.getLoader().getLong("");
        failure(ret);
    }
    /**
     * 
     */
    @Test(expected = ConstraintsViolatedException.class)
    public void testGetLong3() {
        setMethod(M_GET_LONG);
        final long ret = this.getLoader().getLong("   ");
        failure(ret);
    }
    /**
     * 
     */
    @Test
    public void testGetLong4() {
        setMethod(M_GET_LONG);
        final long ret = this.getLoader().getLong("x.test");
        assertEquals(getPosition(),-1,ret);
    }
    /**
     * 
     */
    @Test
    public void testGetLong5() {
        setMethod(M_GET_LONG);
        final long ret = this.getLoader().getLong("test.longexp");
        assertEquals(getPosition(),-1,ret);
    }
    
    /**
     * 
     */
    @Test
    public void testGetLong6() {
        setMethod(M_GET_LONG);
        final long ret = this.getLoader().getLong("test.long");
        assertEquals(getPosition(),20150109L,ret);
    }
    // ~ Private Methods =====================================
}
