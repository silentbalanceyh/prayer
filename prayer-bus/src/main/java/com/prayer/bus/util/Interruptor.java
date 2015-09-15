package com.prayer.bus.util;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractException;
import com.prayer.exception.web.ServiceParamInvalidException;
import com.prayer.exception.web.ServiceParamMissingException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/** **/
@Guarded
public final class Interruptor {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/**
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static AbstractException interruptParams(@NotNull final Class<?> clazz,
			@NotNull final JsonObject jsonObject) {
		AbstractException error = null;
		try {
			if (!(jsonObject.containsKey(Constants.PARAM_ID) && jsonObject.containsKey(Constants.PARAM_DATA)
					&& jsonObject.containsKey(Constants.PARAM_SCRIPT))) {
				if (!jsonObject.containsKey(Constants.PARAM_ID)) { // NOPMD
					error = new ServiceParamMissingException(clazz, Constants.PARAM_ID);
				} else if (!jsonObject.containsKey(Constants.PARAM_DATA)) { // NOPMD
					error = new ServiceParamMissingException(clazz, Constants.PARAM_DATA);
				} else if (!jsonObject.containsKey(Constants.PARAM_SCRIPT)) { // NOPMD
					error = new ServiceParamMissingException(clazz, Constants.PARAM_SCRIPT);
				}
			}
			jsonObject.getString(Constants.PARAM_ID);
			jsonObject.getJsonObject(Constants.PARAM_DATA);
			jsonObject.getString(Constants.PARAM_SCRIPT);
		} catch (ClassCastException ex) {
			error = new ServiceParamInvalidException(clazz, ex.toString());
		}
		return error;
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Interruptor() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
