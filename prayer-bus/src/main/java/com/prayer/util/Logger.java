package com.prayer.util;

import com.prayer.util.cv.Resources;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Logger {
	// ~ Static Fields =======================================
	/** Info Properties **/
	private static final PropertyKit I_LOADER = new PropertyKit(Logger.class,
			Resources.LOG_CFG_FOLDER + "/info.properties");

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Logger() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
