package com.prayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.util.Instance;

/**
 * 
 * @author Lang
 *
 */
public final class Assistant {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(Assistant.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** 生成字符串集合 **/
	public static Set<String> set(final int size) {
		final Set<String> retSet = new HashSet<>();
		if (0 < size) {
			for (int idx = 0; idx < size; idx++) {
				retSet.add("Set" + idx);
			}
		}
		return retSet;
	}

	/** 创建一个新的实例的方法 **/
	public static <T> T instance(final Class<?> clazz, final Object... params) {
		return Instance.instance(clazz.getName(), params);
	}

	/** 创建无参数实例 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T instance(final Class<?> clazz) {
		T ret = null;
		Constructor con;
		try {
			con = clazz.getDeclaredConstructor();
			if (!con.isAccessible()) {
				con.setAccessible(true);
			}
			ret = (T) con.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException exp) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[JVM] Test Scope !", exp);
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param collection
	 * @param separator
	 * @param appendEnd
	 * @return
	 */
	public static String join(final Collection<String> collection, final char separator, final boolean appendEnd) {
		final StringBuilder retStr = new StringBuilder();
		if (Constants.ONE == collection.size()) {
			retStr.append(collection.iterator().next());
		} else {
			for (final String item : collection) {
				if (null != item) {
					retStr.append(item).append(separator);
				}
			}
			if (!appendEnd) {
				retStr.deleteCharAt(retStr.length() - 1);
			}
		}
		return retStr.toString();
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Assistant() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
