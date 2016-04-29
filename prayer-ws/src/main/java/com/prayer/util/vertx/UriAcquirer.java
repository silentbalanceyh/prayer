package com.prayer.util.vertx;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.util.string.StringKit;

import io.vertx.ext.web.RoutingContext;

/**
 * URI的处理器，用于读写固定的URI信息
 * 
 * @author Lang
 *
 */
public final class UriAcquirer {
    // ~ Static Fields =======================================

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 读取URI信息
     * 
     * @param context
     * @return
     */
    public static String acquirer(final RoutingContext context) {
        /** 从Context中读取 **/
        String uri = context.get(WebKeys.Request.URI);
        /** 读取不了直接从Request Path中提取 **/
        if (StringKit.isNil(uri)) {
            uri = context.request().path();
        }
        return uri;
    }

    /**
     * 放置URI信息
     * 
     * @param context
     * @param uri
     */
    public static void lay(final RoutingContext context, final String uri) {
        context.put(WebKeys.Request.URI, uri);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private UriAcquirer() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
