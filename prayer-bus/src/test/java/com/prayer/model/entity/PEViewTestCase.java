package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.meta.database.PEView;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class PEViewTestCase extends AbstractEntityTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEViewTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEView getInstance(final String file) {
        PEView instance = null;
        if (null == file) {
            instance = new PEView();
        } else {
            final JsonObject data = this.readData(file);
            instance = new PEView(data);
        }
        Log.debug(LOGGER, instance.toString());
        return instance;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/peview/view.json");
        final PEView actual = this.getInstance("/entity/peview/view.json");
        System.out.println(expected.encodePrettily());
        System.out.println(actual.toJson().encodePrettily());
        // Compare
        assertTrue(expected.equals(actual.toJson()));
    }

    /** **/
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/peview/view2.json");
        final PEView actual = this.getInstance("/entity/peview/view2.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /** **/
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEView actual = this.getInstance("/entity/peview/view3.json");
        actual.writeToBuffer(buffer);
        final PEView expected = new PEView(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /** **/
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEView expected = this.getInstance("/entity/peview/view4.json");
        expected.writeToBuffer(buffer);
        final PEView actual = new PEView();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }

}
