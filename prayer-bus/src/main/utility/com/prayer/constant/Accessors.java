package com.prayer.constant;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 数据库配置工具类
 *
 * @author Lang
 * @see
 */
@Guarded
public final class Accessors {
    // ~ Static Fields =======================================
    /** 默认值 **/
    private static final String DFT_DB_POOL = "com.prayer.database.pool.impl.jdbc.BoneCPPool";
    /** 默认的Accessor使用SQL **/
    private static final String DFT_META_ACCESSOR = "com.prayer.database.accessor.impl.IBatisAccessorImpl";
    /** 默认的元数据连接 **/
    private static final String DFT_META_JDBC = "com.prayer.database.pool.impl.jdbc.H2ConnImpl";

    /** Builder 默认值 **/
    private static final String DFT_DB_BUILDER = "com.prayer.builder.mssql.MsSqlBuilder";
    /** Validator 默认值 **/
    private static final String DFT_DB_VALIDATOR = "com.prayer.builder.mssql.part.MsSqlValidator";
    /** Databaser 默认值 **/
    private static final String DFT_DB_DATABASER = "com.prayer.dao.impl.data.special.MsSqlDatabaseDalor";

    /** Dao 默认值 **/
    private static final String DFT_DB_DAO = "com.prayer.dao.impl.data.special.MsSqlDataDalor";
    /** Transverter默认值 **/
    private static final String DFT_DB_TRANS = "com.prayer.dao.impl.data.special.MsSqlTransducer";

    /** Cache默认值 **/
    private static final String DFT_SYS_CACHE = "com.prayer.model.cache.HazelcastManager";
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================

    // ~ Static Methods ======================================
    /**
     * 读取数据库访问器
     * 
     * @return
     */
    @NotNull
    public static String databaser() {
        return null == Resources.DB_DATABASE_DAO ? DFT_DB_DATABASER : Resources.DB_DATABASE_DAO;
    }

    /**
     * 获取连接池实现方式
     * 
     * @return
     */
    @NotNull
    public static String connection() {
        return null == Resources.META_JDBC_CONNECTION ? DFT_META_JDBC : Resources.META_JDBC_CONNECTION;
    }

    /**
     * 获取连接池实现方式
     * 
     * @return
     */
    @NotNull
    public static String pool() {
        return null == Resources.DB_POOL ? DFT_DB_POOL : Resources.DB_POOL;
    }

    /**
     * 获取元数据Accessor的实现方法
     * 
     * @return
     */
    @NotNull
    public static String accessor() {
        return null == Resources.META_ACCESSOR ? DFT_META_ACCESSOR : Resources.META_ACCESSOR;
    }

    // ~ Configuration ===================================
    /**
     * 缓存相关
     * 
     * @return
     */
    public static String cache() {
        return null == Resources.SYS_CACHE_CLS ? DFT_SYS_CACHE : Resources.SYS_CACHE_CLS;
    }

    // ~ Database Configuration =========================

    /**
     * 验证器相关信息
     * 
     * @return
     */
    public static String validator() {
        return null == Resources.DB_VALIDATOR ? DFT_DB_VALIDATOR : Resources.DB_VALIDATOR;
    }

    /**
     * 获取Builder类名
     * 
     * @return
     */
    @NotNull
    public static String builder() {
        return null == Resources.DB_BUILDER ? DFT_DB_BUILDER : Resources.DB_BUILDER;
    }

    /**
     * 获取Dao类实现
     * 
     * @return
     */
    @NotNull
    public static String dao() {
        return null == Resources.DB_DAO ? DFT_DB_DAO : Resources.DB_DAO;
    }

    /**
     * 获取transverter实现
     * 
     * @return
     */
    @NotNull
    public static String transducer() {
        return null == Resources.DB_TRANSDUCER ? DFT_DB_TRANS : Resources.DB_TRANSDUCER;
    }

    // ~ Constructors ========================================
    private Accessors() {

    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
}
