package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.meta.database.PEMeta;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
/**
 * 
 * @author Lang
 *
 */
public class PEMetaTestCase extends AbstractEntityTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEMetaTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEMeta getInstance(final String file) {
        PEMeta instance = null;
        if (null == file) {
            instance = new PEMeta();
        } else {
            final JsonObject data = this.readData(file);
            instance = new PEMeta(data);
        }
        Log.debug(LOGGER, instance.toString());
        return instance;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/pemeta/meta.json");
        final PEMeta actual = this.getInstance("/entity/pemeta/meta.json");
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /** **/
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/pemeta/meta2.json");
        final PEMeta actual = this.getInstance("/entity/pemeta/meta2.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /** **/
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEMeta actual = this.getInstance("/entity/pemeta/meta3.json");
        actual.writeToBuffer(buffer);
        final PEMeta expected = new PEMeta(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /** **/
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEMeta expected = this.getInstance("/entity/pemeta/meta4.json");
        expected.writeToBuffer(buffer);
        final PEMeta actual = new PEMeta();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }

}
