package com.prayer.bus;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

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
        }else if(HttpMethod.DELETE == method){
            resp = this.client().requestDelete(this.client().getApi(path), headers, params);
        }else if(HttpMethod.GET == method){
            resp = this.client().requestGet(this.client().getApi(path), headers);
        }
        return resp;
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
        if (StatusCode.OK.status() != data.getInteger("status")) {
            ret = false;
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
