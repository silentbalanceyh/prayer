package com.prayer.util.instance;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.model.cache.Cache;
import com.prayer.model.cache.CacheBuilder;

/**
 * 
 * @author Lang
 *
 */
public class InstanceTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * Instance
     */
    @Test
    public void testInstance1() {
        final String str1 = instance(String.class);
        final String str2 = instance(String.class);
        final boolean ret = str1 == str2;
        info(LOGGER, "[T] Instance instance() tested and compared result = " + ret);
        assertFalse(ret);
    }

    /**
     * Instance
     */
    @Test
    public void testInstance2() {
        CacheBuilder.clean();
        final String str1 = singleton(String.class);
        final String str2 = singleton(String.class);
        final boolean ret = str1 == str2;
        info(LOGGER, "[T] Instance singleton() tested and compared result = " + ret);
        assertTrue(ret);
    }

    /**
     * Instance
     */
    @Test
    public void testInstance3() {
        CacheBuilder.clean();
        final String str1 = reservoir("K1", String.class);
        final String str2 = reservoir("K1", String.class);
        final boolean ret = str1 == str2;
        info(LOGGER, "[T] Instance reservoir() tested and compared result = " + ret);
        assertTrue(ret);
    }

    /**
     * Instance
     */
    @Test
    public void testInstance4() {
        CacheBuilder.clean();
        final String str1 = reservoir("K1", String.class);
        final String str2 = reservoir("K2", String.class);
        final boolean ret = str1 == str2;
        info(LOGGER, "[T] Instance reservoir() tested and compared result = " + ret);
        assertFalse(ret);
    }

    /**
     * Instance
     */
    @Test
    public void testCache1() {
        CacheBuilder.clean();
        final Cache cache1 = CacheBuilder.build(String.class);
        final Cache cache2 = CacheBuilder.build(String.class);
        final boolean ret = cache1 == cache2;
        info(LOGGER, "[T] Cache (String.class) keep singleton and compared result = " + ret + ".  GS = "
                + CacheBuilder.size() + ", LS = " + cache1.size());
        assertTrue(ret);
    }

    /**
     * Instance
     */
    @Test
    public void testCache2() {
        CacheBuilder.clean();
        reservoir("K1", String.class);
        reservoir("K1", String.class);
        final Cache cache = CacheBuilder.build(String.class);
        info(LOGGER,
                "[T] Cache (String.class) keep singleton.  GS = " + CacheBuilder.size() + ", LS = " + cache.size());
        assertEquals(1, cache.size());
    }

    /**
     * Instance
     */
    @Test
    public void testCache3() {
        CacheBuilder.clean();
        reservoir("K1", String.class);
        reservoir("K2", String.class);
        final Cache cache = CacheBuilder.build(String.class);
        info(LOGGER, "[T] Cache (String.class) added.  GS = " + CacheBuilder.size() + ", LS = " + cache.size());
        assertEquals(2, cache.size());
    }
    /**
     * 
     */
    @Test
    public void testCache4() {
        CacheBuilder.clean();
        CacheBuilder.build(String.class);
        CacheBuilder.build(Integer.class);
        info(LOGGER, "[T] Cache (String.class) added.  GS = " + CacheBuilder.size());
        /** 新架构中有2个特殊的 **/
        assertEquals(4, CacheBuilder.size());
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
