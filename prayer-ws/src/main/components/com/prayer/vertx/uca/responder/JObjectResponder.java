package com.prayer.vertx.uca.responder;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.WebKeys;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 如果读取的数据只有一条，返回JsonObject作为data节点
 * @author Lang
 *
 */
@Guarded
public class JObjectResponder extends CommonResponder{
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
        /** 执行Body的转换 **/
        if (bodyRef.containsKey(Constants.PARAM.ADMINICLE.PAGE.RET.COUNT)) {
            final int count = bodyRef.getInteger(Constants.PARAM.ADMINICLE.PAGE.RET.COUNT);
            final JsonArray arrData = bodyRef.getJsonArray(Constants.PARAM.ADMINICLE.PAGE.RET.LIST);
            if(Constants.ONE == count && Constants.ONE == arrData.size()){
                final JsonObject finalData = arrData.getJsonObject(Constants.IDX).copy();
                data.remove(WebKeys.Envelop.DATA);
                data.put(WebKeys.Envelop.DATA, finalData);
            }
        }
        /** 回传Data节点 **/
        return data.encode();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
