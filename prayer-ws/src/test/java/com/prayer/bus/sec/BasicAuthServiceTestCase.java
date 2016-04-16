package com.prayer.bus.sec;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.AbstractSDTestCase;
import com.prayer.model.business.behavior.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class BasicAuthServiceTestCase extends AbstractSDTestCase{
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthServiceTestCase.class);
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
    public void testApiLogin(){
        final JsonObject params = this.getParameter("login.json");
        final ServiceResult<JsonArray> result = this.getRService().find(params);
        if(!console(result)){
            fail("[ERR] Api Call Error ! /api/sec/login");
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
