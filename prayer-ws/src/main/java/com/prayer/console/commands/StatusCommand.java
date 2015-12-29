package com.prayer.console.commands;

import static com.prayer.util.Instance.singleton;

import org.apache.commons.cli.CommandLine;

import com.prayer.util.PropertyKit;
import com.prayer.util.cv.Resources;

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
    private transient final PropertyKit REPORTER = new PropertyKit(getClass(), "/console/report/status.properties");// NOPMD
    /** **/
    private transient final JdbcHelper helper;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public StatusCommand() {
        super();
        this.helper = singleton(JdbcHelper.class);
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
        final CommandLine cmdLine = this.parse(args);
        JsonObject retJson = null;
        // TODO: 命令status的开发
        if (null != cmdLine) {
            if (cmdLine.hasOption('m')) {
                retJson = helper.getMetadata(Resources.META_CATEGORY);
            } else if (cmdLine.hasOption('d')) {
                retJson = helper.getMetadata(Resources.DB_CATEGORY);
            }
        }
        return report(retJson);
    }
    // ~ Private Methods =====================================

    private JsonObject report(final JsonObject result) {
        final JsonObject ret = new JsonObject();
        if (null != result) {
            for (final String field : result.fieldNames()) {
                ret.put(REPORTER.getString(field), result.getString(field));
            }
        }
        return null == result ? null : ret;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
