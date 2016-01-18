package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.meta.vertx.PEVerticle;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
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
    /** **/
    @Override
    public PEVerticle getInstance(final String file){
        PEVerticle instance = null;
        if(null == file){
            instance = new PEVerticle();
        }else{
            final JsonObject data = this.readData(file);
            instance = new PEVerticle(data);
        }
        Log.debug(LOGGER,instance.toString());
        return instance;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/peverticle/verticle.json");
        final PEVerticle actual = this.getInstance("/entity/peverticle/verticle.json");
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /** **/
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/peverticle/verticle2.json");
        final PEVerticle actual = this.getInstance("/entity/peverticle/verticle2.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /** **/
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEVerticle actual = this.getInstance("/entity/peverticle/verticle3.json");
        actual.writeToBuffer(buffer);
        final PEVerticle expected = new PEVerticle(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /** **/
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEVerticle expected = this.getInstance("/entity/peverticle/verticle4.json");
        expected.writeToBuffer(buffer);
        final PEVerticle actual = new PEVerticle();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
