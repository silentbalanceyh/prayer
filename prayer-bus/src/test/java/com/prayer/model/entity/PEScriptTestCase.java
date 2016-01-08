package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.vertx.PEScript;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 测试Model中的PEScript
 * 
 * @author Lang
 *
 */
public class PEScriptTestCase extends AbstractEntityTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEScriptTestCase.class);

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
        // Expected
        final JsonObject data = this.readData("/entity/pescript/script.json");
        // Actual
        final PEScript script = this.getInstance("/entity/pescript/script.json");
        // Compare
        assertEquals(data.encode(), script.toString());
    }

    /**
     * 
     */
    @Test
    public void testToJson() {
        // Expected
        final JsonObject data = this.readData("/entity/pescript/script1.json");
        // Actual
        final PEScript script = this.getInstance("/entity/pescript/script1.json");
        // Compare
        assertEquals(data, script.toJson());
    }

    /**
     * 
     */
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        // Actual
        final PEScript script = this.getInstance("/entity/pescript/script2.json");
        script.writeToBuffer(buffer);
        // Expected
        final PEScript expected = new PEScript(buffer);
        // Compare
        assertEquals(expected, script);
    }

    /**
     * 
     */
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        // Actual
        final PEScript expected = this.getInstance("/entity/pescript/script3.json");
        expected.writeToBuffer(buffer);
        // Expected
        final PEScript actual = new PEScript();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }
    // ~ Private Methods =====================================

    private PEScript getInstance(final String file) {
        final JsonObject data = this.readData(file);
        final PEScript script = new PEScript(data);
        Log.debug(LOGGER, script.toString());
        return script;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
