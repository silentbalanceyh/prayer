package com.prayer.handler.cache;

import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.facade.business.configuration.ConfigInstantor;
import com.prayer.model.meta.vertx.PEAddress;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.shareddata.AsyncMap;

/**
 * 
 * @author Lang
 *
 */
public class AsyncAddressHandler implements Handler<AsyncResult<AsyncMap<String, PEAddress>>> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final ConfigInstantor configSev;
    /** **/
    private transient final Class<?> workClass;
    /** **/
    private transient final Vertx vertxRef;
    /** **/
    private transient final Handler<AsyncResult<Void>> handler;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param busRef
     * @param workClass
     * @return
     */
    public static AsyncAddressHandler create(final Vertx busRef, final Class<?> workClass,
            final Handler<AsyncResult<Void>> handler) {
        return new AsyncAddressHandler(busRef, workClass, handler);
    }

    // ~ Constructors ========================================
    /**
     * 
     * @param busRef
     * @param workClass
     */
    private AsyncAddressHandler(final Vertx vertx, final Class<?> workClass, final Handler<AsyncResult<Void>> handler) {
        this.workClass = workClass;
        this.vertxRef = vertx;
        this.configSev = singleton(ConfigBllor.class);
        this.handler = handler;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void handle(AsyncResult<AsyncMap<String, PEAddress>> event) {
        if (event.succeeded()) {
            // 1.Handler获取异步Map
            final AsyncMap<String, PEAddress> dataMap = event.result();
            // 2.使用类名
            final String clsName = this.workClass.getName();
            // 2.从dataMap中读取
            System.out.println(dataMap);
            dataMap.get(clsName, res -> {
                if (res.succeeded()) {
                    PEAddress addr = res.result();
                    if (null == addr) {
                        this.vertxRef.<PEAddress> executeBlocking(block -> {
                            PEAddress addrRef = this.configSev.address(this.workClass);
                            if (null != addrRef.getConsumerHandler()) {
                                final EventBus bus = this.vertxRef.eventBus();
                                bus.consumer(addrRef.getConsumerAddr(), instance(addrRef.getConsumerHandler()));
                            }
                            block.complete(addrRef);
                        }, ret -> {
                            dataMap.put(clsName, ret.result(), this.handler);
                        });

                    } else {
                        if (null != addr.getConsumerHandler()) {
                            final EventBus bus = this.vertxRef.eventBus();
                            bus.consumer(addr.getConsumerAddr(), instance(addr.getConsumerHandler()));
                        }
                    }
                }
            });
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
