package com.prayer.resource;

import static com.prayer.util.debug.Log.info;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.resource.DatumLoader;

/**
 * 
 * @author Lang
 *
 */
public class LoaderTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory
            .getLogger(LoaderTestCase.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test
    public void testGetLoader() {
        final Properties prop = DatumLoader.getLoader();
        info(LOGGER,"[T] Properties object should not be null. prop = " + prop);
        assertNotNull(prop);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
