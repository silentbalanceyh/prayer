package com.prayer.bus;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.model.web.StatusCode;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractRBTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Rest Client **/
    private transient final RestClient rest = singleton(RestClient.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected RestClient client(){
        return this.rest;
    }
    // ~ Response Check =====================================
    /** **/
    protected boolean checkSuccess(final JsonObject resp) {
        boolean ret = true;
        if (StatusCode.OK.status() != resp.getInteger("code")) {
            ret = false;
        }
        if (!StringUtil.equals(resp.getString("status"), "PASSED")) {
            ret = false;
        }
        JsonObject data = resp.getJsonObject("data");
        final ResponseCode code = fromStr(ResponseCode.class, data.getString("returnCode"));
        if (ResponseCode.SUCCESS != code) {
            ret = false;
        }
        if (StatusCode.OK.status() != data.getInteger("status")) {
            ret = false;
        }
        data = data.getJsonObject("data");
        if (data.isEmpty()) {
            ret = false;
        }
        return ret;
    }

    /** **/
    protected boolean checkErrorCode(final JsonObject error, final int code) {
        final int errorCode = error.getInteger("code");
        return errorCode == code;
    }

    /** **/
    protected boolean checkHttpStatus(final JsonObject resp, final int code) {
        final int statusCode = resp.getInteger("code");
        return statusCode == code;
    }

    /** **/
    protected boolean checkReturnCode(final JsonObject data, final ResponseCode retCode) {
        final ResponseCode code = fromStr(ResponseCode.class, data.getString("returnCode"));
        return code == retCode;
    }

    /** **/
    protected boolean checkStatus(final JsonObject status, final StatusCode statusCode) {
        boolean ret = true;
        if (status.getInteger("code") != statusCode.status()) {
            ret = false;
        }
        if (!StringUtil.equals(status.getString("literal"), statusCode.name().toUpperCase(Locale.getDefault()))) {
            ret = false;
        }
        return ret;
    }
    // ~ Template Testing =====================================
    /** **/
    protected void testPostValidation(final String api, final String inputFile, final String retStr) {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders(rest.getUserName(), rest.getPassword());
        headers.put("Content-Type", "application/json");
        final JsonObject params = rest.getParameter(inputFile);
        final JsonObject resp = rest.requestPost(rest.getApi(api), headers, params);
        if (!StringUtil.equals("SKIP", resp.getString("status"))) {
            boolean ret = check30001Resp(resp);
            if (ret) {
                final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                info(getLogger(), "[INFO] Display Error: " + display);
                ret = 0 <= display.indexOf(retStr);
                assertTrue("[TST] ( 400 : " + retStr + ") Unsuccessful !", ret);
            } else {
                fail("[ERR] Basic Information Checking Failure !");
            }
        }
    }
    /** **/
    protected void testPostRequired(final String api, final String inputFile, final String retStr) {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders(rest.getUserName(), rest.getPassword());
        headers.put("Content-Type", "application/json");
        final JsonObject params = rest.getParameter(inputFile);
        final JsonObject resp = rest.requestPost(rest.getApi(api), headers, params);
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
