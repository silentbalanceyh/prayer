package com.prayer.bus.record;

import com.prayer.base.AbstractSDTestCase;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractRTestCase extends AbstractSDTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    public JsonObject callSevApiSave(final String path) {
        final JsonObject params = this.getParameter(path);
        final ServiceResult<JsonObject> ret = this.getRService().save(params);
        return ret.getResult();
    }

    /** **/
    public JsonObject callSevApiModify(final String path) {
        final JsonObject params = this.getParameter(path);
        final ServiceResult<JsonObject> ret = this.getRService().modify(params);
        return ret.getResult();
    }

    /** **/
    public JsonArray callSevApiFind(final String path) {
        final JsonObject params = this.getParameter(path);
        final ServiceResult<JsonArray> ret = this.getRService().find(params);
        return ret.getResult();
    }

    /** **/
    public JsonObject callSevApiRemove(final String path) {
        final JsonObject params = this.getParameter(path);
        final ServiceResult<JsonObject> ret = this.getRService().remove(params);
        return ret.getResult();
    }

    /** **/
    public JsonObject callSevApiPage(final String path) {
        final JsonObject params = this.getParameter(path);
        final ServiceResult<JsonObject> ret = this.getRService().page(params);
        return ret.getResult();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
