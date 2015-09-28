package com.prayer.bus.security.impl;

import static com.prayer.bus.util.BusLogger.error;
import static com.prayer.bus.util.BusLogger.info;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.security.BasicAuthService;
import com.prayer.bus.std.impl.AbstractSevImpl;
import com.prayer.bus.util.BusLogger;
import com.prayer.bus.util.Interruptor;
import com.prayer.exception.AbstractException;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class BasicAuthSevImpl extends AbstractSevImpl implements BasicAuthService {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthSevImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** **/
    @Override
    public ServiceResult<JsonArray> find(final JsonObject jsonObject) {
        info(getLogger(), BusLogger.I_PARAM_INFO, "GET", jsonObject.encode());
        ServiceResult<JsonArray> ret = new ServiceResult<>();
        final AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.sharedFind(jsonObject);
            } catch (ScriptException ex) {
                error(getLogger(), BusLogger.E_JS_ERROR, ex.toString());
                ret.setResponse(null, new JSScriptEngineException(getClass(), ex.toString()));
            } catch (AbstractException ex) {
                ret.setResponse(null, ex);
            }
        } else {
            ret.setResponse(null, error);
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
