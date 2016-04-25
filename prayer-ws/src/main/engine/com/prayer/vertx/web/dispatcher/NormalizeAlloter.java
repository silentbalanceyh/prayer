package com.prayer.vertx.web.dispatcher;

import java.text.MessageFormat;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.vtx.request.Alloter;

import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;

/**
 * 判断请求的基本信息
 * 
 * @author Lang
 *
 */
// 404：当前URI地址是否存在
// 405：当前Method是否允许
// 500：内部服务器错误
public class NormalizeAlloter implements Alloter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public void accept(final RoutingContext context) {
        /** 1.读取EventBus的地址数据 **/
        final String address = MessageFormat.format(WebKeys.URI_ADDR, context.get(WebKeys.Request.URI));
        final EventBus bus = context.vertx().eventBus();
        /** 2.构造Message Addr **/
        System.out.println(address);
        try {
            bus.consumer(address, handler -> {
                System.out.println(handler.body());
                context.next();
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
