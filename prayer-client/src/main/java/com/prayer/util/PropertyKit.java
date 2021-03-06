package com.prayer.util;

import static com.prayer.util.Instance.reservoir;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.prayer.constant.MemoryPool;
import com.prayer.constant.Symbol;

import jodd.util.StringPool;
import jodd.util.StringUtil;
import net.sf.oval.constraint.Digits;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 属性文件加载器
 *
 * @author Lang
 * @see
 */
@Guarded
public final class PropertyKit {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 当前实例加载的资源文件信息
     */
    @NotNull
    private transient final Properties prop;

    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param resource
     */
    @PostValidateThis
    public PropertyKit(final Class<?> clazz, @NotNull @NotEmpty @NotBlank final String resource) {
        this.prop = reservoir(MemoryPool.POOL_PROP, resource, Properties.class);
        try {
            final InputStream inStream = IOKit.getFile(resource, clazz);
            if (null != inStream) {
                this.prop.load(inStream);
            }
        } catch (IOException ex) {
        }
        // debug(LOGGER, "SYS.KIT.PROP", prop, null == prop ? 0 :
        // prop.hashCode());
        MemoryPool.POOL_PROP.put(resource, this.prop);
        // Monitor Pool if debug
    }

    /**
     * 
     * @param resource
     */
    @PostValidateThis
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
    @Digits
    @Max(Long.MAX_VALUE)
    @Min(Long.MIN_VALUE)
    public long getLong(@NotNull @NotEmpty @NotBlank final String propKey) {
        final String orgValue = this.getString(propKey);
        long retValue = -1;
        if (null != orgValue && StringUtil.containsOnlyDigitsAndSigns(orgValue)) {
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
    @Digits
    @Max(Integer.MAX_VALUE)
    @Min(Integer.MIN_VALUE)
    public int getInt(@NotNull @NotEmpty @NotBlank final String propKey) {
        final String orgValue = this.getString(propKey);
        int retValue = -1;
        if (null != orgValue && StringUtil.containsOnlyDigitsAndSigns(orgValue)) {
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
    public String[] getArray(@NotNull @NotBlank @NotEmpty final String propKey){
        final String ret = this.getString(propKey);
        return StringUtil.split(ret,String.valueOf(Symbol.COMMA));
    }

    /**
     * 
     * @return
     */
    @NotNull
    @PreValidateThis
    public Properties getProp() {
        return this.prop;
    }

    /**
     * 
     * @param resource
     * @return
     */
    public Properties getProp(@NotNull @NotEmpty @NotBlank final String resource) {
        return MemoryPool.POOL_PROP.get(resource);
    }
    // ~ Private Methods =====================================

    // ~ hashCode,equals,toString ============================
}
