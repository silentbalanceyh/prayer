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
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        final HttpServerRequest request = context.request();
        final String path = request.path();
        if (StringUtil.endsWithIgnoreCase(path, "h2")) {
            this.injectH2(context);
        } else if (StringUtil.endsWithIgnoreCase(path, "server")) {
            this.injectServer(context);
        }
        context.next();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void injectServer(final RoutingContext context) {
        final HttpServerRequest request = context.request();
        context.put("server.ip", request.localAddress().host());
        context.put("server.api.port", this.SEV_LOADER.getInt("server.api.port"));
        context.put("server.web.port", this.SEV_LOADER.getInt("server.web.port"));
        context.put("server.host", this.SEV_LOADER.getString("server.host"));
        context.put("server.compression", this.SEV_LOADER.getBoolean("server.compression.support"));
        context.put("server.accept.backlog", this.SEV_LOADER.getInt("server.accept.backlog"));
        context.put("server.client.auth.required", this.SEV_LOADER.getBoolean("server.client.auth.required"));
    }

    private void injectH2(final RoutingContext context) {
        final HttpServerRequest request = context.request();
        context.put("h2.host", request.localAddress().host());
        context.put("h2.tcp", this.SEV_LOADER.getInt("h2.database.tcp.port"));
        context.put("h2.tcp.allow", this.SEV_LOADER.getBoolean("h2.database.tcp.allow.others"));
        context.put("h2.web", this.SEV_LOADER.getInt("h2.database.web.port"));
        context.put("h2.web.allow", this.SEV_LOADER.getBoolean("h2.database.web.allow.others"));
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
