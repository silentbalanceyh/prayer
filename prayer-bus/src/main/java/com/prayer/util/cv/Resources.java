package com.prayer.util.cv;

import java.nio.charset.Charset;

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

    // System Global Configuration ===========================
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
    /** SQL Error的Mapping映射文件 **/
    public static final String DB_SQL_ERROR;

    // File Configuration ====================================
    /** Vertx的配置文件路径 **/
    public static final String VX_CFG_FILE;
    /** Server的配置文件路径 **/
    public static final String SEV_CFG_FILE;
    /** Meta Data的配置文件路径 **/
    public static final String META_CFG_FILE;
    /** Meta Data的OOB数据默认路径 **/
    public static final String META_OD_FOLDER;
    /** 日志配置文件存储的根目录 **/
    public static final String LOG_CFG_FOLDER;

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

        // Database Configuration
        DB_MODE = null == LOADER.getString("database.mode") ? Constants.DB_MODE_SQL
                : StringUtil.toUpperCase(LOADER.getString("database.mode"));

        DB_POOL = LOADER.getString("database.pool.impl");

        DB_CATEGORY = null == LOADER.getString("database.category") ? "MSSQL"
                : StringUtil.toUpperCase(LOADER.getString("database.category"));

        DB_CFG_FILE = LOADER.getString("database.config.file");

        DB_TYPES_FILE = LOADER.getString("database.mapping");

        DB_BUILDER = LOADER.getString("database.meta.builder");

        DB_DAO = LOADER.getString("database.dao");

        DB_TRANSDUCER = LOADER.getString("database.dao.transducer");

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

        // Meta
        META_CFG_FILE = LOADER.getString("meta.config.file");

        // Meta
        META_OD_FOLDER = LOADER.getString("meta.oob.data.folder");

        // Log Folder
        LOG_CFG_FOLDER = LOADER.getString("system.log.folder");
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
