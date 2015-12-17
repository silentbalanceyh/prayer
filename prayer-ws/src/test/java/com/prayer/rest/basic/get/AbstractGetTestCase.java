package com.prayer.rest.basic.get;

import java.util.List;

import org.junit.Test;

import com.prayer.bus.AbstractRBTestCase;
import com.prayer.bus.ErrorChecker;
import com.prayer.model.web.StatusCode;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractGetTestCase extends AbstractRBTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract List<String> getValidateRules();
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ JUnit Test Case =====================================
    /** No Required for -30001 **/

    /** Test Validation For Parameters **/
    @Test
    public void testGetValidation() {
        for (final String key : this.getValidateRules()) {
            final JsonObject params = this.client().getParameter(key);
            final String url = params.getString("url");
            final JsonObject resp = this.sendRequest(url, params, HttpMethod.GET);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30007(resp);
                // Failure Output
                failure(getLogger(), StatusCode.BAD_REQUEST, -30007, resp, ret, HttpMethod.GET, null);
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
