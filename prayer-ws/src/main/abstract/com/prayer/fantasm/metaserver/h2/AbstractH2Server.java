package com.prayer.fantasm.metaserver.h2;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.opts.Options;
import com.prayer.facade.metaserver.h2.H2Server;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractH2Server implements H2Server {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected void exit() {
        info(getLogger(), WebConsole.T_STOPPED);
        System.exit(0);
    }
    /**
     * 
     * @return
     */
    public final Logger getLogger(){
        return LoggerFactory.getLogger(getClass());
    }
    // ~ Private Methods =====================================
    /**
     * 
     * @param options
     * @return
     */
    protected List<Server> initDatabase(final JsonArray optArrs) {
        /** 1.参数表 **/
        final List<Server> servers = new ArrayList<>();
        int length = optArrs.size();
        /** 2.遍历Server表 **/
        for (int idx = 0; idx < length; idx++) {
            final List<String> params = new ArrayList<>();
            final JsonObject config = optArrs.getJsonObject(idx);
            // 1.1.端口号提取
            params.add("-tcpPort");
            params.add(String.valueOf(config.getInteger("tcp.port")));
            // 1.2.baseDir的计算
            params.add("-baseDir");
            params.add("~/H2/" + config.getString("baseDir"));
            // 1.3.Password
            params.add("-tcpPassword");
            params.add(Constants.TCP_PASSWORD);
            // 1.4.是否允许连接
            if (config.getBoolean("tcp.allow.others")) {
                params.add("-tcpAllowOthers");
            }
            /** Server实例化 **/
            Server server = null;
            try {
                server = Server.createTcpServer(params.toArray(new String[] {}));
            } catch (SQLException ex) {
                jvmError(getLogger(), ex);
            }
            if (null != server) {
                servers.add(server);
            }
        }
        return servers;
    }

    /**
     * 
     * @param options
     */
    protected Server initWebConsole(final Options options) {
        /** 1.参数表 **/
        final JsonObject config = options.readOpts().getJsonObject("extension");
        final List<String> params = new ArrayList<>();
        // 1.1.端口号
        params.add("-webPort");
        params.add(String.valueOf(config.getInteger("web.port")));
        // 1.2.允许连接
        if (config.getBoolean("web.allow.others")) {
            params.add("-webAllowOthers");
        }
        /** 2.Server实例化 **/
        Server server = null;
        try {
            server = Server.createWebServer(params.toArray(new String[] {}));
        } catch (SQLException ex) {
            jvmError(getLogger(), ex);
        }
        return server;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
