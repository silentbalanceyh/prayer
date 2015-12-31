package com.prayer.base.bus;

import static com.prayer.util.Instance.reservoir;
import static com.prayer.util.Instance.singleton;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.prayer.base.exception.AbstractException;
import com.prayer.bus.impl.std.ServiceHelper;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.log.DebugKey;
import com.prayer.constant.log.InfoKey;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.facade.dao.RecordDao;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.bus.Interruptor;
import com.prayer.util.debug.Log;

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
    protected ServiceHelper getHelper() {
        return this.helper;
    }

    // ~ Private Methods =====================================
    private ServiceResult<JsonObject> executeSave(final JsonObject jsonObject) {
        ServiceResult<JsonObject> ret = new ServiceResult<>();
        AbstractException error = Interruptor.interruptRecordParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.helper.sharedSave(jsonObject);
                Log.info(getLogger(), InfoKey.INF_SEV_RET, ret.getResult().encode());
            } catch (ScriptException ex) {
                Log.jvmError(getLogger(),ex);
                error = new JSScriptEngineException(getClass(), ex.toString());
                Log.peError(getLogger(),error);
                ret.error(error);
            } catch (AbstractException ex) {
                Log.peError(getLogger(),ex);
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
        Log.debug(getLogger(), DebugKey.INFO_SEV_PARAM, "POST", jsonObject.encode());
        return this.executeSave(jsonObject);
    }

    /** **/
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> remove(@NotNull final JsonObject jsonObject) {
        Log.debug(getLogger(), DebugKey.INFO_SEV_PARAM, "DELETE", jsonObject.encode());
        ServiceResult<JsonObject> ret = new ServiceResult<>();
        AbstractException error = Interruptor.interruptRecordParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.helper.sharedDelete(jsonObject);
                Log.info(getLogger(), InfoKey.INF_SEV_RET, ret.getResult().encode());
            } catch (ScriptException ex) {
                Log.jvmError(getLogger(),ex);
                error = new JSScriptEngineException(getClass(), ex.toString());
                Log.peError(getLogger(),error);
                ret.error(error);
            } catch (AbstractException ex) {
                Log.peError(getLogger(),ex);
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
        Log.debug(getLogger(), DebugKey.INFO_SEV_PARAM, "PUT", jsonObject.encode());
        return this.executeSave(jsonObject);
    }

    /** **/
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonArray> find(@NotNull final JsonObject jsonObject) {
        Log.debug(getLogger(), DebugKey.INFO_SEV_PARAM, "DELETE", jsonObject.encode());
        ServiceResult<JsonArray> ret = new ServiceResult<>();
        AbstractException error = Interruptor.interruptRecordParams(getClass(), jsonObject);
        if (null == error) {
            try {
                ret = this.helper.sharedFind(jsonObject);
                Log.info(getLogger(), InfoKey.INF_SEV_RET, ret.getResult().encode());
            } catch (ScriptException ex) {
                Log.jvmError(getLogger(),ex);
                error = new JSScriptEngineException(getClass(), ex.toString());
                Log.peError(getLogger(),error);
                ret.error(error);
            } catch (AbstractException ex) {
                Log.peError(getLogger(),ex);
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
        Log.debug(getLogger(), DebugKey.INFO_SEV_PARAM, "POST - Query", jsonObject.encode());
        ServiceResult<JsonObject> ret = new ServiceResult<>();
        AbstractException error = Interruptor.interruptRecordParams(getClass(), jsonObject);
        if (null == error) {
            // Page特殊参数
            error = Interruptor.interruptPageParams(getClass(), jsonObject);
            if (null == error) {
                try {
                    ret = this.helper.sharedPage(jsonObject);
                    Log.info(getLogger(), InfoKey.INF_SEV_RET, ret.getResult().encode());
                } catch (ScriptException ex) {
                    Log.jvmError(getLogger(),ex);
                    error = new JSScriptEngineException(getClass(), ex.toString());
                    Log.peError(getLogger(),error);
                    ret.error(error);
                } catch (AbstractException ex) {
                    Log.peError(getLogger(),ex);
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
