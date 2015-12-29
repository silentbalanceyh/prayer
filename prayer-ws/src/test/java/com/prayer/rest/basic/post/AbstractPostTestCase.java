package com.prayer.rest.basic.post;

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
public abstract class AbstractPostTestCase extends AbstractRBTestCase {
    // ~ Static Fields =======================================
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

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ JUnit Test Case =====================================
    /** **/
    @Test
    public void testPostRequired() {
        for (final String key : this.getRequiredRules().keySet()) {
            final String retStr = this.getRequiredRules().get(key);
            final JsonObject params = this.client().getParameter(key);
            final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.POST);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30001(resp);
                // Failure Output
                failure(getLogger(), StatusCode.BAD_REQUEST, -30001, resp, ret, HttpMethod.POST, retStr);
            }
        }
    }

    /** **/
    @Test
    public void testPostValidation() {
        for (final String key : this.getValidateRules()) {
            final JsonObject params = this.client().getParameter(key);
            final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.POST);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30007(resp);
                // 30007 Failure Output
                if (ret) {
                    failure(getLogger(), StatusCode.BAD_REQUEST, -30007, resp, ret, HttpMethod.POST, null);
                } else {
                    ret = ErrorChecker.check30004(resp);
                    // 30004 Failure Output
                    failure(getLogger(), StatusCode.BAD_REQUEST, -30004, resp, ret, HttpMethod.POST, null);
                }
            }
        }
    }
    // ~ Template Testing ====================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
