package com.prayer.vertx.launcher;

import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.MsgVertx;
import com.prayer.facade.engine.cv.RmiKeys;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.vtx.AbstractPromulgator;
import com.prayer.vertx.deploy.handler.CompletionHandler;
import com.prayer.vertx.util.RemoteRefers;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

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
    /** Instance **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient String instanceRef;
    /** Vertx Reference **/
    private transient Vertx vertxRef;
    /** **/
    private transient ConcurrentMap<String, DeploymentOptions> OPTIONS = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public SinglePromulgator(@NotNull @NotEmpty @NotBlank final String instanceRef) {
        this.instanceRef = instanceRef;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean deploy(@NotNull final VertxOptions options) throws AbstractException {
        /** 1.初始化Vertx实例 **/
        this.vertxRef = factory().vertx(options);
        info(LOGGER, MessageFormat.format(MsgVertx.DP_MODE, getClass().getSimpleName(), options.isClustered(),
                options.isHAEnabled(), options.toString()));
        /** 2.获取发布信息 **/
        OPTIONS = intaker().ingest();
        /** 3.直接Deploy **/
        for (final String name : OPTIONS.keySet()) {
            /** 4.读取Options **/
            final DeploymentOptions option = OPTIONS.get(name);
            /** 5.初始化Handler **/
            final CompletionHandler handler = new CompletionHandler(name, option);
            vertxRef.deployVerticle(name, option, handler);
            /** 6.写入信息到RMI **/
            RemoteRefers.registry(this.getAddress(name, option), option.toJson().encode());
        }
        return true;
    }

    /** **/
    @Override
    public boolean undeploy() throws AbstractException {
        /** Undeploy **/
        if (null != this.vertxRef) {
            for (final String name : OPTIONS.keySet()) {
                this.vertxRef.undeploy(name);
            }
        }
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private String getAddress(final String name, final DeploymentOptions options) {
        final boolean isWorker = options.isWorker();
        String address = "";
        if (isWorker) {
            address = MessageFormat.format(RmiKeys.VERTCILE_OPTS, this.instanceRef, "WORKER", name,
                    options.getInstances());
        } else {
            address = MessageFormat.format(RmiKeys.VERTCILE_OPTS, this.instanceRef, "AGENT", name,
                    options.getInstances());
        }
        return address;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
