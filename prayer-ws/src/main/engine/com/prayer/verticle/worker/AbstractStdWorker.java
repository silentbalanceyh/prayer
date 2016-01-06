package com.prayer.verticle.worker;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.AddressModel;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import net.sf.oval.constraint.NotNull;
/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractStdWorker extends AbstractVerticle{
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
    @NotNull
    private transient final ConfigService configSev;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
    /** **/
    public AbstractStdWorker() {
        super();
        this.configSev = singleton(ConfigSevImpl.class);
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
        final ServiceResult<AddressModel> result = this.configSev.findAddress(workClass);
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            final AddressModel address = result.getResult();
            if (null != address) {
                // 3.从地址上消费Message
                final EventBus bus = vertx.eventBus();
                
                bus.consumer(address.getConsumerAddr(),instance(address.getConsumerHandler()));
            }
        }
    }
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}