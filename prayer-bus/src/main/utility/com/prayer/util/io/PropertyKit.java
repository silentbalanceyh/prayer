package com.prayer.util.io;

import static com.prayer.util.debug.Log.jvmError;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.model.cache.Cache;
import com.prayer.util.reflection.Instance;
import com.prayer.util.string.StringKit;
import com.prayer.util.string.StringPool;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 属性文件加载器
 *
 * @author Lang
 * @see
 */
@Guarded
@Deprecated
public final class PropertyKit {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyKit.class);

    /** 顶级Cache，Global直接使用 **/
    private static final Cache GLOBAL_CACHE;
    /** Global Loader **/
    private static final Properties LOADER;
    /** 仅仅内部使用的Cache操作 **/
    static {
        LOADER = new Properties();
        try {
            final InputStream in = IOKit.getFile(Resources.SYS_GLOBAL_CFG, PropertyKit.class);
            if (null != in) {
                LOADER.load(in);
            }
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
        }
        GLOBAL_CACHE = Instance.instance(LOADER.getProperty("system.cache.manager"));
    }
    // ~ Error Key Constants =================================
    // ~ Instance Fields =====================================
    /**
     * 当前实例加载的资源文件信息
     */
    @NotNull
    private transient Properties prop;
    // ~ Debug Information ===================================

    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param resource
     */
    @PostValidateThis
    public PropertyKit(final Class<?> clazz, @NotNull @NotEmpty @NotBlank final String resource) {
        this.prop = this.get(resource);
        try {
            final InputStream inStream = IOKit.getFile(resource, clazz);
            if (null != inStream) {
                this.prop.load(inStream);
            }
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
        }
    }

    /**
     * 
     * @param resource
     */
    // @PostValidateThis 因为OVAL的特殊属性，不可以使用PostValidateThis，这里使用了会抛异常
    public PropertyKit(@NotNull @NotEmpty @NotBlank final String resource) {
        this(null, resource);
    }

    // ~ Methods =============================================
    /**
     * 根据属性key获取Long属性值
     * 
     * @param propKey
     * @return
     */
    @Min(-1)
    public long getLong(@NotNull @NotEmpty @NotBlank final String propKey) {
        final String orgValue = this.getString(propKey);
        long retValue = -1;
        if (null != orgValue && StringKit.digitsAndSigns(orgValue)) {
            retValue = Long.parseLong(orgValue.trim());
        }
        return retValue;
    }

    /**
     * 根据属性key获取Integer属性值
     * 
     * @param propKey
     * @return
     */
    @Min(-1)
    public int getInt(@NotNull @NotEmpty @NotBlank final String propKey) {
        final String orgValue = this.getString(propKey);
        int retValue = -1;
        if (null != orgValue && StringKit.digitsAndSigns(orgValue)) {
            retValue = Integer.parseInt(orgValue.trim());
        }
        return retValue;
    }

    /**
     * 根据属性key获取Boolean属性值
     * 
     * @param propKey
     * @return
     */
    public boolean getBoolean(@NotNull @NotEmpty @NotBlank final String propKey) {
        final String retValue = this.getString(propKey);
        boolean ret = false;
        if (null != retValue) {
            ret = Boolean.parseBoolean(retValue.trim());
        }
        return ret;
    }

    /**
     * 根据属性Key获取String属性值
     * 
     * @param propKey
     * @return
     */
    public String getString(@NotNull @NotEmpty @NotBlank final String propKey) {
        // 过滤值null
        String ret = this.getProp().getProperty(propKey);
        if (StringKit.isNonNil(ret) && StringPool.NULL.equals(ret)) {
            ret = null; // NOPMD
        }
        return ret;
    }

    /**
     * 
     * @param propKey
     * @return
     */
    @NotNull
    public String[] getArray(@NotNull @NotBlank @NotEmpty final String propKey) {
        final String ret = this.getString(propKey);
        return StringKit.split(ret, String.valueOf(Symbol.COMMA));
    }

    /**
     * 
     * @return
     */
    @NotNull
    @Pre(expr = "_this.prop != null", lang = Constants.LANG_GROOVY)
    public Properties getProp() {
        return this.prop;
    }

    /**
     * 
     * @param resource
     * @return
     */
    @NotNull
    public Properties getProp(@NotNull @NotEmpty @NotBlank final String resource) {
        return this.get(resource);
    }

    // ~ Private Methods =====================================
    private Properties get(final String resource) {
        /** 1.检索特殊的Properties中的Cache **/
        Cache cache = GLOBAL_CACHE.get(Properties.class.getName());
        if (null == cache) {
            cache = Instance.instance(LOADER.getProperty("system.cache.manager"));
            GLOBAL_CACHE.put(Properties.class.getName(), cache);
        }
        /** 2.从Cache中读取Properties **/
        Properties prop = cache.get(resource);
        if (null == prop) {
            prop = new Properties();
            cache.put(resource, prop);
        }
        return prop;
    }
    // ~ hashCode,equals,toString ============================
}
