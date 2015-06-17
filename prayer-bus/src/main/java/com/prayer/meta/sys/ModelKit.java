package com.prayer.meta.sys;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * Model Configurator
 *
 * @author Lang
 * @see
 */
public class ModelKit {
	// ~ Static Fields =======================================
	/**
	 * 【池化单例】Model的工具池，保证每一个Model拥有一个实例
	 */
	private final static ConcurrentMap<String,ModelKit> KIT_MAP = new ConcurrentHashMap<>();
	/**
	 * SQL语句类
	 *
	 * @author Lang
	 * @see
	 */
	private static class SqlConstants{
		/** 根据NAME读取SYS_MODEL记录 **/
		private final static String QUERY_BY_NAME = "SELECT ROOT_FOLDER,FILE_DATA,FILE_SCHEMA,FILE_MAPPING FROM SYS_MODEL WHERE NAME=''{0}''";
		/** 读取所有正在使用的、或者未使用的Model **/
		private final static String QUERY_ALL = "SELECT NAME FROM SYS_MODEL WHERE IN_USE=''{0}'' ORDER BY INIT_ORDER ASC,INIT_SUB_ORDER ASC";
	}
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
