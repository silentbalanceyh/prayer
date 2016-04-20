package com.prayer.fantasm.metaserver.h2;

import static com.prayer.util.debug.Log.jvmError;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;

import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.h2.H2Server;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;

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
    private transient final Server webServer;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param options
     */
    public AbstractH2Server(final Options options) {
        webServer = this.initWebConsole(options);
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    /**
     * 获取Server引用
     * 
     * @return
     */
    @Override
    public Server getWebRef() {
        return this.webServer;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 
     * @param options
     */
    private Server initWebConsole(final Options options) {
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
