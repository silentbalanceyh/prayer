package com.prayer.bus;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.info;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.model.StatusCode;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.http.HttpMethod;
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
    protected RestClient client() {
        return this.rest;
    }

    // ~ Util Methods ========================================
    /** **/
    protected JsonObject sendRequest(final String path, final JsonObject params, final HttpMethod method) {
        final ConcurrentMap<String, String> headers = RestClient.getHeaders(this.client().getUserName(),
                this.client().getPassword());
        headers.put("Content-Type", "application/json");
        JsonObject resp = null;
        if (HttpMethod.POST == method) {
            resp = this.client().requestPost(this.client().getApi(path), headers, params);
        } else if (HttpMethod.DELETE == method) {
            resp = this.client().requestDelete(this.client().getApi(path), headers, params);
        } else if (HttpMethod.GET == method) {
            resp = this.client().requestGet(this.client().getApi(path), headers);
        } else if (HttpMethod.PUT == method) {
            resp = this.client().requestPut(this.client().getApi(path), headers, params);
        }
        return resp;
    }

    /**
     * 简化代码的综合统一输出信息的方法
     * 
     * @param logger
     * @param code
     * @param errorCode
     * @param resp
     * @param checkRet
     * @param subStr
     */
    protected boolean failure(final Logger logger, final StatusCode code, final int errorCode, final JsonObject resp,
            Boolean checkRet, final HttpMethod method, final String subStr) {
        if (checkRet) {
            final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
            info(logger, "[ERR" + errorCode + "] <" + method.name() + "> Display Error : " + display);
            // 只有subStr不为null的时候执行这种检查
            if (null != subStr) {
                checkRet = 0 <= display.indexOf(subStr);
                assertTrue("[TST] ( " + code.status() + " : " + code.toString() + " ) Unsuccessful ! ", checkRet);
            } else {
                assertTrue("[TST] ( " + code.status() + " : " + code.toString() + " ) Unsuccessful ! ", checkRet);
            }
        } else {
            fail("[ERR" + errorCode + "] <" + method.name() + "> ( " + code.status() + " : " + code.toString()
                    + " ) Basic Information Checking Failure !");
        }
        // 特殊的结果返回使用
        return checkRet;
    }

    /**
     * 简化代码的统一综合输出信息的方法
     * 
     * @param checkRet
     * @param resp
     */
    protected void success(final Boolean checkRet, final JsonObject resp) {
        assertTrue("[TST] ( 200 : " + resp.getJsonObject("data").getJsonObject("data").encode() + " ) Unsuccessful !",
                checkRet);
    }

    // ~ Response Check ======================================
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
        final Object statusVal = data.getValue("status");
        if (Integer.class == statusVal.getClass()) {
            // Security Web Service Interface
            if (StatusCode.OK.status() != data.getInteger("status")) {
                ret = false;
            }
        }else if(JsonObject.class == statusVal.getClass()){
            // Business Web Service Interface
            final JsonObject status = data.getJsonObject("status");
            if (StatusCode.OK.status() != status.getInteger("code")){
                ret = false;
            }
        }
        data = data.getJsonObject("data");
        if (data.isEmpty()) {
            ret = false;
        }
        return ret;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
