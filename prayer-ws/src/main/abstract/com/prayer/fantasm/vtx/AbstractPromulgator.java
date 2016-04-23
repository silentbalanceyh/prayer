package com.prayer.fantasm.vtx;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.vtx.Promulgator;
import com.prayer.vertx.config.DeployOptsIntaker;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractPromulgator implements Promulgator {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 一个EngineCluster中提供的Factory的实例 **/
    private transient final VertxFactory FACTORY = new VertxFactoryImpl();
    // ~ Static Block ========================================
    /** **/
    private transient final Intaker<ConcurrentMap<String,DeploymentOptions>> INTAKER = singleton(DeployOptsIntaker.class);
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 子类调用
     * @return
     */
    protected VertxFactory factory(){
        return FACTORY;
    }
    /**
     * 子类调用
     * @return
     */
    protected Intaker<ConcurrentMap<String,DeploymentOptions>> intaker(){
        return INTAKER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
