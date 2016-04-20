package com.prayer.fantasm.metaserver.h2;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.h2.H2Quoter;
import com.prayer.facade.engine.metaserver.h2.H2Server;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.metaserver.h2.rmi.H2OptionsQuoter;
import com.prayer.resource.InceptBus;
import com.prayer.rmi.RemoteInvoker;

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
    /** **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.RMI.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected void exit() {
        info(getLogger(), WebConsole.T_STOPPED);
        System.exit(0);
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

    // ~ RMI =================================================
    /**
     * 
     * @param name
     */
    protected void registry(final String name, final JsonObject options) {
        final String pattern = INCEPTOR.getString(Point.RMI.META_SERVER);
        try {
            final H2Quoter quoter = new H2OptionsQuoter(options.encode());
            RemoteInvoker.registry(quoter, pattern, name);
        } catch (RemoteException ex) {
            jvmError(getLogger(), ex);
        }
    }

    /**
     * 
     * @param name
     * @return
     */
    protected JsonObject lookup(final String name) {
        JsonObject data = new JsonObject();
        try {
            final String pattern = INCEPTOR.getString(Point.RMI.META_SERVER);
            final H2Quoter quoter = (H2Quoter) RemoteInvoker.lookup(pattern, name);
            data = new JsonObject(quoter.service(null));
        } catch (RemoteException ex) {
            jvmError(getLogger(), ex);
            ex.printStackTrace();
        }
        return data;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
