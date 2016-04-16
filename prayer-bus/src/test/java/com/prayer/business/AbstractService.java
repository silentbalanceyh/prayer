package com.prayer.business;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.service.RecordBehavior;
import com.prayer.facade.business.service.RecordService;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.util.io.IOKit;

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
    protected RecordService getRecordSrv() {
        return singleton(RecordBehavior.class);
    }

    /**
     * 生成WebRequest
     * 
     * @param file
     * @return
     */
    protected ActRequest prepareRequest(final String file) {
        final String content = IOKit.getContent(path(file));
        ActRequest request = null;
        try{
            final JsonObject item = new JsonObject(content);
            request = new ActRequest(item);
        }catch(DecodeException ex){
            jvmError(getLogger(),ex);
        }
        return request;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
