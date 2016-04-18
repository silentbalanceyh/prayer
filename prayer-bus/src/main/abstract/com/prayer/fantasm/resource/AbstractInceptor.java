package com.prayer.fantasm.resource;

import static com.prayer.util.reflection.Instance.clazz;

import java.util.Properties;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.resource.Inceptor;
import com.prayer.util.resource.DatumLoader;
import com.prayer.util.string.StringKit;
import com.prayer.util.string.StringPool;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 用于读取配置的抽象类
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractInceptor implements Inceptor {
    // ~ Static Fields =======================================
    /** 1.获取全局配置 **/
    private static final Properties GLOBAL = DatumLoader.getLoader();
    // ~ Instance Fields =====================================
    /** 2.当前实例中的配置 **/
    @NotNull
    private transient final Properties LOADER;
    /** 3.读取File路径 **/
    @NotNull
    private transient String file;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 传入Global中的Key值，初始化LOADER
     * 
     * @param fileKey
     */
    @PostValidateThis
    public AbstractInceptor(@NotNull @NotBlank @NotEmpty final String fileKey) {
        this.file = GLOBAL.getProperty(fileKey);
        /** 1.未提供KEY **/
        if (this.file == null) {
            this.file = fileKey;
        }
        this.LOADER = DatumLoader.getLoader(file);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @param key
     * @return
     */
    @Override
    public Class<?> getClass(@NotNull @NotBlank @NotEmpty final String key) {
        final String value = this.getString(key);
        Class<?> ret = null;
        if (StringKit.isNonNil(value)) {
            ret = clazz(value);
        }
        return ret;
    }

    /**
     * 
     * @param key
     * @return
     */
    @Override
    public String[] getArray(@NotNull @NotBlank @NotEmpty final String key) {
        final String value = this.getString(key);
        String[] ret = new String[] {};
        if (StringKit.isNonNil(value)) {
            ret = StringKit.split(value, String.valueOf(Symbol.COMMA));
        }
        return ret;
    }

    /**
     * 
     */
    @Override
    @Min(-1)
    public int getInt(@NotNull @NotBlank @NotEmpty final String key) {
        final String value = this.getString(key);
        int ret = Constants.RANGE;
        if (StringKit.isNonNil(value) && StringKit.digitsAndSigns(value)) {
            ret = Integer.parseInt(value.trim());
        }
        return ret;
    }

    /**
     * 
     * @param key
     * @return
     */
    @Override
    @Min(-1)
    public long getLong(@NotNull @NotBlank @NotEmpty final String key) {
        final String value = this.getString(key);
        long ret = Constants.RANGE;
        if (StringKit.isNonNil(value) && StringKit.digitsAndSigns(value)) {
            ret = Long.parseLong(value.trim());
        }
        return ret;
    }

    /**
     * 
     */
    @Override
    public boolean getBoolean(@NotNull @NotBlank @NotEmpty final String key) {
        final String value = this.getString(key);
        boolean ret = false;
        if (StringKit.isNonNil(value)) {
            ret = Boolean.parseBoolean(value.trim());
        }
        return ret;
    }

    /**
     * 
     */
    @Override
    public String getString(@NotNull @NotBlank @NotEmpty final String key) {
        String ret = this.LOADER.getProperty(key);
        /** 1.过滤掉String字面量为null **/
        if (StringKit.isNonNil(ret) && StringPool.NULL.equals(ret)) {
            ret = null;
        }
        return ret;
    }

    /**
     * 
     */
    @Override
    public String getFile() {
        return this.file;
    }

    // ~ Methods =============================================
    /**
     * 只有子类可调用的方法
     * 
     * @return
     */
    protected Properties getLoader() {
        return this.LOADER;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
