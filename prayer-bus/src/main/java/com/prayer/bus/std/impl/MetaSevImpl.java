package com.prayer.bus.std.impl;

import static com.prayer.bus.util.BusLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.std.MetaService;
import com.prayer.bus.util.BusLogger;
import com.prayer.bus.util.Interruptor;
import com.prayer.exception.AbstractException;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class MetaSevImpl implements MetaService {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaSevImpl.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public ServiceResult<JsonObject> save(JsonObject jsonObject) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServiceResult<JsonObject> remove(JsonObject jsonObject) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServiceResult<JsonObject> modify(JsonObject jsonObject) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServiceResult<JsonObject> find(JsonObject jsonObject) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     */
    @Override
    public ServiceResult<JsonObject> page(@NotNull final JsonObject jsonObject) {
        info(LOGGER, BusLogger.I_PARAM_INFO, "POST - Query", jsonObject.encode());
        ServiceResult<JsonObject> ret = new ServiceResult<>();
        AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
        if(null == error){
            // Page特殊参数
            error = Interruptor.interruptPageParams(getClass(), jsonObject);
            if(null == error){
                
            }else{
                // Page特殊参数缺失
                ret.failure(error);
            }
        }else{
            // 通用参数缺失
            ret.failure(error);
        }
        return ret;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}