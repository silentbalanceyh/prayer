package com.prayer.bus.meta;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.AbstractSevTestCase;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class ScriptServiceTestCase extends AbstractSevTestCase{
    // ~ Static Fields =======================================

    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptServiceTestCase.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    // ~ Methods =============================================
    /** **/
    @Test
    public void testApiMetaPage(){
        final JsonObject params = this.getParameter("page.meta.script.json");
        final ServiceResult<JsonObject> result = this.getMService().page(params);
        if(!console(result)){
            fail("[ERR] Api Call Error ! /api/sec/page/meta/script ");
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
