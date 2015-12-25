package com.prayer.vx.console.commands;

import static com.prayer.util.Instance.singleton;

import org.apache.commons.cli.CommandLine;

import com.prayer.bus.impl.console.StatusSevImpl;
import com.prayer.facade.bus.console.StatusService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.PropertyKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class StatusCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final PropertyKit LOADER = new PropertyKit(getClass(), Resources.DB_CFG_FILE); // NOPMD
    /** **/
    private transient final PropertyKit REPORTER = new PropertyKit(getClass(), "/console/report/status.properties");// NOPMD
    /** **/
    private transient final StatusService service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public StatusCommand() {
        this.service = singleton(StatusSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String command() {
        return "status";
    }

    // ~ Methods =============================================
    /** **/
    public JsonObject execute(final String... args) {
        final CommandLine cl = this.parse(args);
        JsonObject retJson = null;
        // TODO: 命令status的开发
        if (cl.hasOption('m')) {
            final JsonObject params = this.getJdbcParams("H2");
            retJson = getResult(params);
        } else if (cl.hasOption('d')) {
            final JsonObject params = this.getJdbcParams(Resources.DB_CATEGORY);
            retJson = getResult(params);
        }
        return report(retJson);
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

    private JsonObject report(final JsonObject result) {
        final JsonObject ret = new JsonObject();
        for (final String field : result.fieldNames()) {
            ret.put(REPORTER.getString(field), result.getString(field));
        }
        return ret;
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
