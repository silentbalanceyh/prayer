package com.prayer.constant;

import java.nio.charset.Charset;

import com.prayer.util.io.PropertyKit;

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

    // System Global Configuration ===========================
    /** 错误信息代码 **/
    public static final String ERR_CODE_FILE = "/errors.properties";
    /** 系统默认英文编码 **/
    public static final Charset SYS_ENCODING;
    /** 系统使用的缓存信息 **/
    public static final String SYS_CACHE_CLS;

    // Database Configuration ================================
    /** 数据库模式：SQL/NOSQL **/
    public static final String DB_MODE;

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
    /** Builder过程的关于Target数据库的验证 **/
    public static final String DB_VALIDATOR;
    /** 是否跳过最底层的字段的Validation过程 **/
    public static final boolean DB_V_ENABLED;

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
    /** SQL Error的Mapping映射文件 **/
    public static final String DB_SQL_ERROR;

    // Metadata Configuration ================================

    /** Meta Data的配置文件路径 **/
    public static final String OOB_SCHEMA_FILE;
    /** Meta Data的数据库类型 **/
    public static final String META_CATEGORY;
    /** Meta Data的初始化SQL脚本位置 **/
    public static final String META_INIT_SQL;
    /** OOB Data的PurgeSQL脚本 **/
    public static final String META_PURGE_SQL;

    // File Configuration ====================================

    /** Meta Data的OOB数据默认路径 **/
    public static final String OOB_DATA_FOLDER;
    /** Vertx的配置文件路径 **/
    public static final String VX_CFG_FILE;
    /** Server的配置文件路径 **/
    public static final String SEV_CFG_FILE;
    /** 日志配置文件存储的根目录 **/
    public static final String LOG_CFG_FOLDER;
    /** 安全配置文件路径 **/
    public static final String SEC_CFG_FILE;
    /** Web Config配置文件路径 **/
    public static final String WEB_CFG_FILE;

    /** 默认不是从CONSOLE启动 **/
    public static boolean useConsole = Boolean.FALSE;

    // Switch Configuration ==================================
    /** 数据库连接池实现类名： **/
    public static final String DB_POOL;
    /** 底层元数据的实现访问器 **/
    public static final String META_ACCESSOR;
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

        // Global Configuration
        SYS_ENCODING = Charset.forName(LOADER.getString("system.en.encoding"));
        // Cache
        SYS_CACHE_CLS = LOADER.getString("system.cache.manager");

        // Database Configuration
        DB_MODE = null == LOADER.getString("database.mode") ? Constants.DB_MODE_SQL
                : StringUtil.toUpperCase(LOADER.getString("database.mode"));

        DB_CATEGORY = null == LOADER.getString("database.category") ? "MSSQL"
                : StringUtil.toUpperCase(LOADER.getString("database.category"));

        DB_CFG_FILE = LOADER.getString("database.config.file");

        DB_TYPES_FILE = LOADER.getString("database.mapping");

        DB_BUILDER = LOADER.getString("database.meta.builder");

        DB_VALIDATOR = LOADER.getString("database.meta.validator");

        DB_DAO = LOADER.getString("database.dao");

        DB_TRANSDUCER = LOADER.getString("database.dao.transducer");

        DB_V_ENABLED = LOADER.getBoolean("database.validation.skip");

        // Open Source
        T_CFG_MYBATIS = LOADER.getString("mybatis.config.file");

        T_CFG_MB_ENV = LOADER.getString("mybatis.environment");

        // Metadata
        DB_SQL_DIR = LOADER.getString("database.sql.directory");

        // Metadata
        DB_SQL_ERROR = LOADER.getString("database.sql.errors");

        // Vertx
        VX_CFG_FILE = LOADER.getString("vertx.config.file");

        // Server
        SEV_CFG_FILE = LOADER.getString("server.config.file");

        // Security
        SEC_CFG_FILE = LOADER.getString("security.config.file");

        // Web
        WEB_CFG_FILE = LOADER.getString("web.config.file");

        // Log Folder
        LOG_CFG_FOLDER = LOADER.getString("system.log.folder");

        // OOB Data Folder
        OOB_DATA_FOLDER = LOADER.getString("oob.data.folder");

        // OOB Schema File
        OOB_SCHEMA_FILE = LOADER.getString("oob.schema.file");

        // Meta
        META_CATEGORY = LOADER.getString("meta.category");

        // Meta
        META_INIT_SQL = DB_SQL_DIR + LOADER.getString("meta.init.data.file");

        // Meta
        META_PURGE_SQL = LOADER.getString("meta.oob.purge.data");
        
        // =================== Switcher ========================
        // Jdbc Database Pool
        DB_POOL = LOADER.getString("database.pool.impl");
        // Meta Accessor Implementation
        META_ACCESSOR = LOADER.getString("database.accessor.impl");
        // Meta Initializer Implementation
        
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
