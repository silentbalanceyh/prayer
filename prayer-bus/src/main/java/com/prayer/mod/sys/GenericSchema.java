package com.prayer.mod.sys;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Json的Schema信息
 * 
 * @author Lang
 * @see
 */
public class GenericSchema {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient String fullname;
	/** **/
	private transient MetaModel meta;
	/** **/
	private transient ConcurrentMap<String, KeyModel> keys = new ConcurrentHashMap<>();
	/** **/
	private transient ConcurrentMap<String, FieldModel> fields = new ConcurrentHashMap<>();
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
