package com.prayer.rest.basic.post;

import static com.prayer.util.Error.info;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.prayer.bus.ErrorChecker;

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
                if (ret) {
                    final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                    info(getLogger(), "(POST) Display Error: " + display);
                    ret = 0 <= display.indexOf("decoding met error");
                    assertTrue("[TST] ( 400 : Required -> decoding met error) Unsuccessful !", ret);
                } else {
                    fail("[ERR] Basic Information Checking Failure !");
                }
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
                if (ret) {
                    final String display = resp.getJsonObject("data").getJsonObject("error").getString("display");
                    info(getLogger(), "(POST) Display Error: " + display);
                    assertTrue("[TST] ( 400 : Required -> OrderBy Error ) Unsuccessful !", ret);
                } else {
                    fail("[ERR] Basic Information Checking Failure !");
                }
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
