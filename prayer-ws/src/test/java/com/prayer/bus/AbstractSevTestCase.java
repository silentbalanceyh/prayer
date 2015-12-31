package com.prayer.bus;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.info;

import org.slf4j.Logger;

import com.prayer.bus.impl.std.MetaSevImpl;
import com.prayer.bus.impl.std.RecordSevImpl;
import com.prayer.facade.bus.MetaService;
import com.prayer.facade.bus.RecordService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonArray;
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
    protected <T> boolean console(final ServiceResult<T> result) {
        if (null == result) {
            info(getLogger(), "[E] Service Result is null.");
            return false;
        }
        final T ret = result.getResult();
        if (null == ret) {
            info(getLogger(), "[E] Data of ServiceResult is null.");
            return false;
        } else {
            if (ret instanceof JsonArray) {
                info(getLogger(), "[I] Return data is : " + ((JsonArray) ret).encode());
            } else if (ret instanceof JsonObject) {
                info(getLogger(), "[I] Return data is : " + ((JsonObject) ret).encode());
            }
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
