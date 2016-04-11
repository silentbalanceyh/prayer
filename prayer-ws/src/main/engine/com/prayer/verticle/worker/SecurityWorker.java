package com.prayer.verticle.worker;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.configuration.impl.ConfigBllor;
import com.prayer.facade.configuration.ConfigInstantor;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.uca.consumer.BasicAuthConsumer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class SecurityWorker extends AbstractVerticle {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ConfigInstantor configSev;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public SecurityWorker() {
        super();
        this.configSev = singleton(ConfigBllor.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 主要方法，重写start **/
    @Override
    public void start() {
        // 1.获取当前Worker的类名
        final Class<?> workClass = getClass();
        // 2.获取元数据信息
        final PEAddress address = this.configSev.address(workClass);
        if (null != address) {
            // 3.从地址上消费Message
            final EventBus bus = vertx.eventBus();
            bus.consumer(address.getConsumerAddr(), BasicAuthConsumer.create());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
