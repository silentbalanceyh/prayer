package com.prayer.vertx.uca.responder;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.WebKeys;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 接口返回True或False
 * @author Lang
 *
 */
@Guarded
public class LogicalResponder extends CommonResponder {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** Body **/
    @Override
    public String buildBody(@NotNull final JsonObject data) {
        final JsonObject bodyRef = data.getJsonObject(WebKeys.Envelop.DATA).getJsonObject(WebKeys.Envelop.Data.BODY);
        /** 执行body的转换 **/
        boolean result = false;
        if (bodyRef.containsKey(Constants.PARAM.ADMINICLE.PAGE.RET.COUNT)) {
            final int count = bodyRef.getInteger(Constants.PARAM.ADMINICLE.PAGE.RET.COUNT);
            if (Constants.ZERO < count) {
                result = true;
            }
        }
        /** 将data节点清空 **/
        data.remove(WebKeys.Envelop.DATA);
        data.put(WebKeys.Envelop.DATA, result);
        return data.encode();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
