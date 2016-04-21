package com.prayer.metaserver.h2.util;

import java.text.MessageFormat;

import com.prayer.facade.engine.Options;
import com.prayer.facade.metaserver.h2.H2Messages;
import com.prayer.util.Converter;

import io.vertx.core.json.JsonArray;
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
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static String resolveJdbc(@NotNull final Options options) {
        /** 1.构建URI **/
        final StringBuilder uri = new StringBuilder();
        /** 2.判断Cluster **/
        final JsonObject config = options.readOpts();
        final boolean encryption = config.getJsonObject("extension").getBoolean("encryption");
        final String database = config.getJsonObject("server").getString("database");
        if (isClustered(config)) {
            final JsonArray sources = config.getJsonObject("nodes").getJsonArray("source");
            final JsonArray targets = config.getJsonObject("nodes").getJsonArray("target");
            final String host = config.getJsonObject("cluster").getString("host");
            final String sevList = ParamsResolver.resolve(Converter.merge(sources, targets), host);
            uri.append(MessageFormat.format(H2Messages.URI_CLUSTER, sevList, database));
        } else {

            final String host = config.getJsonObject("nodes").getString("host");
            final String port = String.valueOf(config.getJsonObject("nodes").getInteger("tcp.port"));
            uri.append(MessageFormat.format(H2Messages.URI_SINGLE, host, port, database));
        }
        /** 3.是否加密 **/
        if (encryption) {
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
