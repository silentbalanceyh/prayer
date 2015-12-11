package com.prayer.rest.basic.post;

import static com.prayer.util.Error.info;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.AbstractRBTestCase;
import com.prayer.model.web.StatusCode;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public class AccountTestCase extends AbstractRBTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTestCase.class);

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
    public void testUsernameMissing() {
        final JsonObject params = this.getParameter("post/001account.json");
        final ConcurrentMap<String, String> headers = getHeaders(getUserName(), getPassword());
        headers.put("Content-Type", "application/json");
        final JsonObject resp = this.requestPost(this.getApi("/sec/account"), headers, params);
        if (!StringUtil.equals("SKIP", resp.getString("status"))) {
            boolean ret = check30001Resp(resp);
            if (ret) {
                final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                info(LOGGER, "[INFO] Display Error: " + display);
                assertTrue("[TST] ( 400 ) Unsuccessful !", ret);
            } else {
                fail("[ERR] Basic Information Checking Failure !");
            }
        }
    }
    // ~ Private Methods =====================================

    private boolean check30001Resp(final JsonObject resp) {
        final JsonObject data = resp.getJsonObject("data");
        // Error Code
        boolean ret = this.checkErrorCode(data.getJsonObject("error"), -30001);
        // Http Status
        ret = ret && this.checkHttpStatus(resp, StatusCode.BAD_REQUEST.status());
        // Return Code
        ret = ret && this.checkReturnCode(data, ResponseCode.FAILURE);
        // Status Information
        ret = ret && this.checkStatus(data.getJsonObject("status"), StatusCode.BAD_REQUEST);
        // Passed
        ret = ret && StringUtil.equals("PASSED", resp.getString("status"));
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
