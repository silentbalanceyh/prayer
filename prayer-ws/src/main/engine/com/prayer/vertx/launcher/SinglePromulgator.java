package com.prayer.vertx.launcher;

import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.msg.MsgVertx;
import com.prayer.facade.vtx.Promulgator;
import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;
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
final class SinglePromulgator implements Promulgator {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SinglePromulgator.class);
    /** **/
    private static final VertxFactory FACTORY = new VertxFactoryImpl();
    // ~ Instance Fields =====================================
    /** Instance **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient String instanceRef;
    /** Vertx Reference **/
    private transient Vertx vertxRef;
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
        this.vertxRef = FACTORY.vertx(options);
        info(LOGGER, MessageFormat.format(MsgVertx.DP_MODE, getClass().getSimpleName(), options.isClustered(),
                options.isHAEnabled(), options.toString()));
        /** 2.发布Vertx中的Verticles **/
        HubExecutor.injectDeploy(this.vertxRef, this.instanceRef);
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
