package com.prayer.vertx.handler.standard;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.util.string.StringKit;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 这里需要初始化请求操作了
 * 
 * @author Lang
 *
 */
public class DataStdner implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    public static DataStdner create() {
        return new DataStdner();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final RoutingContext event) {
        /** 1.提取参数和URI规范 **/
        final JsonObject data = event.get(WebKeys.Request.Data.PARAMS);
        final PEUri uri = event.get(WebKeys.Request.Data.Meta.PEURI);
        /** 2.构造最终Request的Json **/
        final JsonObject params = new JsonObject();
        /** 根节点数据 **/
        params.put(Constants.PARAM.ID, uri.getGlobalId());
        params.put(Constants.PARAM.METHOD, uri.getMethod().name());
        if (StringKit.isNonNil(uri.getScript())) {
            params.put(Constants.PARAM.SCRIPT, uri.getScript());
        }
        /** 3.处理Query参数，如果带Query则需要 **/
        if (data.containsKey(Constants.PARAM.QUERY)) {
            params.put(Constants.PARAM.QUERY, data.getJsonObject(Constants.PARAM.QUERY).copy());
            data.remove(Constants.PARAM.QUERY);
        }
        /** 4.填充Data **/
        params.put(Constants.PARAM.DATA, data);
        event.put(WebKeys.Request.Data.PARAMS, params);
        event.next();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
