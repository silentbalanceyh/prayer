package com.prayer.assistant;

import java.text.MessageFormat;

import org.slf4j.Logger;

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
public final class WebLogger {
	// ~ Static Fields =======================================
	/**
	 * Web Error
	 * @param logger
	 * @param patternAndMsg
	 * @param params
	 */
	public static void error(@NotNull final Logger logger, @NotNull @NotBlank @NotEmpty final String patternAndMsg,
			final Object... params){
		if (params.length == 0) {
			logger.error("[E-WEB] " + patternAndMsg);
		}else{
			logger.error(MessageFormat.format("[E-WEB] " + patternAndMsg, params));
		}
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private WebLogger() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
