package com.prayer.constant;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;
/**
 * 静态变量统一管理
 * @author Lang
 *
 */
public interface MemoryPool {	// NOPMD
	// ~ Static Fields =======================================
	/** 数据源的Pool **/
	ConcurrentMap<String, DataSource> POOL_DATA_SOURCE = new ConcurrentHashMap<>();
	/** KEY -> POOL 全局单例模式 **/
	ConcurrentMap<String, Object> POOL_OBJECT = new ConcurrentHashMap<>();
	/** 资源文件池 **/
	ConcurrentMap<String, Properties> POOL_PROP = new ConcurrentHashMap<>();
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
