package com.prayer.rest.basic.put;

import static com.prayer.util.Log.info;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;

import com.prayer.bus.AbstractRBTestCase;
import com.prayer.bus.ErrorChecker;
import com.prayer.model.StatusCode;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractPutTestCase extends AbstractRBTestCase {
    // ~ Static Fields =======================================
    /** Request Params : id **/
    private static final String ID = "id";
    /** Request Params : uniqueId **/
    private static final String UK_ID = "uniqueId";
    /** Request Params : data **/
    private static final String DATA = "data";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract String getPath();

    /** **/
    public abstract ConcurrentMap<String, String> getRequiredRules();

    /** **/
    public abstract List<String> getValidateRules();

    /** **/
    public abstract List<String> getDependRules();

    // ~ Override Methods ====================================
    /** **/
    @Test
    public void testPutRequired() {
        for (final String key : this.getRequiredRules().keySet()) {
            final String retStr = this.getRequiredRules().get(key);
            final JsonObject params = this.client().getParameter(key + "/data.json");
            final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.PUT);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30001(resp);
                // Failure Output
                failure(getLogger(), StatusCode.BAD_REQUEST, -30001, resp, ret, HttpMethod.PUT, retStr);
            }
        }
    }

    /** **/
    @Test
    public void testPutValidation() {
        for (final String key : this.getValidateRules()) {
            final JsonObject params = this.prepareParams(key);
            final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.PUT);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30007(resp);
                // 30007 Failure Output
                if (ret) {
                    failure(getLogger(), StatusCode.BAD_REQUEST, -30007, resp, ret, HttpMethod.PUT, null);
                } else {
                    ret = ErrorChecker.check30004(resp);
                    // 30004 Failure Output
                    failure(getLogger(), StatusCode.BAD_REQUEST, -30004, resp, ret, HttpMethod.PUT, null);
                }
                // 验证成功，删除添加好的数据信息
                this.deleteRecord(params);
            }
        }
    }

    /** **/
    @Test
    public void testPutDependent() {
        for (final String key : this.getDependRules()) {
            // 1.创建第一条记录
            final JsonObject firstParam = this.createdParams(key + "/first.json");
            // 2.创建第二条记录
            final JsonObject secondParam = this.createdParams(key + "/second.json");
            // 3.读取更新过后的信息
            final JsonObject updatedData = this.client().getParameter(key + "/data.json");
            // 4.将updatedData中参数合并到secondParam中
            secondParam.mergeIn(updatedData);
            final JsonObject resp = this.sendRequest(this.getPath(), secondParam, HttpMethod.PUT);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30007(resp);
                // 30007 Failure Output
                if (ret) {
                    failure(getLogger(), StatusCode.BAD_REQUEST, -30007, resp, ret, HttpMethod.PUT, null);
                }
                // 验证成功，删除添加好的数据信息
                this.deleteRecord(firstParam);
                this.deleteRecord(secondParam);
            }
        }
    }

    // ~ Methods =============================================
    /** **/
    protected void deleteRecord(final JsonObject params) {
        if (params.containsKey(ID)) {
            final JsonObject ids = new JsonObject();
            ids.put(ID, params.getString(ID));
            this.sendRequest(this.getPath(), ids, HttpMethod.DELETE);
        }
    }

    /** **/
    protected JsonObject createdParams(final String key) {
        final JsonObject params = this.client().getParameter(key);
        final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.POST);
        // 获取创建Record对应的返回的JsonObject
        if(!StringUtil.equals("SKIP", resp.getString("status"))){
            final JsonObject data = resp.getJsonObject(DATA).getJsonObject(DATA);
            if (null != data && data.containsKey(UK_ID)) {
                params.put(ID, data.getString(UK_ID));
            }
        }
        return params;
    }
    // ~ Private Methods =====================================

    private JsonObject prepareParams(final String key) {
        JsonObject params = null;
        if (0 <= key.indexOf("format-params")) {
            params = this.createdParams(key + "/before.json");
            // 读取新的json文件
            final JsonObject updatedData = this.client().getParameter(key + "/data.json");
            params.mergeIn(updatedData);
        } else {
            params = this.client().getParameter(key + "/data.json");
        }
        info(getLogger(),"Final Param Data : " + params.encode());
        return params;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
