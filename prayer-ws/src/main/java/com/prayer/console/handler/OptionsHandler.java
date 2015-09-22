package com.prayer.console.handler;

import com.prayer.constant.Resources;
import com.prayer.util.PropertyKit;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class OptionsHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final PropertyKit SEV_LOADER;
    /** **/
    private transient final PropertyKit VX_LOADER;
    /** **/
    private transient final String VX_PREFIX = "vertx.";

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static OptionsHandler create() {
        return new OptionsHandler();
    }

    // ~ Constructors ========================================
    /** **/
    private OptionsHandler() {
        this.SEV_LOADER = new PropertyKit(OptionsHandler.class, Resources.SEV_CFG_FILE);
        this.VX_LOADER = new PropertyKit(OptionsHandler.class, Resources.VX_CFG_FILE);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        final HttpServerRequest request = context.request();
        final String path = request.path();
        if (StringUtil.endsWithIgnoreCase(path, "/options/h2")) {
            this.injectH2(context);
        } else if (StringUtil.endsWithIgnoreCase(path, "/options/server")) {
            this.injectServer(context);
        } else if (StringUtil.endsWithIgnoreCase(path, "/options/vertx")) {
            this.injectVertx(context);
        } else if (StringUtil.endsWithIgnoreCase(path, "/options/security")){
            this.injectSecurity(context);
        }
        // 通用参数，运行的IP地址
        context.put("running.ip", request.localAddress().host());
        context.next();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    private void injectSecurity(final RoutingContext context){
        final String current = this.SEV_LOADER.getString("server.security.mode");
        context.put("security.mode", current);
    }

    private void injectVertx(final RoutingContext context) {
        final String current = this.VX_LOADER.getString("vertx.active");
        // Pool Information
        context.put("vertx.name", current);
        context.put("vertx.pool.size.el", this.VX_LOADER.getInt(VX_PREFIX + current + ".pool.size.event.loop"));

        context.put("vertx.pool.size.worker", this.VX_LOADER.getInt(VX_PREFIX + current + ".pool.size.worker"));
        context.put("vertx.pool.size.ib", this.VX_LOADER.getInt(VX_PREFIX + current + ".pool.size.internal.blocking"));
        // Cluster Information
        context.put("vertx.cluster.enabled", this.VX_LOADER.getBoolean(VX_PREFIX + current + ".cluster.enabled"));
        context.put("vertx.cluster.host", this.VX_LOADER.getString(VX_PREFIX + current + ".cluster.host"));
        context.put("vertx.cluster.port", this.VX_LOADER.getInt(VX_PREFIX + current + ".cluster.port"));
        context.put("vertx.cluster.ping.i", this.VX_LOADER.getLong(VX_PREFIX + current + ".cluster.ping.interval"));
        context.put("vertx.cluster.ping.ir",
                this.VX_LOADER.getLong(VX_PREFIX + current + ".cluster.ping.interval.reply"));
        // HA Configuration Information
        context.put("vertx.ha.enabled", this.VX_LOADER.getBoolean(VX_PREFIX + current + ".ha.enabled"));
        context.put("vertx.ha.group", this.VX_LOADER.getString(VX_PREFIX + current + ".ha.group"));
        context.put("vertx.quorum.size", this.VX_LOADER.getInt(VX_PREFIX + current + ".quorum.size"));
        // Other Configuration Information
        context.put("vertx.blocked.thread.ci",
                this.VX_LOADER.getInt(VX_PREFIX + current + ".blocked.thread.check.internal"));
        context.put("vertx.et.max.event.loop",
                this.VX_LOADER.getLong(VX_PREFIX + current + ".execute.time.max.event.loop") / 1000000L);
        context.put("vertx.et.max.worker",
                this.VX_LOADER.getLong(VX_PREFIX + current + ".execute.time.max.worker") / 1000000L);
        context.put("vertx.t.exception.warning",
                this.VX_LOADER.getLong(VX_PREFIX + current + ".warning.exception.time") / 1000000L);
    }

    private void injectServer(final RoutingContext context) {
        context.put("server.api.port", this.SEV_LOADER.getInt("server.api.port"));
        context.put("server.web.port", this.SEV_LOADER.getInt("server.web.port"));
        context.put("server.host", this.SEV_LOADER.getString("server.host"));
        context.put("server.compression", this.SEV_LOADER.getBoolean("server.compression.support"));
        context.put("server.accept.backlog", this.SEV_LOADER.getInt("server.accept.backlog"));
        context.put("server.client.auth.required", this.SEV_LOADER.getBoolean("server.client.auth.required"));
    }

    private void injectH2(final RoutingContext context) {
        context.put("h2.tcp", this.SEV_LOADER.getInt("h2.database.tcp.port"));
        context.put("h2.tcp.allow", this.SEV_LOADER.getBoolean("h2.database.tcp.allow.others"));
        context.put("h2.web", this.SEV_LOADER.getInt("h2.database.web.port"));
        context.put("h2.web.allow", this.SEV_LOADER.getBoolean("h2.database.web.allow.others"));
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
