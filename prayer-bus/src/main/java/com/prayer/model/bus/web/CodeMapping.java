package com.prayer.model.bus.web;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.Max;
import net.sf.oval.guard.Guarded;

/**
 * HTTP状态代码和异常代码之间的映射
 * 
 * @author Lang
 *
 */
@Guarded
final class CodeMapping { // NOPMD

	// ~ Static Fields =======================================
	/** Error Code -> Http Status Code **/
	public static final ConcurrentMap<Integer, StatusCode> CODE_MAP = new ConcurrentHashMap<>();

	// ~ Static Block ========================================
	/** **/
	static {
		// Fill mapping data
		if (CODE_MAP.isEmpty()) {
			CODE_MAP.put(-30001, StatusCode.BAD_REQUEST);
		}
	}

	// ~ Static Methods ======================================
	/**
	 * 
	 * @param errorCode
	 * @return
	 */
	public static StatusCode getStatus(@Max(-10000) final int errorCode) {
		return CODE_MAP.get(errorCode);
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================

	// ~ hashCode,equals,toString ============================
}
