package com.prayer.handler.cache;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.AddressModel;

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
public class AsyncAddressHandler implements Handler<AsyncResult<AsyncMap<String, AddressModel>>> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final ConfigService configSev;
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
        this.configSev = singleton(ConfigSevImpl.class);
        this.handler = handler;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void handle(AsyncResult<AsyncMap<String, AddressModel>> event) {
        if (event.succeeded()) {
            // 1.Handler获取异步Map
            final AsyncMap<String, AddressModel> dataMap = event.result();
            // 2.使用类名
            final String clsName = this.workClass.getName();
            // 2.从dataMap中读取
            System.out.println(dataMap);
            dataMap.get(clsName, res -> {
                if (res.succeeded()) {
                    AddressModel addr = res.result();
                    if (null == addr) {
                        this.vertxRef.<AddressModel> executeBlocking(block -> {
                            final ServiceResult<AddressModel> result = this.configSev.findAddress(this.workClass);
                            if (ResponseCode.SUCCESS == result.getResponseCode()) {
                                final AddressModel addrRef = result.getResult();
                                if (null != addrRef.getConsumerHandler()) {
                                    final EventBus bus = this.vertxRef.eventBus();
                                    bus.consumer(addrRef.getConsumerAddr(), instance(addrRef.getConsumerHandler()));
                                }
                                block.complete(addrRef);
                            }
                        } , ret -> {
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
