package com.prayer.vertx.util;

import static com.prayer.util.debug.Log.debug;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.engine.cv.msg.MsgVertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class SharedDator {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SharedDator.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * Local的SharedDator调用
     * 
     * @param vertx
     * @param keys
     */
    public static void put(@NotNull final Vertx vertx, @NotNull final JsonObject keys) {
        final SharedData data = vertx.sharedData();
        final LocalMap<String, JsonObject> map = data.getLocalMap(keys.getString(WebKeys.Shared.Params.MAP));
        final JsonObject ret = keys.getJsonObject(WebKeys.Shared.Params.VALUE);
        log(MsgVertx.MAP_PUT, keys, ret, data.hashCode());
        map.put(keys.getString(WebKeys.Shared.Params.KEY), ret);
    }

    /**
     * Cluster模式的SharedDator调用
     * 
     * @param vertx
     * @param keys
     * @param handler
     */
    public static void put(@NotNull final Vertx vertx, @NotNull final JsonObject keys,
            final Handler<AsyncResult<Void>> handler) {
        final SharedData data = vertx.sharedData();
        data.<String, JsonObject> getClusterWideMap(keys.getString(WebKeys.Shared.Params.MAP), res -> {
            if (res.succeeded()) {
                final AsyncMap<String, JsonObject> map = res.result();
                final JsonObject ret = keys.getJsonObject(WebKeys.Shared.Params.VALUE);
                log(MsgVertx.MAP_PUT, keys, ret, data.hashCode());
                map.put(keys.getString(WebKeys.Shared.Params.KEY), ret, handler);
            }
        });
    }

    /**
     * Local的SharedDator调用
     * 
     * @param vertx
     * @param keys
     * @return
     */
    public static JsonObject get(@NotNull final Vertx vertx, @NotNull final JsonObject keys) {
        final SharedData data = vertx.sharedData();
        final LocalMap<String, JsonObject> map = data.getLocalMap(keys.getString(WebKeys.Shared.Params.MAP));
        final JsonObject ret = map.get(keys.getString(WebKeys.Shared.Params.KEY));
        log(MsgVertx.MAP_GET, keys, ret, data.hashCode());
        return ret;
    }

    /**
     * Cluster模式的SharedDator调用
     * 
     * @param vertx
     * @param keys
     */
    public static void get(@NotNull final Vertx vertx, @NotNull final JsonObject keys,
            final Handler<AsyncResult<JsonObject>> handler) {
        final SharedData data = vertx.sharedData();
        data.<String, JsonObject> getClusterWideMap(keys.getString(WebKeys.Shared.Params.MAP), res -> {
            if (res.succeeded()) {
                final AsyncMap<String, JsonObject> map = res.result();
                map.get(keys.getString(WebKeys.Shared.Params.KEY), getRet -> {
                    if (getRet.succeeded()) {
                        final JsonObject ret = getRet.result();
                        log(MsgVertx.MAP_GET, keys, ret, data.hashCode());
                        handler.handle(Future.<JsonObject> succeededFuture(ret));
                    }
                });
            }
        });
    }

    private static void log(final String pattern, final JsonObject keys, final JsonObject data, final int hash) {
        final String message = MessageFormat.format(pattern, SharedDator.class.getSimpleName(),
                keys.getString(WebKeys.Shared.Params.MAP), String.valueOf(hash),
                keys.getString(WebKeys.Shared.Params.KEY), null == data ? "null" : data.encode());
        debug(LOGGER, message);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 共享数据区域 **/
    private SharedDator() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
