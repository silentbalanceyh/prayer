package com.prayer.rest.basic.post;

import static com.prayer.util.Error.info;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;

import com.prayer.bus.AbstractRBTestCase;
import com.prayer.bus.ErrorChecker;

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
                if (ret) {
                    final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                    info(getLogger(), "(POST) Display Error: " + display + "\n");
                    ret = 0 <= display.indexOf(retStr);
                    assertTrue("[TST] ( 400 : -30001 ) Required -> " + retStr + " Unsuccessful !", ret);
                } else {
                    fail("[ERR] Basic Information Checking Failure !");
                }
            }
        }
    }

    /** **/
    @Test
    public void testPostValidation() {
        for (final String key : this.getValidateRules()) {
            final JsonObject params = this.client().getParameter(key);
            final JsonObject resp = this.sendRequest(this.getPath(), params, HttpMethod.POST);
            final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
            if (!StringUtil.equals("SKIP", resp.getString("status"))) {
                boolean ret = ErrorChecker.check30007(resp);
                if (ret) {
                    info(getLogger(), "(POST) Display Error: " + display + "\n");
                    assertTrue("[TST] ( 400 : -30007 ): Validation Failure/Unsuccessful !", ret);
                } else {
                    ret = ErrorChecker.check30004(resp);
                    if (ret) {
                        info(getLogger(), "(POST) Display Error: " + display + "\n");
                        assertTrue("[TST] ( 400 : -30004 ): Validation Failure/Unsuccessful !", ret);
                    } else {
                        fail("[ERR] Basic Information Checking Failure !");
                    }
                }
            }
        }
    }
    // ~ Template Testing ====================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
