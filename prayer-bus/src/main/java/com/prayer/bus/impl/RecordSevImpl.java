package com.prayer.bus.impl;

import static com.prayer.util.Error.info;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.RecordService;
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
public class RecordSevImpl implements RecordService {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordSevImpl.class);
	/** **/
	private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("nashorn");
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/* (non-Javadoc)
	 * @see com.prayer.bus.RecordService#create(io.vertx.core.json.JsonObject)
	 */
	@Override
	public ServiceResult<JsonObject> save(JsonObject jsonObject) {
		info(LOGGER,"[BUS] POST - Method : Params = " + jsonObject.encodePrettily());
		return null;
	}
	/* (non-Javadoc)
	 * @see com.prayer.bus.RecordService#remove(io.vertx.core.json.JsonObject)
	 */
	@Override
	public ServiceResult<JsonObject> remove(JsonObject jsonObject) {
		info(LOGGER,"[BUS] DELETE - Method : Params = " + jsonObject.encodePrettily());
		return null;
	}
	/* (non-Javadoc)
	 * @see com.prayer.bus.RecordService#modify(io.vertx.core.json.JsonObject)
	 */
	@Override
	public ServiceResult<JsonObject> modify(JsonObject jsonObject) {
		info(LOGGER,"[BUS] PUT - Method : Params = " + jsonObject.encodePrettily());
		return null;
	}
	/* (non-Javadoc)
	 * @see com.prayer.bus.RecordService#find(io.vertx.core.json.JsonObject)
	 */
	@Override
	public ServiceResult<JsonArray> find(JsonObject jsonObject) {
		info(LOGGER,"[BUS] GET - Method : Params = " + jsonObject.encodePrettily());
		return null;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
