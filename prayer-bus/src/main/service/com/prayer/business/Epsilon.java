package com.prayer.business;

import static com.prayer.util.reflection.Instance.clazz;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Resources;
import com.prayer.facade.constant.Constants;
import com.prayer.util.io.PropertyKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class Epsilon {
    // ~ Static Fields =======================================
    /** DALOR的映射 **/
    private static final ConcurrentMap<Class<?>, Class<?>> DALOR_MAPPING = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 映射配置文件 **/
    static {
        /** Dao类映射 **/
        final PropertyKit loader = new PropertyKit(Resources.SEV_MAPPING_FILE);
        final Properties prop = loader.getProp();
        for (final Object key : prop.keySet()) {
            final String daoStr = key.toString();
            if (daoStr.endsWith(".dao")) {
                final String entityCls = daoStr.substring(Constants.IDX, daoStr.indexOf(".dao"));
                final String daoCls = prop.getProperty(key.toString());
                DALOR_MAPPING.put(clazz(entityCls), clazz(daoCls));
            }
        }
    }

    /** 读取Dalor **/
    public static Class<?> getDalor(@NotNull final Class<?> entityCls) {
        return DALOR_MAPPING.get(entityCls);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
