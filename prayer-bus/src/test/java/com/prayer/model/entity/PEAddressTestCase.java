package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.vertx.PEAddress;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class PEAddressTestCase extends AbstractEntityTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEAddressTestCase.class);

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
        final JsonObject expected = this.readData("/entity/peaddress/address.json");
        final PEAddress actual = this.getInstance("/entity/peaddress/address.json");
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /**
     * 
     */
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/peaddress/address1.json");
        final PEAddress actual = this.getInstance("/entity/peaddress/address1.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /**
     * 
     */
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEAddress actual = this.getInstance("/entity/peaddress/address2.json");
        actual.writeToBuffer(buffer);
        final PEAddress expected = new PEAddress(buffer);
        // Compare
        assertEquals(expected, actual);
    }
    
    /**
     * 
     */
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEAddress expected = this.getInstance("/entity/peaddress/address3.json");
        expected.writeToBuffer(buffer);
        final PEAddress actual = new PEAddress();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }
    // ~ Private Methods =====================================

    private PEAddress getInstance(final String file) {
        final JsonObject data = this.readData(file);
        final PEAddress address = new PEAddress(data);
        Log.debug(LOGGER, address.toString());
        return address;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
