package com.prayer.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.IOKit;
import com.prayer.util.JsonKit;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Accessors;
import com.prayer.util.cv.Resources;

/**
 * 构造函数的生成
 *
 * @author Lang
 * @see
 */
public class ConTestCase extends AbstractConTestCase {     // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConTestCase.class);
    /** **/
    private static final String DEBUG_MSG = "[TD] Test constructor -> ";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /** **/
    @Test
    public void testCon3() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(DEBUG_MSG + Accessors.class.getName());
        }
        final Accessors ref = instance(Accessors.class.getName());
        assertNotNull("[E] testCon3", ref);
    }

    /** **/
    @Test
    public void testCon2() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[TD] Test constructor5 -> "
                    + Resources.class.getName());
        }
        final Resources ref = instance(Resources.class.getName());
        assertNotNull("[E] testCon2", ref);
    }

    /** **/
    @Test
    public void testCon4() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(DEBUG_MSG + JsonKit.class.getName());
        }
        final JsonKit ref = instance(JsonKit.class.getName());
        assertNotNull("[E] testCon4", ref);
    }

    /** **/
    @Test
    public void testCon5() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(DEBUG_MSG + IOKit.class.getName());
        }
        final IOKit ref = instance(IOKit.class.getName());
        assertNotNull("[E] testCon5", ref);
    }

    /** **/
    @Test
    public void testCon6() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(DEBUG_MSG + StringKit.class.getName());
        }
        final StringKit ref = instance(StringKit.class.getName());
        assertNotNull("[E] testCon6", ref);
    }
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
}
