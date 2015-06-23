package com.test.prayer.util.constructor;

import static com.prayer.util.sys.Instance.clazz;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 *
 * @author Lang
 * @see
 */
public class AbstractConTestCase {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractConTestCase.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Get/Set =============================================
	// ~ Methods =============================================
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> T instance(final Class<?> clazz) {
		T ret = null;
		Constructor con;
		try {
			con = clazz.getDeclaredConstructor();
			con.setAccessible(true);
			ret = (T) con.newInstance();
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("[E] Exception -> ",e);
			}
		}
		return ret;
	}
	/**
	 * 
	 * @param className
	 * @return
	 */
	protected <T> T instance(final String className) {
		return instance(clazz(className));
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
