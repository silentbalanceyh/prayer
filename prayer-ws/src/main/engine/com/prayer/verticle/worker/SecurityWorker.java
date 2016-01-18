package com.prayer.verticle.worker;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
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
    private transient final ConfigService configSev;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public SecurityWorker() {
        super();
        this.configSev = singleton(ConfigSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 主要方法，重写start **/
    @Override
    public void start() {
        // 1.获取当前Worker的类名
        final Class<?> workClass = getClass();
        // 2.获取元数据信息
        final ServiceResult<PEAddress> result = this.configSev.findAddress(workClass);
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            final PEAddress address = result.getResult();
            if (null != address) {
                // 3.从地址上消费Message
                final EventBus bus = vertx.eventBus();
                bus.consumer(address.getConsumerAddr(),BasicAuthConsumer.create());
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
