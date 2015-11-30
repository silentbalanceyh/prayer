package com.prayer.bus.impl.std;

import static com.prayer.util.Instance.reservoir;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.bus.BusinessLogger.error;
import static com.prayer.util.bus.BusinessLogger.info;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.prayer.exception.AbstractException;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.facade.dao.RecordDao;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.bus.BusinessLogger;
import com.prayer.util.bus.Interruptor;
import com.prayer.util.cv.MemoryPool;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/** **/
@Guarded
public abstract class AbstractSevImpl {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final RecordDao recordDao;
    /** **/
    @NotNull
    private transient final ServiceHelper helper;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractSevImpl(@NotNull final Class<?> daoCls, @NotNull final Class<?> entityCls) {
        this.recordDao = singleton(daoCls);
        this.helper = reservoir(MemoryPool.POOL_SEV_HELPER, this.recordDao.getClass().getName(), ServiceHelper.class,
                this.recordDao, entityCls);
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @NotNull
    protected RecordDao getDao() {
        return this.recordDao;
    }

    /** **/
    @NotNull
    protected ServiceHelper helper() {
        return this.helper;
    }

    // ~ Private Methods =====================================
    private ServiceResult<JsonObject> executeSave(final JsonObject jsonObject) {
        ServiceResult<JsonObject> ret = new ServiceResult<>();
        final AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.helper.sharedSave(jsonObject);
                info(getLogger(), BusinessLogger.I_RESULT_DB, ret.getResult().encode());
            } catch (ScriptException ex) {
                error(getLogger(), BusinessLogger.E_JS_ERROR, ex.toString());
                ret.error(new JSScriptEngineException(getClass(), ex.toString()));
            } catch (AbstractException ex) {
                error(getLogger(), BusinessLogger.E_AT_ERROR, ex.toString());
                ret.error(ex);
            }
        } else {
            ret.failure(error);
        }
        return ret;
    }
    // ~ Get/Set =============================================

    // ~ Template Method =====================================
    /**
     * 
     * @param jsonObject
     * @return
     */
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> save(@NotNull final JsonObject jsonObject) {
        info(getLogger(), BusinessLogger.I_PARAM_INFO, "POST", jsonObject.encode());
        return this.executeSave(jsonObject);
    }

    /** **/
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> remove(@NotNull final JsonObject jsonObject) {
        info(getLogger(), BusinessLogger.I_PARAM_INFO, "DELETE", jsonObject.encode());
        ServiceResult<JsonObject> ret = new ServiceResult<>();
        final AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.helper.sharedDelete(jsonObject);
                info(getLogger(), BusinessLogger.I_RESULT_DB, ret.getResult().encode());
            } catch (ScriptException ex) {
                error(getLogger(), BusinessLogger.E_JS_ERROR, ex.toString());
                ret.error(new JSScriptEngineException(getClass(), ex.toString()));
            } catch (AbstractException ex) {
                error(getLogger(), BusinessLogger.E_AT_ERROR, ex.toString());
                ret.error(ex);
            }
        } else {
            ret.failure(error);
        }
        return ret;
    }

    /** **/
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> modify(@NotNull final JsonObject jsonObject) {
        info(getLogger(), BusinessLogger.I_PARAM_INFO, "PUT", jsonObject.encode());
        return this.executeSave(jsonObject);
    }

    /** **/
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonArray> find(@NotNull final JsonObject jsonObject) {
        info(getLogger(), BusinessLogger.I_PARAM_INFO, "GET", jsonObject.encode());
        ServiceResult<JsonArray> ret = new ServiceResult<>();
        final AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.helper.sharedFind(jsonObject);
                info(getLogger(), BusinessLogger.I_RESULT_DB, ret.getResult().encode());
            } catch (ScriptException ex) {
                error(getLogger(), BusinessLogger.E_JS_ERROR, ex.toString());
                ret.error(new JSScriptEngineException(getClass(), ex.toString()));
            } catch (AbstractException ex) {
                error(getLogger(), BusinessLogger.E_AT_ERROR, ex.toString());
                ret.error(ex);
            }
        } else {
            ret.failure(error);
        }
        return ret;
    }

    /** **/
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> page(@NotNull final JsonObject jsonObject) {
        info(getLogger(), BusinessLogger.I_PARAM_INFO, "POST - Query", jsonObject.encode());
        ServiceResult<JsonObject> ret = new ServiceResult<>();
        AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
        if (null == error) {
            // Page特殊参数
            error = Interruptor.interruptPageParams(getClass(), jsonObject);
            if (null == error) {
                try {
                    ret = this.helper.sharedPage(jsonObject);
                    info(getLogger(), BusinessLogger.I_RESULT_DB, ret.getResult().encode());
                } catch (ScriptException ex) {
                    error(getLogger(), BusinessLogger.E_JS_ERROR, ex.toString());
                    ret.error(new JSScriptEngineException(getClass(), ex.toString()));
                } catch (AbstractException ex) {
                    error(getLogger(), BusinessLogger.E_AT_ERROR, ex.toString());
                    ret.error(ex);
                }
            } else {
                // Page特殊参数缺失
                ret.failure(error);
            }
        } else {
            // 通用参数缺失
            ret.failure(error);
        }
        return ret;
    }
    // ~ hashCode,equals,toString ============================
}
