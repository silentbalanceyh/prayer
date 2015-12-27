package com.prayer.handler.deploy;

import static com.prayer.util.Log.info;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import net.sf.oval.constraint.AssertFieldConstraints;
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
public class VerticleAsyncHandler implements Handler<AsyncResult<String>> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticleAsyncHandler.class);
    /** **/
    private static final String DP_VERTICLE = "(Async) Verticle : {0} has been deployed {1} instances successfully";

    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String name;    // NOPMD
    /** **/
    @NotNull
    private transient final DeploymentOptions option;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public VerticleAsyncHandler(@AssertFieldConstraints("name") final String name,
            @NotNull final DeploymentOptions option) {
        this.name = name;
        this.option = option;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Handler的核心方法
     */
    @Override
    public void handle(@NotNull final AsyncResult<String> event) {
        if (event.succeeded()) {
            info(LOGGER, MessageFormat.format(DP_VERTICLE, this.name, this.option.getInstances()));
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
