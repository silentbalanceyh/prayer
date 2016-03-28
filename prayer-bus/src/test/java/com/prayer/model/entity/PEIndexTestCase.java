package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.meta.database.PEIndex;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
/**
 * 
 * @author Lang
 *
 */
public class PEIndexTestCase extends AbstractEntityTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEIndexTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEIndex getInstance(final String file) {
        PEIndex instance = null;
        if (null == file) {
            instance = new PEIndex();
        } else {
            final JsonObject data = this.readData(file);
            instance = new PEIndex(data);
        }
        Log.debug(LOGGER, instance.toString());
        return instance;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/peindex/index.json");
        final PEIndex actual = this.getInstance("/entity/peindex/index.json");
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /** **/
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/peindex/index2.json");
        final PEIndex actual = this.getInstance("/entity/peindex/index2.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /** **/
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEIndex actual = this.getInstance("/entity/peindex/index3.json");
        actual.writeToBuffer(buffer);
        final PEIndex expected = new PEIndex(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /** **/
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEIndex expected = this.getInstance("/entity/peindex/index4.json");
        expected.writeToBuffer(buffer);
        final PEIndex actual = new PEIndex();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }

}
