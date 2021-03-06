package com.prayer.constant;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import com.prayer.base.dao.AbstractDbPool;
import com.prayer.bus.impl.std.ServiceHelper;
import com.prayer.facade.dao.Builder;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.schema.DataValidator;
import com.prayer.facade.schema.Importer;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.model.kernel.MetaConnector;

/**
 * 静态变量统一管理
 * @author Lang
 *
 */
public interface MemoryPool {    // NOPMD
    // ~ Static Fields =======================================
    /** Metadata Builder Pool **/
    ConcurrentMap<String, Builder> POOL_BUILDER = new ConcurrentHashMap<>();
    /** Schema Importer Pool **/
    ConcurrentMap<String, Importer> POOL_IMPORTER = new ConcurrentHashMap<>();
    /** 连接池，用于传统数据库和H2之间的切换 **/
    ConcurrentMap<String, AbstractDbPool> POOL_CONPOOL = new ConcurrentHashMap<>();
    /** JDBC的Context的延迟池化技术 **/
    ConcurrentMap<String, JdbcContext> POOL_JDBC = new ConcurrentHashMap<>();
    /** 和Business Database联合验证 **/
    ConcurrentMap<String, DataValidator> POOL_VALIDATOR = new ConcurrentHashMap<>();
    /** 数据源的Pool **/
    ConcurrentMap<String, DataSource> POOL_DATA_SOURCE = new ConcurrentHashMap<>();
    /** Schema的缓存信息 **/
    ConcurrentMap<String, GenericSchema> POOL_SCHEMA = new ConcurrentHashMap<>();
    /** KEY -> POOL 全局单例模式 **/
    ConcurrentMap<String, Object> POOL_OBJECT = new ConcurrentHashMap<>();
    /** 资源文件池 **/
    ConcurrentMap<String, Properties> POOL_PROP = new ConcurrentHashMap<>();
    /** Metadata Connector 全局单例模式 **/
    ConcurrentMap<String, MetaConnector> POOL_CONNECTOR = new ConcurrentHashMap<>();
    /** Service Helper 全局单例模式 **/
    ConcurrentMap<String, ServiceHelper> POOL_SEV_HELPER = new ConcurrentHashMap<>();
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
