package com.prayer.bus.impl.std;

import static com.prayer.util.bus.BusinessLogger.error;
import static com.prayer.util.bus.BusinessLogger.info;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.i.BasicAuthService;
import com.prayer.dao.impl.record.RecordDaoImpl;
import com.prayer.exception.AbstractException;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.bus.BusinessLogger;
import com.prayer.util.bus.Interruptor;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
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
    /** **/
    public BasicAuthSevImpl() {
        super(RecordDaoImpl.class, GenericRecord.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** **/
    @Override
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonArray> find(@NotNull final JsonObject jsonObject) {
        info(getLogger(), BusinessLogger.I_PARAM_INFO, "GET", jsonObject.encode());
        ServiceResult<JsonArray> ret = new ServiceResult<>();
        final AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.helper().sharedFind(jsonObject);
            } catch (ScriptException ex) {
                error(getLogger(), BusinessLogger.E_JS_ERROR, ex.toString());
                ret.error(new JSScriptEngineException(getClass(), ex.toString()));
                // TODO: Debug
                ex.printStackTrace();
            } catch (AbstractException ex) {
                error(getLogger(), BusinessLogger.E_AT_ERROR, ex.toString());
                ret.failure(ex);
                // TODO: Debug
                ex.printStackTrace();
            } 
            // TODO: Debug
            catch(Exception ex){
                ex.printStackTrace();
            }
        } else {
            ret.failure(error);
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
