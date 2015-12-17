package com.prayer.rest.basic.put;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public class AccountTestCase extends AbstractPutTestCase {
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

    /** **/
    @Override
    public String getPath() {
        return "/sec/account";
    }

    /** **/
    @Override
    public ConcurrentMap<String, String> getRequiredRules() {
        final ConcurrentMap<String, String> requiredRules = new ConcurrentHashMap<>();
        requiredRules.put("put/account/missing-params-001", "name = id");
        return requiredRules;
    }

    /** **/
    @Override
    public List<String> getValidateRules() {
        final List<String> validateRules = new ArrayList<>();
        validateRules.add("put/account/non-existing-params-001");
        validateRules.add("put/account/format-params-001");
        validateRules.add("put/account/format-params-002");
        validateRules.add("put/account/format-params-003");
        validateRules.add("put/account/format-params-004");
        return validateRules;
    }

    /** **/
    @Override
    public List<String> getDependRules() {
        final List<String> dependRules = new ArrayList<>();
        dependRules.add("put/account/dependant-params-001");
        dependRules.add("put/account/dependant-params-002");
        dependRules.add("put/account/dependant-params-003");
        return dependRules;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testPutSuccess() {
        // 1.创建第一条记录
        final JsonObject firstParam = this.createdParams("put/account/dependant-success-001/first.json");
        // 2.创建第二条记录
        final JsonObject secondParam = this.createdParams("put/account/dependant-success-001/second.json");
        // 3.读取更新过后的信息
        final JsonObject updatedData = this.client().getParameter("put/account/dependant-success-001/data.json");
        // 4.将updatedData中参数合并到secondParam中
        secondParam.mergeIn(updatedData);
        final JsonObject resp = this.sendRequest(this.getPath(), secondParam, HttpMethod.PUT);
        if(!StringUtil.equals("SKIP", resp.getString("status"))){
            boolean ret = this.checkSuccess(resp);
            if(ret){
                // 成功Update记录信息
                success(ret,resp);
            }
            // 删除创建的信息
            this.deleteRecord(firstParam);
            this.deleteRecord(secondParam);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
