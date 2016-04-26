package com.prayer.vertx.handler.pin;

import java.util.Iterator;
import java.util.Map.Entry;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.vertx.util.UriAcquirer;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public class PatternHooker implements Handler<RoutingContext> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 占位用的Handler，无实际意义，主要用于Pattern钩子 **/
    @Override
    public void handle(final RoutingContext event) {
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
        UriAcquirer.lay(event, finalUri);
        event.next();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
