package com.prayer.dao.impl.builder;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.util.io.PropertyKit;
import com.prayer.util.string.StringKit;

import jodd.util.StringUtil;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class SqlTypes {
    // ~ Static Fields =======================================
    /** 数据库类型映射 **/
    private static final ConcurrentMap<String,String> DB_TYPES = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 初始化数据类型映射表，直接根据Database填充 **/
    static {
        final PropertyKit loader = new PropertyKit(Resources.DB_TYPES_FILE);
        final Properties prop = loader.getProp();
        for (final Object key : prop.keySet()) {
            if (null != key && StringKit.isNonNil(key.toString())) {
                final String keyStr = key.toString();
                final String[] keys = keyStr.split("\\.");
                if (Constants.TWO == keys.length && StringUtil.equals(keys[0], Resources.DB_CATEGORY)
                        && StringKit.isNonNil(keys[1])) {
                    DB_TYPES.put(keys[1], prop.getProperty(keyStr));
                }
            }
        }
    }
    // ~ Static Methods ======================================
    /**
     * 从系统中读取类型信息
     * @param key
     * @return
     */
    public static String get(@NotNull @NotEmpty @NotBlank final Object key){
        return DB_TYPES.get(key);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private SqlTypes(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
