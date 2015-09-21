package com.prayer.constant;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * 静态变量统一管理
 * @author Lang
 *
 */
public interface MemoryPool {    // NOPMD
    // ~ Static Fields =======================================
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
