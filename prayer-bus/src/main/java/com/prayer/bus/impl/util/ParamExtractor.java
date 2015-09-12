package com.prayer.bus.impl.util;

import static com.prayer.util.Instance.singleton;

import com.prayer.bus.ConfigService;
import com.prayer.bus.SchemaService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.bus.impl.SchemaSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.script.ScriptModel;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class ParamExtractor {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** Config Service 接口 **/
	private transient final ConfigService configSev;
	/** Schema Service 接口 **/
	private transient final SchemaService schemaSev;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** 创建新实例 **/
	public static ParamExtractor create() {
		return new ParamExtractor();
	}

	// ~ Constructors ========================================
	/** **/
	private ParamExtractor() {
		this.configSev = singleton(ConfigSevImpl.class);
		this.schemaSev = singleton(SchemaSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public String extractJSContent(@NotNull final JsonObject parameters) {
		final String scriptName = parameters.getString(Constants.PARAM_SCRIPT);
		final ServiceResult<ScriptModel> script = this.configSev.findScript(scriptName);
		String ret = "";
		if (ResponseCode.SUCCESS == script.getResponseCode()) {
			ret = script.getResult().getContent();
		}
		return ret;
	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public GenericSchema extractSchema(@NotNull final JsonObject parameters) {
		final String identifier = parameters.getString(Constants.PARAM_ID);
		final ServiceResult<GenericSchema> schema = this.schemaSev.findSchema(identifier);
		GenericSchema ret = null;
		if (ResponseCode.SUCCESS == schema.getResponseCode()) {
			ret = schema.getResult();
		}
		return ret;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
