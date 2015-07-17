package com.prayer;

import static org.junit.Assert.fail;

import java.text.MessageFormat;

import org.slf4j.Logger;

import com.prayer.util.PropertyKit;

/**
 * Prayer测试框架基类，用于测试系统的基础
 * 
 * @author Lang
 *
 */
public abstract class AbstractTestTool implements ErrorKeys { // NOPMD
	// ~ Static Fields =======================================
	/**
	 * Error property loader to read Error Message
	 */
	private static PropertyKit loader = new PropertyKit(AbstractTestTool.class, "/asserts.properties");

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	/** 获取当前类的日志器 **/
	protected abstract Logger getLogger();

	/** 获取被测试类类名 **/
	protected abstract Class<?> getTarget();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 格式化消息
	 * 
	 * @param messageKey
	 * @param params
	 * @return
	 */
	protected String message(final String messageKey, final Object... params) {
		// 生成测试用例消息前缀
		final StringBuilder message = new StringBuilder();
		message.append('[').append(messageKey).append(']');
		// 是否提供了Class位置，如果没有提供则不输出Class
		final Class<?> clazz = getTarget();
		if (null != clazz) {
			message.append(" Class -> ").append(clazz.getName()).append(" |");
		}
		message.append(' ').append(MessageFormat.format(loader.getString(messageKey), params));
		return message.toString();
	}

	/**
	 * 输出Debug信息
	 * 
	 * @param logger
	 * @param messageKey
	 * @param error
	 * @param params
	 * @return
	 */
	protected void debug(final Logger logger, final String messageKey, final Throwable error, final Object... params) {
		if (logger.isDebugEnabled()) {
			if (null == error) {
				logger.debug(message(messageKey, params));
			} else {
				logger.debug(message(messageKey, params), error);
			}
		}
	}

	/**
	 * 输出Information
	 * 
	 * @param logger
	 * @param messageKey
	 * @param params
	 */
	protected void info(final Logger logger, final String messageKey, final Object... params) {
		if (logger.isInfoEnabled()) {
			logger.info(message(messageKey, params));
		}
	}

	/** 一般用于异常测试，异常如果没有抛出来则手动设置failure **/
	protected void failure(final String messageKey, final Object... params) {
		fail(message(messageKey, params));
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
