package com.prayer.handler.deploy;

import static com.prayer.util.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractException;
import com.prayer.engine.VerticleDeployer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 发布过程的Handler
 * 
 * @author Lang
 *
 */
@Guarded
public class VertxClusterHandler implements Handler<AsyncResult<Vertx>> {

    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxClusterHandler.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 核心方法
     */
    @Override
    public void handle(@NotNull final AsyncResult<Vertx> event) {
        if (event.succeeded()) {
            final Vertx vertx = event.result();
            final VerticleDeployer deployer = new VerticleDeployer(vertx);
            try {
                deployer.deployVerticles();
            } catch (AbstractException ex) {
                peError(LOGGER,ex);
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
