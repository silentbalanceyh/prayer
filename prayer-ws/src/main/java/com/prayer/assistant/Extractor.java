package com.prayer.assistant;

import com.prayer.constant.Constants;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Extractor {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/** **/
	public static Integer getNumber(@NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String key) {
		Integer integer = Integer.valueOf(Constants.RANGE);
		try {
			// 过滤null值
			if (config.containsKey(key)) {
				integer = config.getInteger(key);
			}
		} catch (ClassCastException ex) {
			integer = null;	// NOPMD
		}
		return integer;
	}

	/**
	 * 
	 * @param config
	 * @param key
	 * @return
	 */
	public static String getString(@NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String key) {
		String str = Constants.EMPTY_STR;
		try {
			if (config.containsKey(key)) {
				str = config.getString(key);
			}
		} catch (ClassCastException ex) {
			str = null;	// NOPMD
		}
		return str;
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Extractor() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
