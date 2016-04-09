package com.prayer.model.crucial;

import static com.prayer.util.reflection.Instance.clazz;
import static com.prayer.util.reflection.Instance.reservoir;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Resources;
import com.prayer.facade.constant.Constants;
import com.prayer.util.io.PropertyKit;
import com.prayer.util.string.StringKit;

/**
 * 根据Metadata的类名、字段名得到对应的列名
 * 
 * @author Lang
 *
 */
public class MetaColumns {
    // ~ Static Fields =======================================
    /** 从实体类名到Identifier的映射 **/
    public static final ConcurrentMap<Class<?>, String> ENTITY_MAP = new ConcurrentHashMap<>();
    /** 元数据资源文件 **/
    private static final PropertyKit LOADER = new PropertyKit(MetaRaw.class, Resources.OOB_SCHEMA_FILE);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    static {
        /** 从属性文件中加载对应的信息 **/
        final Properties prop = LOADER.getProp();
        prop.keySet().stream().filter(item -> item.toString().endsWith(".instance")).forEach(item -> {
            final String literal = item.toString();
            final String identifier = literal.split("\\.")[Constants.IDX];
            if (StringKit.isNonNil(identifier)) {
                final Class<?> key = clazz(prop.getProperty(literal));
                if (null != key) {
                    ENTITY_MAP.put(key, identifier);
                }
            }
        });
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 通过池化单例创建MetaRaw
     * 
     * @return
     */
    public static MetaRaw reference(final Class<?> entityCls) {
        final String identifier = ENTITY_MAP.get(entityCls);
        MetaRaw raw = null;
        if (StringKit.isNonNil(identifier)) {
            raw = reservoir(identifier, MetaRaw.class, identifier);
        }
        return raw;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
