package com.prayer.business;

import static com.prayer.util.debug.Log.jvmError;

import com.prayer.database.accessor.impl.MetaAccessorImpl;
import com.prayer.facade.database.accessor.MetaAccessor;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractConfigInstantor<T> extends AbstractInstantor{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 读取Accessor **/
    protected MetaAccessor accessor(final Class<?> genericT){
        return new MetaAccessorImpl(genericT);
    }
    // ~ Methods =============================================
    /**
     * 读取JsonObject
     * @param file
     * @return
     */
    protected JsonObject readData(final String file){
        final String content = IOKit.getContent(path(file));
        JsonObject ret = new JsonObject();
        try{
            ret = new JsonObject(content);
        }catch(DecodeException ex){
            jvmError(getLogger(),ex);
        }
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
