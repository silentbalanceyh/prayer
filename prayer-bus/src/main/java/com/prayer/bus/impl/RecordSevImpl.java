package com.prayer.bus.impl;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.RecordService;
import com.prayer.bus.impl.util.ParamExtractor;
import com.prayer.bus.script.JavaScriptEngine;
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
	// ~ Instance Fields =====================================
	/** **/
	private transient final ParamExtractor extractor;
	/** **/
	private transient final JavaScriptEngine engine;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public RecordSevImpl(){
		this.extractor = ParamExtractor.create();
		this.engine = JavaScriptEngine.getEngine();
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prayer.bus.RecordService#create(io.vertx.core.json.JsonObject)
	 */
	@Override
	public ServiceResult<JsonObject> save(JsonObject jsonObject) {
		info(LOGGER, "[BUS] POST - Method : Params = " + jsonObject.encode());
		this.engine.execute(extractor.extractJSContent(jsonObject));
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prayer.bus.RecordService#remove(io.vertx.core.json.JsonObject)
	 */
	@Override
	public ServiceResult<JsonObject> remove(JsonObject jsonObject) {
		info(LOGGER, "[BUS] DELETE - Method : Params = " + jsonObject.encode());
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prayer.bus.RecordService#modify(io.vertx.core.json.JsonObject)
	 */
	@Override
	public ServiceResult<JsonObject> modify(JsonObject jsonObject) {
		info(LOGGER, "[BUS] PUT - Method : Params = " + jsonObject.encode());
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prayer.bus.RecordService#find(io.vertx.core.json.JsonObject)
	 */
	@Override
	public ServiceResult<JsonArray> find(JsonObject jsonObject) {
		info(LOGGER, "[BUS] GET - Method : Params = " + jsonObject.encode());
		return null;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
