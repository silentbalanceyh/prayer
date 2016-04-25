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
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.vertx.opts.uri.UriOptsIntaker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
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
    // ~ Instance Fields =====================================
    /** **/
    private static final Intaker<ConcurrentMap<String, JsonObject>> INTAKER = singleton(UriOptsIntaker.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void start() {
        /** 1.提取EventBus **/
        final EventBus evtBus = vertx.eventBus();
        /** 2.读取配置操作 **/
        final ConcurrentMap<String, JsonObject> retData = new ConcurrentHashMap<>();
        try {
            retData.putAll(INTAKER.ingest());
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
        /** 3.并行Publish **/
        retData.forEach((key, value) -> {
            /** 4.发布URI数据到系统中 **/
            evtBus.publish(key, value);
            /** 5.日志输出 **/
            info(LOGGER, MessageFormat.format(MsgVertx.ES_URI, getClass().getSimpleName(), key));
        });
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
