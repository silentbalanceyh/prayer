package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.database.PEKey;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
/**
 * 
 * @author Lang
 *
 */
public class PEKeyTestCase extends AbstractEntityTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEKeyTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEKey getInstance(final String file) {
        PEKey instance = null;
        if (null == file) {
            instance = new PEKey();
        } else {
            final JsonObject data = this.readData(file);
            instance = new PEKey(data);
        }
        Log.debug(LOGGER, instance.toString());
        return instance;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/pekey/key.json");
        final PEKey actual = this.getInstance("/entity/pekey/key.json");
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /** **/
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/pekey/key2.json");
        final PEKey actual = this.getInstance("/entity/pekey/key2.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /** **/
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEKey actual = this.getInstance("/entity/pekey/key3.json");
        actual.writeToBuffer(buffer);
        final PEKey expected = new PEKey(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /** **/
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEKey expected = this.getInstance("/entity/pekey/key4.json");
        expected.writeToBuffer(buffer);
        final PEKey actual = new PEKey();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }

}
