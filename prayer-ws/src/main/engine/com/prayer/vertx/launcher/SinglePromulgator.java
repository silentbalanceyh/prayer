package com.prayer.vertx.launcher;

import static com.prayer.util.debug.Log.error;
import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.MsgVertx;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.vtx.AbstractPromulgator;
import com.prayer.vertx.deploy.handler.CompletionHandler;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class SinglePromulgator extends AbstractPromulgator {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SinglePromulgator.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean promulgate(@NotNull final VertxOptions options) throws AbstractException {
        /** 1.初始化Vertx实例 **/
        final Vertx vertx = factory().vertx(options);
        info(LOGGER, MessageFormat.format(MsgVertx.DP_MODE, getClass().getSimpleName(), options.isClustered(),
                options.isHAEnabled(), options.toString()));
        /** 2.获取发布信息 **/
        final ConcurrentMap<String, DeploymentOptions> deployOpts = intaker().ingest();
        /** 3.直接Deploy **/
        for (final String name : deployOpts.keySet()) {
            /** 4.读取Options **/
            final DeploymentOptions option = deployOpts.get(name);
            /** 5.初始化Handler **/
            final CompletionHandler handler = new CompletionHandler(name, option);
            handler.setCounter(new AtomicInteger(Constants.ONE));
            /** 6.检查Counter **/
            if (Constants.RANGE == handler.getCounter()) {
                error(LOGGER, MessageFormat.format(MsgVertx.DP_COUNTER, getClass().getSimpleName()));
            }
            vertx.deployVerticle(name, option, handler);
        }
        return true;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
