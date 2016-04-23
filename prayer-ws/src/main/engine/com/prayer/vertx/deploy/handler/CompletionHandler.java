package com.prayer.vertx.deploy.handler;

import static com.prayer.util.debug.Log.error;
import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.MsgVertx;

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
    /** **/
    private transient AtomicInteger counter;

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
        if (null != this.counter) {
            /** 1.发布统计 **/
            final int counter = this.counter.getAndAdd(Constants.ONE);
            if (event.succeeded()) {
                if (counter <= 1) {
                    info(LOGGER, MessageFormat.format(MsgVertx.DP_HANDLER, getClass().getSimpleName(), this.name,
                            this.option.getInstances(), this.option.toJson().encode()));
                }
            } else {
                if (counter <= 1) {
                    error(LOGGER, MessageFormat.format(MsgVertx.DP_HANDLER_ERR, getClass().getSimpleName(), this.name,
                            this.option.getInstances()));
                }
            }
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /** **/
    public void setCounter(@NotNull final AtomicInteger counter) {
        this.counter = counter;
    }

    /** 发布的数量满的时候 **/
    public int getCounter() {
        if (null == this.counter) {
            return Constants.RANGE;
        }
        return this.counter.get();
    }
    // ~ hashCode,equals,toString ============================

}
