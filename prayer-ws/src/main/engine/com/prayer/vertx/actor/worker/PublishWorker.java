package com.prayer.vertx.actor.worker;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.MsgVertx;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.vertx.opts.uri.UriOptsIntaker;
import com.prayer.vertx.util.SharedDator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

/**
 * 发布Message用的地址
 * 
 * @author Lang
 *
 */
public class PublishWorker extends AbstractVerticle {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PublishWorker.class);
    /** **/
    private static final Intaker<ConcurrentMap<String, JsonObject>> INTAKER = singleton(UriOptsIntaker.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient ConcurrentMap<String, JsonObject> uriData = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PublishWorker() {
        try {
            uriData.putAll(INTAKER.ingest());
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 同步启动 **/
    @Override
    public void start() {
        uriData.forEach((key, value) -> {
            if (vertx.isClustered()) {
                /** Cluster模式 **/
                SharedDator.put(vertx, this.buildParams(key, value), handler -> {
                    if (handler.succeeded()) {
                        info(LOGGER, MessageFormat.format(MsgVertx.ES_URI, getClass().getSimpleName(), key));
                    }
                });
            } else {
                /** Local模式 **/
                SharedDator.put(vertx, this.buildParams(key, value));
                info(LOGGER, MessageFormat.format(MsgVertx.ES_URI, getClass().getSimpleName(), key));
            }
        });
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JsonObject buildParams(final String key, final JsonObject value) {
        final JsonObject params = new JsonObject();
        params.put(WebKeys.Shared.Params.MAP, WebKeys.Shared.URI);
        params.put(WebKeys.Shared.Params.KEY, key);
        params.put(WebKeys.Shared.Params.VALUE, value);
        return params;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
