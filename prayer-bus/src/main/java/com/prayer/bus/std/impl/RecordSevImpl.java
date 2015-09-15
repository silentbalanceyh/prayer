package com.prayer.bus.std.impl;

import static com.prayer.bus.util.BusLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.std.RecordService;
import com.prayer.bus.util.BusLogger;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Logger getLogger(){
		return LOGGER;
	}
	/**
	 * 添加、保存方法
	 */
	@Override
	public ServiceResult<JsonObject> save(@NotNull final JsonObject jsonObject) {
		return this.sharedSave(jsonObject);
	}

	/** **/
	@Override
	public ServiceResult<JsonObject> remove(@NotNull final JsonObject jsonObject) {
		info(LOGGER, BusLogger.I_PARAM_INFO, "DELETE", jsonObject.encode());
		return null;
	}

	/** **/
	@Override
	public ServiceResult<JsonObject> modify(@NotNull final JsonObject jsonObject) {
		info(LOGGER, BusLogger.I_PARAM_INFO, "PUT", jsonObject.encode());
		return null;
	}

	/** **/
	@Override
	public ServiceResult<JsonArray> find(@NotNull final JsonObject jsonObject) {
		info(LOGGER, BusLogger.I_PARAM_INFO, "GET", jsonObject.encode());
		return null;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
