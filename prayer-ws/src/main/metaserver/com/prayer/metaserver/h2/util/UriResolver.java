package com.prayer.metaserver.h2.util;

import java.text.MessageFormat;

import org.h2.tools.Server;

import com.prayer.facade.engine.Options;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class UriResolver {
    // ~ Static Fields =======================================
    /** Single **/
    private static final String URI_SINGLE = "jdbc:h2:tcp://{0}:{1}/META/{2}";
    /** Cluster **/
    private static final String URI_CLUSTER = "jdbc:h2:tcp://{0}/META/{1}";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static String resolveJdbc(@NotNull final Options options) {
        /** 1.构建URI **/
        final StringBuilder uri = new StringBuilder();
        /** 2.判断Cluster **/
        final JsonObject config = options.readOpts();
        if (isClustered(config)) {

        } else {
            final String host = config.getJsonObject("nodes").getString("host");
            final String port = String.valueOf(config.getJsonObject("nodes").getInteger("tcp.port"));
            final String database = config.getJsonObject("server").getString("database");
            uri.append(MessageFormat.format(URI_SINGLE, host, port, database));
        }
        /** 3.是否加密 **/
        if (config.getJsonObject("extension").getBoolean("encryption")) {
            uri.append(";PASSWORD_HASH=TRUE");
        }
        /** 4.返回URI **/
        return uri.toString();
    }

    private static boolean isClustered(final JsonObject config) {
        return config.containsKey("cluster");
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private UriResolver() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
