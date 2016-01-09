package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.database.PETrigger;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
/**
 * 
 * @author Lang
 *
 */
public class PETriggerTestCase extends AbstractEntityTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PETriggerTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PETrigger getInstance(final String file) {
        PETrigger instance = null;
        if (null == file) {
            instance = new PETrigger();
        } else {
            final JsonObject data = this.readData(file);
            instance = new PETrigger(data);
        }
        Log.debug(LOGGER, instance.toString());
        return instance;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/petrigger/trigger.json");
        final PETrigger actual = this.getInstance("/entity/petrigger/trigger.json");
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /** **/
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/petrigger/trigger2.json");
        final PETrigger actual = this.getInstance("/entity/petrigger/trigger2.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /** **/
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PETrigger actual = this.getInstance("/entity/petrigger/trigger3.json");
        actual.writeToBuffer(buffer);
        final PETrigger expected = new PETrigger(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /** **/
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PETrigger expected = this.getInstance("/entity/petrigger/trigger4.json");
        expected.writeToBuffer(buffer);
        final PETrigger actual = new PETrigger();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }

}
