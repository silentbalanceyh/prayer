package com.prayer.util.resource;

import static com.prayer.util.debug.Log.jvmError;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.model.cache.Cache;
import com.prayer.util.io.IOKit;
import com.prayer.util.reflection.Instance;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/** **/
@Guarded
public final class DatumLoader {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DatumLoader.class);

    /** 顶级Cache，Global直接使用 **/
    private static final Cache GLOBAL_CACHE;
    /** 使用的缓存策略 **/
    private static final String CACHE_CLS;
    /** 顶级配置文件 **/
    private static final Properties KERNAL_LOADER;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 系统顶级配置文件 **/
    static {
        /** 1.初始化系统顶级Loader **/
        KERNAL_LOADER = new Properties();
        try {
            final InputStream in = IOKit.getFile("/kernel.properties");
            if (null != in) {
                KERNAL_LOADER.load(in);
            }
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
        }
        /** 2.初始化全局缓存 **/
        {
            final String injectFile = KERNAL_LOADER.getProperty("file.injection.point");
            final Properties cacheLoader = new Properties();
            try {
                final InputStream in = IOKit.getFile(injectFile);
                if (null != in) {
                    cacheLoader.load(in);
                }
            } catch (IOException ex) {
                jvmError(LOGGER, ex);
            }
            CACHE_CLS = cacheLoader.getProperty("cache.manager");
            GLOBAL_CACHE = Instance.instance(CACHE_CLS);
        }
    }

    // ~ Static Methods ======================================
    /**
     * 获取系统级的Properties
     * 
     * @return
     */
    public static Properties getLoader() {
        return KERNAL_LOADER;
    }
    /**
     * 读取全局Cache
     * @return
     */
    public static Cache getSystemCache(){
        return GLOBAL_CACHE;
    }
    /**
     * 根据resource路径读取Properties
     * 
     * @param resource
     * @return
     */
    public static Properties getLoader(@NotNull @NotEmpty @NotBlank final String resource) {
        /** 1.获取当前缓存 **/
        final Cache cache = getCache();
        /** 2.从Cache中读取Properties **/
        Properties prop = cache.get(resource);
        if (null == prop) {
            prop = new Properties();
            /** 3.初始化Properties **/
            try {
                final InputStream in = IOKit.getFile(resource);
                if (null != in) {
                    prop.load(in);
                }
            } catch (IOException ex) {
                jvmError(LOGGER, ex);
            }
            /** 4.将新的Properties添加到Cache中 **/
            cache.put(resource, prop);
        }
        return prop;
    }

    // ~ Constructors ========================================
    private DatumLoader() {
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static Cache getCache() {
        /** 1.检查特殊的Properties中的Cache **/
        Cache cache = GLOBAL_CACHE.get(Properties.class.getName());
        if (null == cache) {
            cache = Instance.instance(CACHE_CLS);
            GLOBAL_CACHE.put(Properties.class.getName(), cache);
        }
        return cache;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
