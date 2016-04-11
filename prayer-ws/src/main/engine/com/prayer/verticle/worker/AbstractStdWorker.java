package com.prayer.verticle.worker;

import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.facade.business.configuration.ConfigInstantor;
import com.prayer.model.meta.vertx.PEAddress;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractStdWorker extends AbstractVerticle {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ConfigInstantor configSev;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractStdWorker() {
        super();
        this.configSev = singleton(ConfigBllor.class);
    }

    // ~ Abstract Methods ====================================
    /**
     * 子类实现该方法，会决定读取数据库中哪条元数据
     */
    protected abstract Class<?> getWorker();

    // ~ Override Methods ====================================
    /** 主要方法，重写start **/
    @Override
    public void start() {
        // 1.获取当前Worker的类名
        final Class<?> workClass = this.getWorker();
        // 2.获取元数据信息
        final PEAddress address = this.configSev.address(workClass);
        if (null != address) {
            // 3.从地址上消费Message
            final EventBus bus = vertx.eventBus();

            bus.consumer(address.getConsumerAddr(), instance(address.getConsumerHandler()));
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}