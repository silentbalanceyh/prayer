package com.prayer.fantasm.vtx.verticles;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PEAddress;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import net.sf.oval.constraint.NotNull;

/**
 * Vertx中的抽象Worker
 * @author Lang
 *
 */
public abstract class AbstractWorker extends AbstractVerticle {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ConfigInstantor instantor = singleton(ConfigBllor.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 主要方法，重写start **/
    @Override
    public void start() {
        /** 1.读取当前Worker类 **/
        final Class<?> workClass = this.getClass();
        /** 2.读取元数据 **/
        try {
            final PEAddress address = this.instantor.address(workClass);
            if (null != address) {
                /** 3.消费 **/
                final EventBus bus = vertx.eventBus();

                bus.consumer(address.getConsumerAddr(), singleton(address.getConsumerHandler()));
            }
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
        }
    }
    // ~ Methods =============================================
    /**
     * 
     * @return
     */
    public final Logger getLogger(){
        return LoggerFactory.getLogger(getClass());
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
