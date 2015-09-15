package com.prayer.bus.util;

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
	/** **/
	public static final String I_AOP_V_ERROR = "[AOP] Aspect validator met error ! Schema = {0}, Field = {1}";
	/** **/
	public static final String I_DPDATA_SUCCESS = "OOB Data deployment successfully !";
	/** New importer **/
	public static final String I_IMPORTER_NEW = "(Create) Initialize new metadata importer : file = {0}";
	/** Exist importer **/
	public static final String I_IMPORTER_EXIST = "(Update) Refresh new metadata importer : file = {0}";
	/** Parameters **/
	public static final String I_PARAM_INFO = "HttpMethod = {0}, Params = {1}";
	/** Return object **/
	public static final String I_RESULT_DB = "DB Accessing result is : Return Object = {0}"; 
	
	/** **/
	public static final String E_JS_ERROR = "Script Engine executed script failure: ex = {1}";

	/** **/
	public static final String E_DPDATA_ERR = "OOB Data deployment met error. Error = {0}";
	/** **/
	public static final String E_AT_ERROR = "Abstract transaction exception occurs. ex = {0}";
	/** **/
	public static final String E_PROCESS_ERR = "{0} exception occurs, ex = {1}";
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
