package com.prayer.bus;

import static com.prayer.util.Error.info;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;

import com.prayer.model.web.StatusCode;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractTemplateTestCase extends AbstractRBTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract String getPath();

    /** **/
    public abstract ConcurrentMap<String, String> getRequiredMap();

    /** **/
    public abstract List<String> getValidateList();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ JUnit Test Case =====================================
    /** **/
    @Test
    public void testPostRequired() {
        for (final String key : this.getRequiredMap().keySet()) {
            final String retStr = this.getRequiredMap().get(key);
            final JsonObject resp = this.sendRequest(key, HttpMethod.POST);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = check30001Resp(resp);
                if (ret) {
                    final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                    info(getLogger(), "[INFO] Display Error: " + display);
                    ret = 0 <= display.indexOf(retStr);
                    assertTrue("[TST] ( 400 : Required -> " + retStr + ") Unsuccessful !", ret);
                } else {
                    fail("[ERR] Basic Information Checking Failure !");
                }
            }
        }
    }

    /** **/
    @Test
    public void testPostValidation() {
        for (final String key : this.getValidateList()) {
            final JsonObject resp = this.sendRequest(key, HttpMethod.POST);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = check30007Resp(resp);
                if (ret) {
                    final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                    info(getLogger(), "[INFO] Display Error: " + display);
                    assertTrue("[TST] ( 400 : Validation Failure ) Unsuccessful !", ret);
                } else {
                    fail("[ERR] Basic Information Checking Failure !");
                }
            }
        }
    }
    // ~ Template Testing ====================================
    // ~ Private Methods =====================================

    private JsonObject sendRequest(final String file, final HttpMethod method) {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders(this.client().getUserName(),
                this.client().getPassword());
        headers.put("Content-Type", "application/json");
        final JsonObject params = this.client().getParameter(file);
        JsonObject resp = null;
        if (HttpMethod.POST == method) {
            resp = this.client().requestPost(this.client().getApi(this.getPath()), headers, params);
        }
        return resp;
    }

    private boolean check30007Resp(final JsonObject resp) {
        final JsonObject data = resp.getJsonObject("data");
        // Error Code
        boolean ret = this.checkErrorCode(data.getJsonObject("error"), -30007);
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
