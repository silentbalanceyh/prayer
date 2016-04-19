package com.prayer.metaserver.h2.util;

import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class JdbcResolver {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static JsonObject resolve(@NotNull final Inceptor inceptor) {
        /** 1.从meta.server.external中读取Inceptor **/
        final Inceptor jdbcInceptor = getJdbcInceptor(inceptor.getString("meta.server.external"));
        /** 2.提取Key **/
        final String usernameKey = extractKey(inceptor.getString("meta.server.username"));
        final String passwordKey = extractKey(inceptor.getString("meta.server.password"));
        final String databaseKey = extractKey(inceptor.getString("meta.server.database"));
        /** 3.构建JsonObject **/
        final JsonObject data = new JsonObject();
        if (null != usernameKey) {
            data.put("username", jdbcInceptor.getString(usernameKey));
        }
        if (null != passwordKey) {
            data.put("password", jdbcInceptor.getString(passwordKey));
        }
        if (null != databaseKey) {
            data.put("database", jdbcInceptor.getString(databaseKey));
        }
        return data;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static String extractKey(final String key) {
        String value = null;
        if (null != key && 0 <= key.indexOf("${") && 0 <= key.indexOf("}")) {
            value = key.substring(key.indexOf("${") + 2, key.indexOf("}"));
        }
        System.out.println(value);
        return value;
    }

    /**
     * JDBC Inceptor
     * 
     * @param file
     * @return
     */
    private static Inceptor getJdbcInceptor(final String file) {
        return InceptBus.build(Point.MetaServer.class, file);
    }

    private JdbcResolver() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
