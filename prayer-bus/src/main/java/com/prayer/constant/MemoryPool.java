package com.prayer.constant;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import com.prayer.db.conn.JdbcContext;
import com.prayer.kernel.Builder;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.schema.Importer;

/**
 * 静态变量统一管理
 * @author Lang
 *
 */
public interface MemoryPool {	// NOPMD
	// ~ Static Fields =======================================
	/** Metadata Builder Pool **/
	ConcurrentMap<String, Builder> POOL_BUILDER = new ConcurrentHashMap<>();
	/** Schema Importer Pool **/
	ConcurrentMap<String, Importer> POOL_IMPORTER = new ConcurrentHashMap<>();
	/** JDBC的Context的延迟池化技术 **/
	ConcurrentMap<String, JdbcContext> POOL_JDBC = new ConcurrentHashMap<>();
	/** 数据源的Pool **/
	ConcurrentMap<String, DataSource> POOL_DATA_SOURCE = new ConcurrentHashMap<>();
	/** Schema的缓存信息 **/
	ConcurrentMap<String, GenericSchema> POOL_SCHEMA = new ConcurrentHashMap<>();
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
