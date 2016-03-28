package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 测试Model中的PEScript
 * 
 * @author Lang
 *
 */
public class PEScriptTestCase extends AbstractEntityTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEScriptTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEScript getInstance(final String file){
        PEScript instance = null;
        if(null == file){
            instance = new PEScript();
        }else{
            final JsonObject data = this.readData(file);
            instance = new PEScript(data);
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
        final JsonObject expected = this.readData("/entity/pescript/script.json");
        final PEScript actual = this.getInstance("/entity/pescript/script.json");
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /**
     * 
     */
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/pescript/script1.json");
        final PEScript actual = this.getInstance("/entity/pescript/script1.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /**
     * 
     */
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEScript actual = this.getInstance("/entity/pescript/script2.json");
        actual.writeToBuffer(buffer);
        final PEScript expected = new PEScript(buffer);
        // Compare
        assertEquals(expected, actual);
    }

    /**
     * 
     */
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PEScript expected = this.getInstance("/entity/pescript/script3.json");
        expected.writeToBuffer(buffer);
        final PEScript actual = new PEScript();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
