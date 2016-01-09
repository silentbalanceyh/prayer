package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.vertx.PEVerticle;
import com.prayer.util.debug.Log;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class PEVerticleTestCase extends AbstractEntityTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEVerticleTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 检查
     */
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/peverticle/verticle.json");
        final PEVerticle actual = this.getInstance("/entity/peverticle/verticle.json");
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }
    // ~ Private Methods =====================================

    private PEVerticle getInstance(final String file) {
        final JsonObject data = this.readData(file);
        final PEVerticle address = new PEVerticle(data);
        Log.debug(LOGGER, address.toString());
        return address;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
