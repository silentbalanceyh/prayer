package com.prayer.rest.basic.post;

import java.util.List;

import org.junit.Test;

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
public abstract class AbstractPageTestCase extends AbstractPostTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract List<String> getValidate30010();

    /** **/
    public abstract List<String> getValidate30019();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test
    public void test30010Error() {
        for (final String file : this.getValidate30010()) {
            final JsonObject params = this.client().getParameter(file);
            final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.POST);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30010(resp);
                // Failure Output
                failure(getLogger(), StatusCode.BAD_REQUEST, -30010, resp, ret, HttpMethod.POST, "decoding met error");
            }
        }
    }

    /** **/
    @Test
    public void test30019Error() {
        for (final String file : this.getValidate30019()) {
            final JsonObject params = this.client().getParameter(file);
            final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.POST);
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30019(resp);
                // Failure Output
                failure(getLogger(), StatusCode.BAD_REQUEST, -30019, resp, ret, HttpMethod.POST, null);
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
