package com.prayer.bus.std.impl;

import static com.prayer.bus.util.BusLogger.error;
import static com.prayer.bus.util.BusLogger.info;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.std.RecordService;
import com.prayer.bus.util.BusLogger;
import com.prayer.bus.util.Interruptor;
import com.prayer.dao.record.impl.RecordDaoImpl;
import com.prayer.exception.AbstractException;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.model.bus.ServiceResult;

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
public class RecordSevImpl extends AbstractSevImpl implements RecordService {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordSevImpl.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public RecordSevImpl(){
	    super(RecordDaoImpl.class,GenericRecord.class);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	/**
	 * 添加、保存方法
	 */
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<JsonObject> save(@NotNull final JsonObject jsonObject) {
		info(getLogger(), BusLogger.I_PARAM_INFO, "POST", jsonObject.encode());
		return this.executeSave(jsonObject);
	}

	/** **/
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<JsonObject> remove(@NotNull final JsonObject jsonObject) {
		info(LOGGER, BusLogger.I_PARAM_INFO, "DELETE", jsonObject.encode());
		ServiceResult<JsonObject> ret = new ServiceResult<>();
		final AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
		if (null == error) {
			try {
				ret = this.sharedDelete(jsonObject);
				info(getLogger(), BusLogger.I_RESULT_DB, ret.getResult().encode());
			} catch (ScriptException ex) {
				error(getLogger(), BusLogger.E_JS_ERROR, ex.toString());
				ret.error(new JSScriptEngineException(getClass(), ex.toString()));
			} catch (AbstractException ex) {
				error(getLogger(), BusLogger.E_AT_ERROR, ex.toString());
				ret.error(ex);
			}
		} else {
			ret.failure(error);
		}
		return ret;
	}

	/** **/
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<JsonObject> modify(@NotNull final JsonObject jsonObject) {
		info(LOGGER, BusLogger.I_PARAM_INFO, "PUT", jsonObject.encode());
		return this.executeSave(jsonObject);
	}

	/** **/
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<JsonArray> find(@NotNull final JsonObject jsonObject) {
		info(LOGGER, BusLogger.I_PARAM_INFO, "GET", jsonObject.encode());
		ServiceResult<JsonArray> ret = new ServiceResult<>();
		final AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
		if (null == error) {
			try {
				ret = this.sharedFind(jsonObject);
				info(getLogger(), BusLogger.I_RESULT_DB, ret.getResult().encode());
			} catch (ScriptException ex) {
				error(getLogger(), BusLogger.E_JS_ERROR, ex.toString());
				ret.error(new JSScriptEngineException(getClass(), ex.toString()));
			} catch (AbstractException ex) {
				error(getLogger(), BusLogger.E_AT_ERROR, ex.toString());
				ret.error(ex);
			}
		} else {
			ret.failure(error);
		}
		return ret;
	}

	/** **/
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<JsonObject> page(@NotNull final JsonObject jsonObject) {
		info(LOGGER, BusLogger.I_PARAM_INFO, "POST - Query", jsonObject.encode());
		ServiceResult<JsonObject> ret = new ServiceResult<>();
		AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
		if (null == error) {
			// Page特殊参数
			error = Interruptor.interruptPageParams(getClass(), jsonObject);
			if (null == error) {
				try {
					ret = this.sharedPage(jsonObject);
					info(getLogger(), BusLogger.I_RESULT_DB, ret.getResult().encode());
				} catch (ScriptException ex) {
					error(getLogger(), BusLogger.E_JS_ERROR, ex.toString());
					ret.error(new JSScriptEngineException(getClass(), ex.toString()));
				} catch (AbstractException ex) {
					error(getLogger(), BusLogger.E_AT_ERROR, ex.toString());
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
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private ServiceResult<JsonObject> executeSave(final JsonObject jsonObject) {
		ServiceResult<JsonObject> ret = new ServiceResult<>();
		final AbstractException error = Interruptor.interruptParams(getClass(), jsonObject);
		if (null == error) {
			try {
				ret = this.sharedSave(jsonObject);
				info(getLogger(), BusLogger.I_RESULT_DB, ret.getResult().encode());
			} catch (ScriptException ex) {
				error(getLogger(), BusLogger.E_JS_ERROR, ex.toString());
				ret.error(new JSScriptEngineException(getClass(), ex.toString()));
			} catch (AbstractException ex) {
				error(getLogger(), BusLogger.E_AT_ERROR, ex.toString());
				ret.error(ex);
			}
		} else {
			ret.failure(error);
		}
		return ret;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
