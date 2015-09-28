package com.prayer.bus.util;

import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractException;
import com.prayer.exception.web.PrimaryKeyMissingException;
import com.prayer.exception.web.ServiceParamInvalidException;
import com.prayer.exception.web.ServiceParamMissingException;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.util.StringKit;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/** **/
@Guarded
public final class Interruptor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /**
     * 
     * @param jsonObject
     * @return
     */
    public static AbstractException interruptParams(@NotNull final Class<?> clazz,
            @NotNull final JsonObject jsonObject) {
        AbstractException error = null;
        try {
            if (!(jsonObject.containsKey(Constants.PARAM.ID) && jsonObject.containsKey(Constants.PARAM.DATA)
                    && jsonObject.containsKey(Constants.PARAM.SCRIPT))) {
                if (!jsonObject.containsKey(Constants.PARAM.ID)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.PARAM.ID);
                } else if (!jsonObject.containsKey(Constants.PARAM.DATA)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.PARAM.DATA);
                } else if (!jsonObject.containsKey(Constants.PARAM.SCRIPT)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.PARAM.SCRIPT);
                }
            }
            jsonObject.getString(Constants.PARAM.ID);
            jsonObject.getJsonObject(Constants.PARAM.DATA);
            jsonObject.getString(Constants.PARAM.SCRIPT);
        } catch (ClassCastException ex) {
            error = new ServiceParamInvalidException(clazz, ex.toString());
        }
        return error;
    }

    /**
     * 
     * @param record
     * @return
     */
    public static boolean isUpdate(@NotNull final Record record){
        boolean isUpdate = true;
        try{
            final ConcurrentMap<String,Value<?>> idKV = record.idKV();
            for(final String id: idKV.keySet()){
                final Value<?> value = idKV.get(id);
                if(StringKit.isNil(value.literal())){
                    isUpdate = false;
                }
            }
        }catch(AbstractException ex){
            
        }
        return isUpdate;
    }
    /**
     * 
     * @param record
     * @return
     */
    public static AbstractException interruptPK(@NotNull final Record record){
        AbstractException error = null;
        try{
            final ConcurrentMap<String,Value<?>> idKV = record.idKV();
            for(final String id: idKV.keySet()){
                final Value<?> value = idKV.get(id);
                if(StringKit.isNil(value.literal())){
                    error = new PrimaryKeyMissingException(Interruptor.class,id);
                }
            }
        }catch(AbstractException ex){
            error = ex;
        }
        return error;
    }
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Interruptor() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
