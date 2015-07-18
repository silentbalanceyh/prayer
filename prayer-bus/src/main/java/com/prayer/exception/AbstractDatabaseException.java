package com.prayer.exception;

import static com.prayer.util.Error.error;

/**
 * Builder处理元数据过程的抽象异常类，在生成数据库信息时候出现异常
 * @author Lang
 *
 */
public abstract class AbstractDatabaseException extends AbstractException{
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -3587573542243637734L;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractDatabaseException(final String message){
		super(message);
	}
	/**
	 * 
	 * @param clazz
	 * @param errorCode
	 * @param params
	 */
	public AbstractDatabaseException(final Class<?> clazz, final int errorCode, final Object... params) {
		this(error(clazz, errorCode, params));
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
