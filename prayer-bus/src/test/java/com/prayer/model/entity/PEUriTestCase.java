package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.vertx.PEUri;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class PEUriTestCase extends AbstractEntityTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEUriTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEUri getInstance(final String file) {
        PEUri instance = null;
        if (null == file) {
            instance = new PEUri();
        } else {
            final JsonObject data = this.readData(file);
            instance = new PEUri(data);
        }
        Log.debug(LOGGER, instance.toString());
        return instance;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/peuri/uri.json");
        final PEUri actual = this.getInstance("/entity/peuri/uri.json");
        System.out.println(expected);
        System.out.println(actual);
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /** **/
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/peuri/uri2.json");
        final PEUri actual = this.getInstance("/entity/peuri/uri2.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /** **/
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEUri actual = this.getInstance("/entity/peuri/uri3.json");
        actual.writeToBuffer(buffer);
        final PEUri expected = new PEUri(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /** **/
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEUri expected = this.getInstance("/entity/peuri/uri4.json");
        expected.writeToBuffer(buffer);
        final PEUri actual = new PEUri();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }

}
