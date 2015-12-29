package com.prayer.util.cv;

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
    private static final String DFT_DB_POOL = "com.prayer.dao.impl.jdbc.BoneCPPool";
    /** Builder 默认值 **/
    private static final String DFT_DB_BUILDER = "com.prayer.dao.impl.builder.MsSqlBuilder";
    /** Validator 默认值 **/
    private static final String DFT_DB_VALIDATOR = "com.prayer.dao.impl.builder.MsSqlValidator";
    /** Dao 默认值 **/
    private static final String DFT_DB_DAO = "com.prayer.dao.impl.std.record.MsSqlRDaoImpl";
    /** Transverter默认值 **/
    private static final String DFT_DB_TRANS = "com.prayer.dao.impl.std.record.MsSqlTransducer";
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================

    // ~ Static Methods ======================================
    /**
     * 验证器相关信息
     * 
     * @return
     */
    public static String validator() {
        return null == Resources.DB_VALIDATOR ? DFT_DB_VALIDATOR : Resources.DB_VALIDATOR;
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
