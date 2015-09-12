package com.prayer.bus.impl;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.RecordService;
import com.prayer.bus.impl.util.ParamExtractor;
import com.prayer.bus.script.JavaScriptEngine;
import com.prayer.constant.Constants;
import com.prayer.dao.record.RecordDao;
import com.prayer.dao.record.impl.RecordDaoImpl;
import com.prayer.exception.AbstractException;
import com.prayer.kernel.Record;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

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
	@NotNull
	private transient final ParamExtractor extractor;
	/** **/
	@NotNull
	private transient final RecordDao recordDao;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RecordSevImpl() {
		this.extractor = ParamExtractor.create();
		this.recordDao = singleton(RecordDaoImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public ServiceResult<JsonObject> save(@NotNull final JsonObject jsonObject) {
		final JavaScriptEngine engine = JavaScriptEngine.getEngine(jsonObject.getJsonObject(Constants.PARAM_DATA));
		info(LOGGER, "[BUS] POST - Method : Params = " + jsonObject.encode());
		final ServiceResult<JsonObject> ret = new ServiceResult<>();
		final Record record = new GenericRecord(jsonObject.getString(Constants.PARAM_ID));
		try {
			engine.put("record", record);
			engine.execute(extractor.extractJSContent(jsonObject));
			final Record inserted = this.recordDao.insert(record);
			ret.setResult(extractor.extractRecord(inserted));
			info(LOGGER,"[BUS] Return Object : Object = " + ret.getResult());
		} catch (ScriptException ex) {
			info(LOGGER, "[BUS] Script Error : ", ex);
		} catch (AbstractException ex) {
			ret.setResponse(null, ex);
		}
		return ret;
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
