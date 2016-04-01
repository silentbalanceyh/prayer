package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.meta.database.PEVColumn;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class PEVColumnTestCase extends AbstractEntityTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEVColumnTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEVColumn getInstance(final String file) {
        PEVColumn instance = null;
        if (null == file) {
            instance = new PEVColumn();
        } else {
            final JsonObject data = this.readData(file);
            instance = new PEVColumn(data);
        }
        Log.debug(LOGGER, instance.toString());
        return instance;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/pevcolumn/vcolumn.json");
        final PEVColumn actual = this.getInstance("/entity/pevcolumn/vcolumn.json");
        // Compare
        assertTrue(expected.equals(actual.toJson()));
    }

    /** **/
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/pevcolumn/vcolumn2.json");
        final PEVColumn actual = this.getInstance("/entity/pevcolumn/vcolumn2.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /** **/
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEVColumn actual = this.getInstance("/entity/pevcolumn/vcolumn3.json");
        actual.writeToBuffer(buffer);
        final PEVColumn expected = new PEVColumn(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /** **/
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEVColumn expected = this.getInstance("/entity/pevcolumn/vcolumn4.json");
        expected.writeToBuffer(buffer);
        final PEVColumn actual = new PEVColumn();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }

}
