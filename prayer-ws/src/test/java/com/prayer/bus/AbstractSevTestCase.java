package com.prayer.bus;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.bus.impl.std.MetaSevImpl;
import com.prayer.bus.impl.std.RecordSevImpl;
import com.prayer.facade.bus.MetaService;
import com.prayer.facade.bus.RecordService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.IOKit;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractSevTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param jsonFile
     * @return
     */
    protected JsonObject getParameter(final String jsonFile) {
        final String content = IOKit.getContent("/service/input/sec/" + jsonFile);
        JsonObject ret = new JsonObject();
        if (null != content) {
            ret = new JsonObject(content);
        }
        info(getLogger(), "[T] Params = " + ret.encode());
        return ret;
    }

    /**
     * 
     * @param result
     */
    protected boolean console(final ServiceResult<JsonObject> result) {
        if (null == result){
            info(getLogger(), "[E] Service Result is null.");
            return false;
        }
        final JsonObject ret = result.getResult();
        if(null == ret){
            info(getLogger(), "[E] Data of ServiceResult is null.");
            return false;
        }else{
            info(getLogger(), "[I] Return data is : " + ret.encode());
            return true;
        }
    }

    /**
     * Record Service
     * 
     * @return
     */
    protected RecordService getRService() {
        return singleton(RecordSevImpl.class);
    }

    /**
     * Meta Service
     * 
     * @return
     */
    protected MetaService getMService() {
        return singleton(MetaSevImpl.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
