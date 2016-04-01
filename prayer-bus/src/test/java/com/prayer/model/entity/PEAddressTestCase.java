package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class PEAddressTestCase extends AbstractEntityTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEAddressTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEAddress getInstance(final String file){
        PEAddress instance = null;
        if(null == file){
            instance = new PEAddress();
        }else{
            final JsonObject data = this.readData(file);
            instance = new PEAddress(data);
        }
        Log.debug(LOGGER,instance.toString());
        return instance;
    }
    // ~ Methods =============================================
    /**
     * 检查
     */
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/peaddress/address.json");
        final PEAddress actual = this.getInstance("/entity/peaddress/address.json");
        // Compare
        assertTrue(expected.equals(actual.toJson()));
    }

    /**
     * 
     */
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/peaddress/address1.json");
        final PEAddress actual = this.getInstance("/entity/peaddress/address1.json");
        // Compare
        assertTrue(expected.equals(actual.toJson()));
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
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
