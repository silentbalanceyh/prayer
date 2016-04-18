package com.prayer.constant;

import static com.prayer.util.Planar.flat;

import java.nio.charset.Charset;

import com.prayer.facade.constant.DBConstants;
import com.prayer.util.io.PropertyKit;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Static resource loeader to read configuration
 *
 * @author Lang
 */
@Guarded
@Deprecated
public final class Resources { // NOPMD
    // ~ Static Fields =======================================

    // System Global Configuration ===========================
    /** 错误信息代码 **/
    public static final String ERR_CODE_FILE = "/errors.properties";
    /** 全局配置文件 **/
    public static final String SYS_GLOBAL_CFG = "/global.properties";
    /** 系统默认英文编码 **/
    public static final Charset SYS_ENCODING;
    /** 系统使用的缓存信息 **/
    public static final String SYS_CACHE_CLS;
    /** 系统的Schema对应的Rules的文件夹 **/
    public static final String SYS_RULES;

    // Database Configuration ================================
    /** 数据库模式：INIT/NOSQL **/
    public static final String DB_MODE;
    /** 元数据数据库模式：INIT/NOSQL **/
    public static final String META_MODE;

    /** 数据库种类：MYSQL, MSSQL, ORACLE, PGSQL, MONGO **/
    public static final String DB_CATEGORY;
    /** JDBC配置文件路径 **/
    public static final String DB_CFG_FILE;

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

    // Metadata INIT File Configuration =======================
    /** 保存的SQL文件地址 **/
    public static final String DB_SQL_DIR;
    /** INIT Error的Mapping映射文件 **/
    public static final String DB_SQL_ERROR;

    // Metadata Configuration ================================

    /** Meta Data的配置文件路径 **/
    public static final String OOB_SCHEMA_FILE;
    /** Meta Data的数据库类型SQL/NOSQL **/
    public static final String META_CATEGORY;
    /** Meta Data的数据库JDBC连接 **/
    public static final String META_JDBC_CONNECTION;

    // File Configuration ====================================

    /** Meta Data的OOB数据默认路径 **/
    public static final String OOB_DATA_FOLDER;
    /** Vertx的配置文件路径 **/
    public static final String VX_CFG_FILE;
    /** Server的配置文件路径 **/
    public static final String SEV_CFG_FILE;
    /** Service中的映射文件路径 **/
    public static final String SEV_MAPPING_FILE;
    /** 日志配置文件存储的根目录 **/
    public static final String LOG_CFG_FOLDER;
    /** 安全配置文件路径 **/
    public static final String SEC_CFG_FILE;
    /** Web Config配置文件路径 **/
    public static final String WEB_CFG_FILE;
    /** Database数据库名称 **/
    public static final String DB_DATABASE;

    /** 默认不是从CONSOLE启动 **/
    public static boolean useConsole = Boolean.FALSE;

    // Switch Configuration ==================================
    /** 数据库连接池实现类名： **/
    public static final String DB_POOL;
    /** 底层元数据的实现访问器 **/
    public static final String META_ACCESSOR;

    // 可配置的数据库组件 ====================================
    /** 数据库的Builder **/
    public static final String DB_BUILDER;
    /** 数据库访问Record的Dao实现 **/
    public static final String DB_DAO;
    /** 数据类型转换器 **/
    public static final String DB_TRANSDUCER;
    /** Builder过程的关于Target数据库的验证 **/
    public static final String DB_VALIDATOR;
    /** 从业务数据库读取元数据访问器 **/
    public static final String DB_DATABASE_DAO;

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
        LOADER = new PropertyKit(Resources.class, SYS_GLOBAL_CFG);

        // Global Configuration
        SYS_ENCODING = Charset.forName(LOADER.getString("system.en.encoding"));
        // Cache
        SYS_CACHE_CLS = LOADER.getString("system.cache.manager");
        // Schema Rules
        SYS_RULES = LOADER.getString("system.schema.rules");
        /**
         * 数据库模式，默认为SQL模式，也可以为NOSQL模式
         */
        DB_MODE = flat(LOADER.getString("database.mode"), DBConstants.MODE_SQL, StringKit::upper);
        /**
         * 元数据数据库模式，默认为SQL模式，也可以为NOSQL模式
         */
        // Metadata Configuration
        META_MODE = flat(LOADER.getString("metadata.mode"), DBConstants.MODE_SQL, StringKit::upper);

        DB_CATEGORY = flat(LOADER.getString("database.category"), DBConstants.CATEGORY_MSSQL, StringKit::upper);

        META_CATEGORY = flat(LOADER.getString("meta.category"), DBConstants.CATEGORY_H2, StringKit::upper);

        DB_CFG_FILE = LOADER.getString("database.config.file");

        DB_TYPES_FILE = LOADER.getString("database.mapping");

        DB_V_ENABLED = LOADER.getBoolean("database.validation.skip");

        // =================================================================
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
        
        // Service Mapping File
        SEV_MAPPING_FILE = LOADER.getString("service.dao.mapping");

        // =================== Meta JDBC =======================
        // JDBC Connection for Meta
        META_JDBC_CONNECTION = LOADER.getString("meta.jdbc.connection");
        // =================== Switcher ========================
        // Jdbc Database Pool
        DB_POOL = LOADER.getString("database.pool.impl");
        // Meta Accessor Implementation
        META_ACCESSOR = LOADER.getString("meta.accessor.impl");
        // =====================================================
        DB_BUILDER = LOADER.getString("database.meta.builder");

        DB_VALIDATOR = LOADER.getString("database.meta.validator");

        DB_DAO = LOADER.getString("database.dao");

        DB_TRANSDUCER = LOADER.getString("database.dao.transducer");

        DB_DATABASE_DAO = LOADER.getString("database.meta.databaser");
        // =====================================================
        // Meta Initializer Implementation
        final PropertyKit DB_LOADER = new PropertyKit(Resources.DB_CFG_FILE);
        DB_DATABASE = DB_LOADER.getString(DB_CATEGORY + ".jdbc.database.name");
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
