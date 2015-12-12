package com.prayer.rest.basic;

import static com.prayer.util.Error.info;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.AbstractRBTestCase;
import com.prayer.bus.RestClient;
import com.prayer.model.web.StatusCode;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 因为headers中的内容会有变化，所以
 * 
 * @author Lang
 *
 */
public class LoginTestCase extends AbstractRBTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginTestCase.class);

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
    /**
     * No Header Send
     */
    @Test
    public void testLoginNoHeader() {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders(null, null);
        this.testFailure(headers, "[E30014]");
    }

    /**
     * No Username Provided
     */
    @Test
    public void testLoginNoUsername() {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders(null, "pl,okmijn123");
        this.testFailure(headers, "USERNAME.MISSING");
    }

    /**
     * No Password Provided
     */
    @Test
    public void testLoginNoPwD() {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders("lang.yu", null);
        this.testFailure(headers, "PASSWORD.MISSING");
    }

    /**
     * User Not Found
     */
    @Test
    public void testLoginUserNotFound() {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders("user.not.found", "pl,okmijn123");
        this.testFailure(headers, "USER.NOT.FOUND");
    }

    /**
     * Auth Failure
     */
    @Test
    public void testLoginAuthFail() {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders("lang.yu", "password.error");
        this.testFailure(headers, "AUTH.FAILURE");
    }

    /**
     * Success
     */
    @Test
    public void testLoginSuccess() {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders("lang.yu", "pl,okmijn123");
        final JsonObject resp = this.client().requestGet(this.client().getApi("/sec/login"), headers);
        if (!StringUtil.equals("SKIP", resp.getString("status"))) {
            boolean ret = this.checkSuccess(resp);
            assertTrue(
                    "[TST] ( 200 : " + resp.getJsonObject("data").getJsonObject("data").encode() + " ) Unsuccessful !",
                    ret);
        }
    }
    // ~ Private Methods =====================================

    private void testFailure(final ConcurrentMap<String, String> headers, final String retStr) {
        final JsonObject resp = this.client().requestGet(this.client().getApi("/sec/login"), headers);
        if (!StringUtil.equals("SKIP", resp.getString("status"))) {
            boolean ret = checkResp(resp);
            if (ret) {
                final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                info(LOGGER, "[INFO] Display Error: " + display);
                ret = 0 <= display.indexOf(retStr);
                assertTrue("[TST] ( 401 :" + retStr + " ) Unsuccessful !", ret);
            } else {
                fail("[ERR] Basic Information Checking Failure !");
            }
        }
    }

    private boolean checkResp(final JsonObject resp) {
        final JsonObject data = resp.getJsonObject("data");
        // Error Code
        boolean ret = this.checkErrorCode(data.getJsonObject("error"), -30014);
        // Http Status
        ret = ret && this.checkHttpStatus(resp, StatusCode.UNAUTHORIZED.status());
        // Return Code
        ret = ret && this.checkReturnCode(data, ResponseCode.FAILURE);
        // Status Information
        ret = ret && this.checkStatus(data.getJsonObject("status"), StatusCode.UNAUTHORIZED);
        // Passed
        ret = ret && StringUtil.equals("PASSED", resp.getString("status"));
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
