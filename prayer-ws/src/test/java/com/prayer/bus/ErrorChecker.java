package com.prayer.bus;

import static com.prayer.util.Converter.fromStr;

import java.util.Locale;

import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.web.StatusCode;

import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public final class ErrorChecker {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================

    /** **/
    public static boolean check30014(final JsonObject resp) {
        return checkError(resp, -30014, StatusCode.UNAUTHORIZED);
    }

    /** **/
    public static boolean check30019(final JsonObject resp) {
        return checkError(resp, -30019, StatusCode.BAD_REQUEST);
    }

    /** **/
    public static boolean check30007(final JsonObject resp) {
        return checkError(resp, -30007, StatusCode.BAD_REQUEST);
    }

    /** **/
    public static boolean check30004(final JsonObject resp) {
        return checkError(resp, -30004, StatusCode.BAD_REQUEST);
    }

    /** **/
    public static boolean check30010(final JsonObject resp) {
        return checkError(resp, -30010, StatusCode.BAD_REQUEST);
    }

    /** **/
    public static boolean check30001(final JsonObject resp) {
        return checkError(resp, -30001, StatusCode.BAD_REQUEST);
    }

    // ~ Segment Methods =====================================

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private ErrorChecker() {
    }

    /** **/
    private static boolean checkErrorCode(final JsonObject error, final int code) {
        final int errorCode = error.getInteger("code");
        return errorCode == code;
    }

    /** **/
    private static boolean checkHttpStatus(final JsonObject resp, final int code) {
        final int statusCode = resp.getInteger("code");
        return statusCode == code;
    }

    /** **/
    private static boolean checkReturnCode(final JsonObject data, final ResponseCode retCode) {
        final ResponseCode code = fromStr(ResponseCode.class, data.getString("returnCode"));
        return code == retCode;
    }

    /** **/
    private static boolean checkStatus(final JsonObject status, final StatusCode statusCode) {
        boolean ret = true;
        if (status.getInteger("code") != statusCode.status()) {
            ret = false;
        }
        if (!StringUtil.equals(status.getString("literal"), statusCode.name().toUpperCase(Locale.getDefault()))) {
            ret = false;
        }
        return ret;
    }

    /** **/
    private static boolean checkError(final JsonObject resp, final int errorCode, final StatusCode statusCode) {
        final JsonObject data = resp.getJsonObject("data");
        // Error Code
        boolean ret = false;
        if (null != data && null != data.getJsonObject("error")) {
            ret = checkErrorCode(data.getJsonObject("error"), errorCode);
        }
        // Http Status
        ret = ret && checkHttpStatus(resp, statusCode.status());
        if (null != data && null != data.getJsonObject("status")) {
            // Return Code
            ret = ret && checkReturnCode(data, ResponseCode.FAILURE);
            // Status Information
            ret = ret && checkStatus(data.getJsonObject("status"), statusCode);
        }
        // Passed
        ret = ret && StringUtil.equals("PASSED", resp.getString("status"));
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
