package com.prayer.handler.deploy;

import static com.prayer.util.Log.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticleAsyncHandler.class);

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
        if (event.succeeded()) {
            info(LOGGER, "Deploy verticle successfully : " + Thread.currentThread().getName() + ", Result = "
                    + event.result());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
