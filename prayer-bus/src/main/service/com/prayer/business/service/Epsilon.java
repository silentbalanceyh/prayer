package com.prayer.business.service;

import static com.prayer.util.reflection.Instance.clazz;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;
import com.prayer.util.resource.DatumLoader;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Epsilon {
    // ~ Static Fields =======================================
    /** DALOR的映射 **/
    private static final ConcurrentMap<Class<?>, Class<?>> DALOR_MAPPING = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 映射配置文件 **/
    static {
        /** Dao类映射 **/
    	final String mappingFile = InceptBus.build(Point.Database.class).getString(Point.Database.DALOR);
        // final PropertyKit loader = new PropertyKit(Resources.SEV_MAPPING_FILE);
        final Properties prop = DatumLoader.getLoader(mappingFile);
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
    private Epsilon(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
