package com.prayer.vertx.handler.aop;

import java.util.Iterator;
import java.util.Map.Entry;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.engine.cv.WebKeys;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

/**
 * 【AOP】在接受数据之前处理并且填充URI数据
 * 
 * @author Lang
 *
 */
public aspect AjUriStrainer {
    // ~ Point Cut ===========================================
    /** 切点定义，只有Acceptor才会执行 **/
    pointcut UriPointCut(final RoutingContext event): execution(void com.prayer.vertx.handler.standard.RequestAcceptor.handle(RoutingContext)) && args(event) && target(Handler);

    // ~ Point Cut Implements ================================
    /** 切点实现 **/
    before(final RoutingContext event):UriPointCut(event){
        /** 1.提取Request **/
        final HttpServerRequest request = event.request();
        final HttpMethod method = request.method();
        /** 2.GET或DELETE方法才会执行该URI过滤操作 **/
        String finalUri = request.path();
        if (HttpMethod.GET == method || HttpMethod.DELETE == method) {
            /** 3.计算URI **/
            final StringBuilder path = new StringBuilder(finalUri);
            /** 4.参数提取 **/
            final Iterator<Entry<String, String>> entryIt = request.params().iterator();
            while (entryIt.hasNext()) {
                final Entry<String, String> item = entryIt.next();
                final int start = path.indexOf(item.getValue());
                /** 5.包含值则替换成name **/
                if (Constants.ZERO <= start) {
                    final int end = start + item.getValue().length();
                    path.replace(start, end, Symbol.COLON + item.getKey());
                }
            }
            /** 6.计算最终值 **/
            finalUri = path.toString();
        }
        /** 7.将内容放到Context中 **/
        event.put(WebKeys.Request.URI, finalUri);
    }
}
