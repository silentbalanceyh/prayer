package com.prayer.bus.impl.util;

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
public final class BusLogger {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * Web Information
	 * 
	 * @param logger
	 * @param patternAndMsg
	 * @param params
	 */
	public static void info(@NotNull final Logger logger, @NotNull @NotBlank @NotEmpty final String patternAndMsg,
			final Object... params) {
		if (params.length == 0) {
			com.prayer.util.Error.info(logger, "[I-BUS] " + patternAndMsg);
		} else {
			com.prayer.util.Error.info(logger, MessageFormat.format("[I-BUS] " + patternAndMsg, params));
		}
	}

	/**
	 * Web Error
	 * 
	 * @param logger
	 * @param patternAndMsg
	 * @param params
	 */
	public static void error(@NotNull final Logger logger, @NotNull @NotBlank @NotEmpty final String patternAndMsg,
			final Object... params) {
		if (params.length == 0) {
			logger.error("[E-BUS] " + patternAndMsg);
		} else {
			logger.error(MessageFormat.format("[E-BUS] " + patternAndMsg, params));
		}
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private BusLogger() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
