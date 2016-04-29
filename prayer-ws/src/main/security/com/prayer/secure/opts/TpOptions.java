package com.prayer.secure.opts;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @author Lang
 *
 */
public class TpOptions {
    // ~ Static Fields =======================================
    /** **/
    private static final ConcurrentMap<String,String> FIELDS = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 将第三方选项添加到MAP中 
     * @param name 第三方选项的Key引用
     * @param config 第三方选项的字段名
     */
    public void put(final String name, final String config){
        FIELDS.put(name, config);
    }
    /**
     * 读取第三方的字段名
     * @param name
     * @return
     */
    public String getField(final String name){
        return FIELDS.get(name);
    }
    
    /** 清除第三方选项 **/
    public void clear(){
        FIELDS.clear();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
