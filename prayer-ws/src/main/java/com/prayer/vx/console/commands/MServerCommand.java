package com.prayer.vx.console.commands;

import static com.prayer.util.Instance.singleton;

import java.net.URL;

import org.apache.commons.cli.CommandLine;

import com.prayer.bus.impl.oob.DeploySevImpl;
import com.prayer.facade.bus.DeployService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.IOKit;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class MServerCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    /** **/
    private transient final String DEPLOYED = "DEPLOYED";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final DeployService service;

    /** **/
    private transient final JdbcHelper helper;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public MServerCommand() {
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
    public JsonObject execute(final String... args) {
        final CommandLine cl = this.parse(args);
        // TODO: 命令mserver的开发
        JsonObject ret = null;
        if (null != cl) {
            if (cl.hasOption('s')) {
            } else if (cl.hasOption('h')) {
            } else if (cl.hasOption('d')) {
                ret = this.deployData(cl);
            }
        }
        return ret;
    }
    // ~ Private Methods =====================================

    private JsonObject deployData(final CommandLine cl) {
        final String folder = cl.getOptionValue('d');
        final URL url = IOKit.getURL(folder);
        JsonObject ret = new JsonObject();
        if (null == url) {
            System.out.println("[ERROR] The folder does not exist in classpath.");
        } else {
            final JsonObject checkConn = this.helper.getMetadata("H2");
            if (checkConn.containsKey("error")) {
                ret.put("error", checkConn.getString("error"));
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
        final ServiceResult<Boolean> ret = this.service.deployPrayerData(folder);
        final JsonObject retJson = new JsonObject();
        if (ret.getResult()) {
            retJson.put(DEPLOYED, Boolean.TRUE);
        } else {
            retJson.put(DEPLOYED, Boolean.FALSE);
            retJson.put("error", ret.getErrorMessage());
        }
        return retJson;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
