package com.prayer.vertx.handler.deploy;

import static com.prayer.util.debug.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.fantasm.exception.AbstractException;
import com.prayer.launcher.vertx.HubExecutor;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Cluster发布专用的Handler
 * 
 * @author Lang
 *
 */
public class ClusterHandler implements Handler<AsyncResult<Vertx>> {

    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterHandler.class);
    // ~ Instance Fields =====================================
    /** Instance **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String instanceRef;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param instanceRef
     */
    public ClusterHandler(final String instanceRef) {
        this.instanceRef = instanceRef;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final AsyncResult<Vertx> event) {
        if (event.succeeded()) {
            final Vertx vertx = event.result();
            try {
                HubExecutor.injectDeploy(vertx, this.instanceRef);
            } catch (AbstractException ex) {
                peError(LOGGER, ex);
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
