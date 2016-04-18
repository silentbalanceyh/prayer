package com.prayer.business;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.reservoir;

import com.prayer.business.fun.ActMethod;
import com.prayer.business.service.RecordBehavior;
import com.prayer.facade.business.service.RecordService;
import com.prayer.facade.constant.Constants;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.util.io.IOKit;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractService extends AbstractBusiness {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String getFolder() {
        return "business/service/";
    }

    // ~ Methods =============================================
    /**
     * Service: Service - Behavior
     * 
     * @return
     */
    protected RecordService getRecordSrv(final Class<?> entityCls) {
        return reservoir(entityCls.getName(), RecordBehavior.class, entityCls);
    }

    /**
     * 
     * @param response
     * @param file
     * @param act
     * @return
     */
    protected ActResponse executeWithData(final ActResponse response, final String file, final HttpMethod method, final ActMethod act) {
        /** 3.生成uniqueId的Json信息 **/
        final JsonObject data = response.getResult();
        final ActRequest request = this.prepareRequest(file);
        
        // 不执行清除，内容会依旧
        // request.clearData();
        request.putData(method);
        request.putData(Constants.PID, data.getString(Constants.PID));
        info(getLogger(),"[T] Updated request : " + request.getData().encode());
        /** 4.执行Delete操作 **/
        return act.execute(request);
    }

    /**
     * 执行请求操作
     * 
     * @param act
     * @param file
     * @return
     */
    protected ActResponse execute(final ActMethod act, final String file) {
        final ActRequest request = this.prepareRequest(file);
        return act.execute(request);
    }
    // ~ Private Methods =====================================
    /**
     * 生成WebRequest
     * 
     * @param file
     * @return
     */
    protected ActRequest prepareRequest(final String file) {
        final String content = IOKit.getContent(path(file));
        ActRequest request = null;
        try {
            final JsonObject item = new JsonObject(content);
            request = new ActRequest(item);
        } catch (DecodeException ex) {
            jvmError(getLogger(), ex);
        }
        return request;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
