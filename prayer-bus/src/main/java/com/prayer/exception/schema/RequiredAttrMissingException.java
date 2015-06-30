package com.prayer.exception.schema;

import static com.prayer.util.sys.Converter.toStr;

import java.util.Set;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10001：必要属性丢失异常
 * 
 * @author Lang
 * @see
 */
public class RequiredAttrMissingException extends AbstractSchemaException {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -4218763921700458889L;

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param attr
	 */
	public RequiredAttrMissingException(final Class<?> clazz, final String attr) {
		super(clazz, -10001, attr);
	}

	/**
	 * 
	 * @param clazz
	 * @param attrs
	 */
	public RequiredAttrMissingException(final Class<?> clazz,
			final String... attrs) {
		super(clazz, -10001, toStr(attrs));
	}

	/**
	 * 
	 * @param clazz
	 * @param sets
	 */
	public RequiredAttrMissingException(final Class<?> clazz,
			final Set<String> sets) {
		super(clazz, -10001, toStr(sets));
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode() {
		return -10001;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
