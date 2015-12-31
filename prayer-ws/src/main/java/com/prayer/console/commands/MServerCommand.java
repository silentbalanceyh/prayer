package com.prayer.console.commands;

import static com.prayer.util.Instance.singleton;

import java.net.URL;

import org.apache.commons.cli.CommandLine;

import com.prayer.bus.impl.oob.DeploySevImpl;
import com.prayer.constant.Resources;
import com.prayer.facade.bus.DeployService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
public class MServerCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    /** **/
    private static final String DEPLOYED = "DEPLOYED";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final DeployService service;

    /** **/
    @NotNull
    private transient final JdbcHelper helper;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    @PostValidateThis
    public MServerCommand() {
        super();
        this.service = singleton(DeploySevImpl.class);
        this.helper = singleton(JdbcHelper.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String command() {
        return "mserver";
    }

    // ~ Methods =============================================
    /**
     * 
     * @param args
     * @return
     */
    @Override
    public JsonObject execute(final String... args) {
        final CommandLine cmdLine = this.parse(args);
        // TODO: 命令mserver的开发
        JsonObject ret = null;
        if (null != cmdLine) {
            if (cmdLine.hasOption('s')) {
            } else if (cmdLine.hasOption('h')) {
            } else if (cmdLine.hasOption('d')) {
                ret = this.deployData(cmdLine);
            } else if (cmdLine.hasOption('i')) {
                ret = this.initMetadata(cmdLine);
            }
        }
        return ret;
    }
    // ~ Private Methods =====================================

    private JsonObject initMetadata(final CommandLine cmdLine) {
        final String sqlFile = Resources.META_INIT_SQL;
        final ServiceResult<Boolean> ret = this.service.initMetadata(sqlFile);
        final JsonObject retJson = new JsonObject();
        if (ret.getResult()) {
            retJson.put("info", "[INFO] H2 Database tables have been initilized successfully!");
        } else {
            retJson.put(ERROR, ret.getErrorMessage());
        }
        return retJson;
    }

    private JsonObject deployData(final CommandLine cmdLine) {
        final String folder = cmdLine.getOptionValue('d');
        final URL url = IOKit.getURL(folder);
        JsonObject ret = new JsonObject();
        if (null == url) {
            System.out.println("[ERROR] The folder : " + folder + " does not exist in classpath."); // NOPMD
        } else {
            final JsonObject checkConn = this.helper.getMetadata(Resources.META_CATEGORY);
            if (checkConn.containsKey(ERROR)) {
                ret.put(ERROR, checkConn.getString(ERROR));
            } else {
                ret = this.deployData(folder);
                if (ret.getBoolean(DEPLOYED)) {
                    ret.put("info", "[INFO] H2 Database metadata has been deployed successfully.");
                }
                ret.remove(DEPLOYED);
            }
        }
        return ret;
    }

    private JsonObject deployData(final String folder) {
        final ServiceResult<Boolean> ret = this.service.deployMetadata(folder);
        final JsonObject retJson = new JsonObject();
        if (ret.getResult()) {
            retJson.put(DEPLOYED, Boolean.TRUE);
        } else {
            retJson.put(DEPLOYED, Boolean.FALSE);
            retJson.put(ERROR, ret.getErrorMessage());
        }
        return retJson;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
