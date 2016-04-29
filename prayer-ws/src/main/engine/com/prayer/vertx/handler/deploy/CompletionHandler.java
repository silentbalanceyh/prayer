package com.prayer.vertx.handler.deploy;

import static com.prayer.util.debug.Log.error;
import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.msg.MsgVertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 发布用的Handler
 * 
 * @author Lang
 *
 */
@Guarded
public class CompletionHandler implements Handler<AsyncResult<String>> {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletionHandler.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String name;
    /** **/
    @NotNull
    private transient final DeploymentOptions option;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param name
     * @param options
     */
    @PostValidateThis
    public CompletionHandler(@NotNull final String name, @NotNull final DeploymentOptions option) {
        this.name = name;
        this.option = option;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void handle(@NotNull final AsyncResult<String> event) {
        /** 1.发布统计 **/
        if (event.succeeded()) {
            info(LOGGER, MessageFormat.format(MsgVertx.DP_HANDLER, getClass().getSimpleName(), this.name,
                    this.option.getInstances(), this.option.toJson().encode()));
        } else {
            error(LOGGER, MessageFormat.format(MsgVertx.DP_HANDLER_ERR, getClass().getSimpleName(), this.name,
                    this.option.getInstances()));
            if (null != event.cause()) {
                jvmError(LOGGER, event.cause());
                // TODO: Debug
                event.cause().printStackTrace();
            }
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
