package com.prayer.launcher.vertx;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.engine.cv.RmiKeys;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.vertx.RemoteRefers;
import com.prayer.vertx.config.DeployOptsIntaker;
import com.prayer.vertx.handler.deploy.CompletionHandler;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class HubExecutor {
    // ~ Static Fields =======================================
    /** **/
    private static final Intaker<ConcurrentMap<String, DeploymentOptions>> INTAKER = singleton(DeployOptsIntaker.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 部署专用方法 **/
    public static void injectDeploy(@NotNull final Vertx vertxRef, @NotNull @NotBlank @NotEmpty final String instance)
            throws AbstractException {
        /** 2.获取发布信息 **/
        final ConcurrentMap<String, DeploymentOptions> OPTIONS = INTAKER.ingest();
        /** 3.直接Deploy **/
        for (final String name : OPTIONS.keySet()) {
            /** 4.读取Options **/
            final DeploymentOptions option = OPTIONS.get(name);
            /** 5.初始化Handler **/
            final CompletionHandler handler = new CompletionHandler(name, option);
            vertxRef.deployVerticle(name, option, handler);
            /** 6.写入信息到RMI **/
            RemoteRefers.registry(getAddress(instance, name, option), option.toJson().encode());
        }
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static String getAddress(final String instance, final String name, final DeploymentOptions options) {
        final boolean isWorker = options.isWorker();
        String address = "";
        if (isWorker) {
            address = MessageFormat.format(RmiKeys.VERTCILE_OPTS, instance, "WORKER", name);
        } else {
            address = MessageFormat.format(RmiKeys.VERTCILE_OPTS, instance, "AGENT", name);
        }
        return address;
    }

    private HubExecutor() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
