package com.prayer.rest.basic.post;

import static com.prayer.util.Error.info;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ErrorChecker;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public class AccountPageTestCase extends AbstractPostTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountPageTestCase.class);

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
        return "/sec/page/account";
    }

    /** **/
    @Override
    public ConcurrentMap<String, String> getRequiredRules() {
        final ConcurrentMap<String, String> requiredRules = new ConcurrentHashMap<>();
        requiredRules.put("page/account/missing-params-001.json", "name = page");
        requiredRules.put("page/account/missing-params-002.json", "name = page->index");
        requiredRules.put("page/account/missing-params-003.json", "name = page->size");
        return requiredRules;
    }

    /** **/
    @Override
    public List<String> getValidateRules() {
        final List<String> validateRules = new ArrayList<>();
        // Page Checking : PagerValidator
        validateRules.add("page/account/range-params-001.json");
        validateRules.add("page/account/range-params-002.json");
        validateRules.add("page/account/range-params-003.json");
        validateRules.add("page/account/range-params-004.json");
        // Order Checking
        validateRules.add("page/account/array-params-001.json");
        return validateRules;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void test30010PageError() {
        final JsonObject params = this.client().getParameter("page/account/spec-params-001.json");
        final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.POST);
        if (!StringUtil.equals("SKIP", resp.getString("status"))) {
            boolean ret = ErrorChecker.check30010(resp);
            if (ret) {
                final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                info(getLogger(), "(POST) Display Error: " + display + "\n");
                ret = 0 <= display.indexOf("decoding met error");
                assertTrue("[TST] ( 400 : Required -> decoding met error) Unsuccessful !", ret);
            } else {
                fail("[ERR] Basic Information Checking Failure !");
            }
        }
    }
    /** **/
    @Test
    public void test30010OrdersError() {
        final JsonObject params = this.client().getParameter("page/account/spec-params-002.json");
        final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.POST);
        if (!StringUtil.equals("SKIP", resp.getString("status"))) {
            boolean ret = ErrorChecker.check30010(resp);
            if (ret) {
                final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                info(getLogger(), "(POST) Display Error: " + display + "\n");
                ret = 0 <= display.indexOf("decoding met error");
                assertTrue("[TST] ( 400 : Required -> decoding met error) Unsuccessful !", ret);
            } else {
                fail("[ERR] Basic Information Checking Failure !");
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
