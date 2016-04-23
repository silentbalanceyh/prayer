package com.prayer.vertx.launcher;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.booter.util.Ensurer;
import com.prayer.facade.engine.Launcher;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.vtx.Promulgator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.vertx.config.VertxOptsIntaker;

import io.vertx.core.VertxOptions;
import net.sf.oval.guard.Guarded;

/** **/
@Guarded
public class VertxLauncher implements Launcher {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxLauncher.class);
    /** **/
    private static final Intaker<ConcurrentMap<String, VertxOptions>> INTAKER = singleton(VertxOptsIntaker.class);
    // ~ Instance Fields =====================================
    /** Active Vertx Name **/
    private static final String INSTANCE = "PRAYER-ENGINE";

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void start() throws AbstractException {
        /** 1.判断Meta Server是否在运行 **/
        if (Ensurer.running(getClass(), LOGGER)) {
            final VertxOptions options = this.initOpts();
            if (options.isClustered()) {
                /** 2.集群发布 **/
            } else {
                /** 2.单节点发布 **/
                final Promulgator promulgator = singleton(SinglePromulgator.class);
                promulgator.promulgate(options);
            }
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean running() {
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 初始化Vertx配置 **/
    private VertxOptions initOpts() throws AbstractException {
        final ConcurrentMap<String, VertxOptions> options = INTAKER.ingest();
        return options.get(INSTANCE);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
