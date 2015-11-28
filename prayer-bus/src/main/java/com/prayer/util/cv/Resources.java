package com.prayer.util.cv;

import static com.prayer.util.Error.debug;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.PropertyKit;

import jodd.util.StringUtil;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Static resource loeader to read configuration
 *
 * @author Lang
 */
@Guarded
public final class Resources { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Resources.class);

    // System Global Configuration ===========================
    /** 错误信息键值 **/
    private static final String ERR_SYS_NULL = "SYS.NULL";
    /** 错误信息代码 **/
    public static final String ERR_CODE_FILE = "/errors.properties";
    /** 系统默认英文编码 **/
    public static final Charset SYS_ENCODING;

    // Database Configuration ================================
    /** 数据库模式：SQL/NOSQL **/
    public static final String DB_MODE;
    /** 数据库连接池实现类名： **/
    public static final String DB_POOL;
    /** 数据库种类：MYSQL, MSSQL, ORACLE, PGSQL, MONGO **/
    public static final String DB_CATEGORY;
    /** JDBC配置文件路径 **/
    public static final String DB_CFG_FILE;
    /** 数据库的Builder **/
    public static final String DB_BUILDER;
    /** 数据库访问Record的Dao实现 **/
    public static final String DB_DAO;
    /** 数据类型转换器 **/
    public static final String DB_TRANSDUCER;

    // Database Type Mapping =================================
    /** 数据库类型映射文件地址 **/
    public static final String DB_TYPES_FILE;
    // Open Source Configuration =============================
    /** Mybatis数据库配置文件 **/
    public static final String T_CFG_MYBATIS;
    /** Mybatis环境名称Environment **/
    public static final String T_CFG_MB_ENV;

    // Metadata SQL File Configuration =======================
    /** 保存的SQL文件地址 **/
    public static final String DB_SQL_DIR;

    // File Configuration ====================================
    /** Vertx的配置文件路径 **/
    public static final String VX_CFG_FILE;
    /** Server的配置文件路径 **/
    public static final String SEV_CFG_FILE;
    /** Meta Data的配置文件路径 **/
    public static final String META_CFG_FILE;

    /**
     * Private singleton resource LOADER. *
     */
    private static final PropertyKit LOADER;

    // ~ Static Block ========================================

    /** Static Loading */
    static {
        /**
         * 因为PropertyLoader内部已经实现了Properties的单件模式，这里没有必要使用反射的方式创建
         */
        LOADER = new PropertyKit(Resources.class, "/global.properties");
        if (null == LOADER) {
            debug(LOGGER, ERR_SYS_NULL, "LOADER", LOADER);
        }
        // Global Configuration
        SYS_ENCODING = Charset.forName(LOADER.getString("system.en.encoding"));
        if (null == SYS_ENCODING) {
            debug(LOGGER, ERR_SYS_NULL, "SYS_ENCODING", SYS_ENCODING);
        }

        // Database Configuration
        DB_MODE = null == LOADER.getString("database.mode") ? Constants.DB_MODE_SQL
                : StringUtil.toUpperCase(LOADER.getString("database.mode"));
        if (null == DB_MODE) {
            debug(LOGGER, ERR_SYS_NULL, "DB_MODE", DB_MODE);
        }
        DB_POOL = LOADER.getString("database.pool.impl");
        if (null == DB_POOL) {
            debug(LOGGER, ERR_SYS_NULL, "DB_POOL", DB_POOL);
        }
        DB_CATEGORY = null == LOADER.getString("database.category") ? "MSSQL"
                : StringUtil.toUpperCase(LOADER.getString("database.category"));
        if (null == DB_CATEGORY) {
            debug(LOGGER, ERR_SYS_NULL, "DB_CATEGORY", DB_CATEGORY);
        }
        DB_CFG_FILE = LOADER.getString("database.config.file");
        if (null == DB_CFG_FILE) {
            debug(LOGGER, ERR_SYS_NULL, "DB_CFG_FILE", DB_CFG_FILE);
        }
        DB_TYPES_FILE = LOADER.getString("database.mapping");
        if (null == DB_TYPES_FILE) {
            debug(LOGGER, ERR_SYS_NULL, "DB_TYPES_FILE", DB_TYPES_FILE);
        }
        DB_BUILDER = LOADER.getString("database.meta.builder");
        if (null == DB_BUILDER) {
            debug(LOGGER, ERR_SYS_NULL, "DB_BUILDER", DB_BUILDER);
        }
        DB_DAO = LOADER.getString("database.dao");
        if (null == DB_DAO) {
            debug(LOGGER, ERR_SYS_NULL, "DB_DAO", DB_DAO);
        }
        DB_TRANSDUCER = LOADER.getString("database.dao.transducer");
        if (null == DB_TRANSDUCER) {
            debug(LOGGER, ERR_SYS_NULL, "DB_TRANSDUCER", DB_TRANSDUCER);
        }

        // Open Source
        T_CFG_MYBATIS = LOADER.getString("mybatis.config.file");
        if (null == T_CFG_MYBATIS) {
            debug(LOGGER, ERR_SYS_NULL, "T_CFG_MYBATIS", T_CFG_MYBATIS);
        }
        T_CFG_MB_ENV = LOADER.getString("mybatis.environment");
        if (null == T_CFG_MB_ENV) {
            debug(LOGGER, ERR_SYS_NULL, "T_CFG_MB_ENV", T_CFG_MB_ENV);
        }

        // Metadata
        DB_SQL_DIR = LOADER.getString("database.sql.directory");
        if (null == DB_SQL_DIR) {
            debug(LOGGER, ERR_SYS_NULL, "DB_SQL_DIR", DB_SQL_DIR);
        }

        // Vertx
        VX_CFG_FILE = LOADER.getString("vertx.config.file");
        if (null == VX_CFG_FILE) {
            debug(LOGGER, ERR_SYS_NULL, "VX_CFG_FILE", VX_CFG_FILE);
        }
        // Server
        SEV_CFG_FILE = LOADER.getString("server.config.file");
        if (null == SEV_CFG_FILE) {
            debug(LOGGER, ERR_SYS_NULL, "SEV_CFG_FILE", SEV_CFG_FILE);
        }
        // Meta
        META_CFG_FILE = LOADER.getString("meta.config.file");
        if (null == META_CFG_FILE){
            debug(LOGGER, ERR_SYS_NULL, "META_CFG_FILE", META_CFG_FILE);
        }
    }

    // ~ Constructors ========================================

    /**
     * Private constructor to prevent created directly *
     */
    private Resources() {
    }

    // ~ Static Methods ======================================
    /**
     * 获取数据库Loader，返回值不能为NULL
     * 
     * @return
     */
    @NotNull
    @InstanceOfAny(PropertyKit.class)
    public static PropertyKit getLoader() {
        return LOADER;
    }
}
