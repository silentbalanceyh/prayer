package com.prayer.handler.deploy;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class VerticleAsyncHandler implements Handler<AsyncResult<String>> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Handler的核心方法
     */
    @Override
    public void handle(@NotNull final AsyncResult<String> event) {
        // TODO: Async Deploy Handler
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
