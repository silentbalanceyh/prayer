package com.prayer.bus.std.impl;

import static com.prayer.util.Instance.singleton;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.prayer.bus.script.JavaScriptEngine;
import com.prayer.bus.util.ParamExtractor;
import com.prayer.constant.Constants;
import com.prayer.dao.record.RecordDao;
import com.prayer.dao.record.impl.RecordDaoImpl;
import com.prayer.exception.AbstractException;
import com.prayer.kernel.Record;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;
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
	private transient final ParamExtractor extractor;
	/** **/
	@NotNull
	private transient final RecordDao recordDao;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public AbstractSevImpl() {
		this.extractor = ParamExtractor.create();
		this.recordDao = singleton(RecordDaoImpl.class);
	}

	// ~ Abstract Methods ====================================
	/** **/
	public abstract Logger getLogger();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	protected ServiceResult<JsonObject> sharedSave(@NotNull final JsonObject jsonObject)
			throws ScriptException, AbstractException {
		final ServiceResult<JsonObject> ret = new ServiceResult<>();
		// 1. 初始化脚本引擎以及Record对象
		final JavaScriptEngine engine = JavaScriptEngine.getEngine(jsonObject.getJsonObject(Constants.PARAM_DATA));
		final Record record = new GenericRecord(jsonObject.getString(Constants.PARAM_ID));
		// 2. 将Java和脚本引擎连接实现变量共享
		engine.put("record", record);
		engine.execute(extractor.extractJSContent(jsonObject));
		// 3. 执行Java脚本插入数据
		final Record inserted = this.recordDao.insert(record);
		final JsonObject retJson = extractor.extractRecord(inserted);
		// 4. 根据Filters的内容过滤属性
		this.extractor.filterRecord(retJson, jsonObject);
		ret.setResult(retJson);
		return ret;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
