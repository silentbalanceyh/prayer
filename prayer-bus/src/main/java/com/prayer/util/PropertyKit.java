package com.prayer.util;

import static com.prayer.util.sys.Instance.reservoir;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性文件加载器
 *
 * @author Lang
 * @see
 */
@Guarded
public final class PropertyKit {
	// ~ Static Fields =======================================
	/** 资源文件池 **/
	private static final ConcurrentMap<String, Properties> PROP_POOL = new ConcurrentHashMap<>();
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PropertyKit.class);
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
	public PropertyKit(final Class<?> clazz,
			@NotNull @NotEmpty @NotBlank final String resource) {
		this.prop = reservoir(PROP_POOL, resource, Properties.class);
		try {
			final InputStream inStream = IOKit.getFile(resource, clazz);
			if (null != inStream) {
				this.prop.load(inStream);
			}
		} catch (IOException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] Construct Error! Input = " + resource, ex);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[D] <== ( prop = " + prop + ", hashCode = "
					+ ((null == prop) ? 0 : prop.hashCode())
					+ " ) Initialized current prop!");
		}
		PROP_POOL.put(resource, this.prop);
		// Monitor Pool if debug
		/*
		 * if (LOGGER.isDebugEnabled()){
		 * LOGGER.debug("[POOL] Current resource: " + resource); for(final
		 * String key: PROP_POOL.keySet()){ LOGGER.debug("[POOL] Key=" + key +
		 * ", value=" + PROP_POOL.get(key)); } }
		 */
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
		return this.getInt(propKey);
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
		if (StringUtil.isNotEmpty(ret) && StringPool.NULL.equals(ret)) {
			ret = null; // NOPMD
		}
		return ret;
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
		return PROP_POOL.get(resource);
	}
	// ~ Private Methods =====================================

	// ~ hashCode,equals,toString ============================
}
