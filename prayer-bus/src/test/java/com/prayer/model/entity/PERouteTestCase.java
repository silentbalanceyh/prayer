package com.prayer.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.util.debug.Log;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class PERouteTestCase extends AbstractEntityTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PERouteTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PERoute getInstance(final String file){
        PERoute instance = null;
        if(null == file){
            instance = new PERoute();
        }else{
            final JsonObject data = this.readData(file);
            instance = new PERoute(data);
        }
        Log.debug(LOGGER,instance.toString());
        return instance;
    }
    // ~ Methods =============================================
    @Test
    public void testFromJson() {
        final JsonObject expected = this.readData("/entity/peroute/route.json");
        final PERoute actual = this.getInstance("/entity/peroute/route.json");
        System.out.println(expected);
        System.out.println(actual);
        // Compare
        assertEquals(expected.encode(), actual.toString());
    }

    /**
     * 
     */
    @Test
    public void testToJson() {
        final JsonObject expected = this.readData("/entity/peroute/route1.json");
        final PERoute actual = this.getInstance("/entity/peroute/route1.json");
        // Compare
        assertEquals(expected, actual.toJson());
    }

    /**
     * 
     */
    @Test
    public void testToBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PERoute actual = this.getInstance("/entity/peroute/route2.json");
        actual.writeToBuffer(buffer);
        final PERoute expected = new PERoute(buffer);
        System.out.println(actual);
        System.out.println(expected);
        // Compare
        assertEquals(expected, actual);
    }

    /**
     * 
     */
    @Test
    public void testFromBuffer() {
        final Buffer buffer = Buffer.buffer();
        final PERoute expected = this.getInstance("/entity/peroute/route3.json");
        expected.writeToBuffer(buffer);
        final PERoute actual = new PERoute();
        actual.readFromBuffer(Constants.POS, buffer);
        // Compare
        assertEquals(expected, actual);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
