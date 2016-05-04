package com.prayer.vertx.handler.deploy;

import static com.prayer.util.debug.Log.error;
import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
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
    /** 日志数据，并行日志 **/
    @NotNull
    private static Set<String> logs;
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
    @NotNull
    private transient final AtomicInteger counter;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param name
     * @param options
     */
    @PostValidateThis
    public CompletionHandler(@NotNull final String name, @NotNull final DeploymentOptions option,
            @NotNull final AtomicInteger counter) {
        this.name = name;
        this.option = option;
        this.counter = counter;
        if (null == logs) {
            logs = new HashSet<>();
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final AsyncResult<String> event) {
        /** 1.发布统计 **/
        if (event.succeeded()) {
            logs.add(MessageFormat.format(MsgVertx.DP_HANDLER, getClass().getSimpleName(), this.name,
                    this.option.getInstances(), this.option.toJson().encode()));
            if (Constants.ZERO == counter.decrementAndGet()) {
                final Iterator<String> it = logs.iterator();
                while (it.hasNext()) {
                    info(LOGGER, it.next());
                }
            }
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
