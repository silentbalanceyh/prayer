package com.prayer.constant;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import com.prayer.business.impl.std.ServiceHelper;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.builder.Builder;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.fantasm.pool.AbstractJdbcPool;

/**
 * 静态变量统一管理
 * 
 * @author Lang
 *
 */
public interface MemoryPool { // NOPMD
    // ~ Static Fields =======================================
    /** Accessor Pool **/
    ConcurrentMap<String, MetaAccessor> POOL_ACCESSOR = new ConcurrentHashMap<>();
    /** Record Dao用于访问元数据 **/
    ConcurrentMap<String, RecordDao> META_RDAO = new ConcurrentHashMap<>();
    /** Metadata OldBuilder Pool **/
    ConcurrentMap<String, Builder> POOL_BUILDER = new ConcurrentHashMap<>();
    /** Importer的池化操作 **/
    // ConcurrentMap<String, Importer> POOL_IMPORTER = new ConcurrentHashMap<>();
    /** 连接池，用于传统数据库和H2之间的切换 **/
    ConcurrentMap<String, AbstractJdbcPool> POOL_CONPOOL = new ConcurrentHashMap<>();
    /** JDBC的Context的延迟池化技术 **/
    ConcurrentMap<String, JdbcConnection> POOL_JDBC = new ConcurrentHashMap<>();
    /** 和Business Database联合验证 **/
    ConcurrentMap<String, DataValidator> POOL_VALIDATOR = new ConcurrentHashMap<>();
    /** 数据源的Pool **/
    ConcurrentMap<String, DataSource> POOL_DATA_SOURCE = new ConcurrentHashMap<>();
    /** Schema的缓存信息 **/
    ConcurrentMap<String, Schema> POOL_SCHEMA = new ConcurrentHashMap<>();
    /** KEY -> POOL 全局单例模式 **/
    ConcurrentMap<String, Object> POOL_OBJECT = new ConcurrentHashMap<>();
    /** 资源文件池 **/
    ConcurrentMap<String, Properties> POOL_PROP = new ConcurrentHashMap<>();
    /** Metadata Connector 全局单例模式 **/
    // ConcurrentMap<String, Meta> POOL_CONNECTOR = new ConcurrentHashMap<>();
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
