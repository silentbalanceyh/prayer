package com.prayer.vertx.web.dispatcher;

import java.text.MessageFormat;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.vtx.request.Alloter;
import com.prayer.vertx.util.SharedDator;
import com.prayer.vertx.util.UriAcquirer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
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
    public void accept(final RoutingContext context, final Handler<AsyncResult<JsonObject>> handler) {
        /** 1.读取Map名称 **/
        final String uri = UriAcquirer.acquirer(context);
        final String address = MessageFormat.format(WebKeys.URI_ADDR, uri);
        /** 2.获取Vertx引用 **/
        final Vertx vertx = context.vertx();
        /** 3.操作Map读取数据 **/
        if (vertx.isClustered()) {
            /** 4.Cluster模式 **/
            SharedDator.get(vertx, this.buildParams(address), res -> {
                if (res.succeeded()) {
                    handler.handle(Future.<JsonObject> succeededFuture(res.result()));
                }
            });
        } else {
            /** 5.Local模式 **/
            handler.handle(Future.<JsonObject> succeededFuture(SharedDator.get(vertx, this.buildParams(address))));
        }
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JsonObject buildParams(final String address) {
        final JsonObject params = new JsonObject();
        params.put(WebKeys.Shared.Params.MAP, WebKeys.Shared.URI);
        params.put(WebKeys.Shared.Params.KEY, address);
        return params;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
