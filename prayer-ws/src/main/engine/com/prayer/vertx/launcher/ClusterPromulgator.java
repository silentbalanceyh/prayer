package com.prayer.vertx.launcher;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.prayer.facade.engine.cv.MsgVertx;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.vtx.Promulgator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.vertx.deploy.handler.ClusterHandler;
import com.prayer.vertx.tp.opts.HazelcastIntaker;

import io.vertx.core.VertxOptions;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
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
final class ClusterPromulgator implements Promulgator {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SinglePromulgator.class);
    /** **/
    private static final Intaker<Config> INTAKER = singleton(HazelcastIntaker.class);
    /** **/
    private static final VertxFactory FACTORY = new VertxFactoryImpl();
    // ~ Instance Fields =====================================
    /** Instance **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient String instanceRef;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ClusterPromulgator(@NotNull @NotEmpty @NotBlank final String instanceRef) {
        this.instanceRef = instanceRef;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean deploy(@NotNull final VertxOptions options) throws AbstractException {
        /** 1.读取Factory引用 **/
        info(LOGGER, MessageFormat.format(MsgVertx.DP_MODE, getClass().getSimpleName(), options.isClustered(),
                options.isHAEnabled(), options.toString()));
        /** 2.读取Cluster配置信息 **/
        final ClusterManager mgr = new HazelcastClusterManager(INTAKER.ingest());
        options.setClusterManager(mgr);
        /** 3.Cluster发布 **/
        FACTORY.clusteredVertx(options, new ClusterHandler(this.instanceRef));
        return false;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
