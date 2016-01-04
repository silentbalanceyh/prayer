package com.prayer.console.commands;

import static com.prayer.util.Instance.singleton;

import com.prayer.bus.impl.console.StatusSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.bus.console.StatusService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.io.PropertyKit;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
class JdbcHelper {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final PropertyKit LOADER = new PropertyKit(getClass(), Resources.DB_CFG_FILE); // NOPMD
    /** **/
    private transient final StatusService service;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public JdbcHelper(){
        this.service = singleton(StatusSevImpl.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param category
     * @return
     */
    public JsonObject getMetadata(final String category){
        final JsonObject params = this.getJdbcParams(category);
        return this.getResult(params);
    }
    // ~ Private Methods =====================================

    private JsonObject getResult(final JsonObject params) {
        JsonObject retJson = new JsonObject();
        final ServiceResult<JsonObject> ret = service.getMetadata(params);
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            retJson = ret.getResult();
        } else {
            retJson.put("error", ret.getErrorMessage());
        }
        return retJson;
    }
    
    private JsonObject getJdbcParams(final String category) {
        final JsonObject ret = new JsonObject();
        ret.put(Constants.CMD.STATUS.JDBC_URL, LOADER.getString(category + ".jdbc.url"));
        ret.put(Constants.CMD.STATUS.USERNAME, LOADER.getString(category + ".jdbc.username"));
        ret.put(Constants.CMD.STATUS.PASSWORD, LOADER.getString(category + ".jdbc.password"));
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
