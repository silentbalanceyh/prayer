package com.prayer.bus.impl.std;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.bus.AbstractSevImpl;
import com.prayer.base.exception.AbstractException;
import com.prayer.constant.log.DebugKey;
import com.prayer.dao.impl.std.record.RecordDaoImpl;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.facade.bus.BasicAuthService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.kernel.GenericRecord;
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
        debug(getLogger(), DebugKey.INFO_SEV_PARAM, "GET", jsonObject.encode());
        ServiceResult<JsonArray> ret = new ServiceResult<>();
        AbstractException error = Interruptor.interruptRecordParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.getHelper().sharedFind(jsonObject);
            } catch (AbstractException ex) {
                peError(getLogger(), ex);
                ret.failure(ex);
            } catch (ScriptException ex) {
                jvmError(getLogger(), ex);
                error = new JSScriptEngineException(getClass(), ex.toString());
                peError(getLogger(), error);
                ret.error(error);
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
