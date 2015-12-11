package com.prayer.rest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.AbstractRestBasicTestCase;
import com.prayer.model.web.StatusCode;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public class LoginRestTestCase extends AbstractRestBasicTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRestTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testLoginNoHeader() {
        final ConcurrentMap<String, String> retMap = getBasicHeaders(null, null);
        final JsonObject resp = this.get(this.api("/sec/login"), retMap);
        if (!StringUtil.equals("SKIP", resp.getString("status"))) {
            boolean ret = checkResp(resp);
            if (ret) {
                final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                ret = (0 <= display.indexOf("[E30014]"));
                assertTrue("[TST] Unsuccessful ! Please check display message !", ret);
            } else {
                fail("[ERR] Basic Information Checking Failure !");
            }
        }
    }
    // ~ Private Methods =====================================

    private boolean checkResp(final JsonObject resp) {
        final JsonObject data = resp.getJsonObject("data");
        // Error Code
        boolean ret = this.checkErrorCode(data.getJsonObject("error"), -30014);
        // Http Status
        ret = ret && this.checkHttpStatus(resp, StatusCode.UNAUTHORIZED.status());
        // Return Code
        ret = ret && this.checkReturnCode(data, ResponseCode.FAILURE);
        // Status Information
        ret = ret && this.checkStatus(data.getJsonObject("status"), StatusCode.UNAUTHORIZED.status(),
                StatusCode.UNAUTHORIZED.toString());
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
