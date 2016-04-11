package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.entity.Entity;
import com.prayer.util.io.IOKit;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractEntityTool {
    // ~ Static Fields =======================================
    /** **/
    protected static String EMPTY_FILE = "/entity/empty.json";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    public abstract Entity getInstance(String file);

    // ~ Methods =============================================
    /**
     * 读取JsonObject的方法
     * 
     * @param file
     * @return
     */
    protected JsonObject readData(final String file) {
        final String content = IOKit.getContent(file);
        final JsonObject json = new JsonObject();
        if (null != content) {
            json.mergeIn(new JsonObject(content));
            // Fix id -> uniqueId
            if(json.containsKey("id")){
               final Object value = json.getValue("id");
               json.put(Constants.PID, value);
               json.remove("id");
            }
        }
        return json;
    }

    /**
     * 检查
     */
    @Test
    public void testJsonInvalid() {
        final Entity actual = this.getInstance(EMPTY_FILE);
        // Compare
        final Entity expected = this.getInstance(null);
        assertEquals(expected, actual);
    }

    /**
     * 
     */
    @Test
    public void testBufferInvalid() {
        final Entity expected = this.getInstance(null);
        
        final Buffer buffer = Buffer.buffer();
        final Entity actual = this.getInstance(EMPTY_FILE);
        actual.writeToBuffer(buffer);
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected.toJson(), actual.toJson());
    }
    /**
     * 
     */
    @Test
    public void testRef(){
        final JsonObject data = this.readData(EMPTY_FILE);
        final Entity expected = this.getInstance(null);
        final Entity actual = expected.fromJson(data);
        assertTrue(expected == actual);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
