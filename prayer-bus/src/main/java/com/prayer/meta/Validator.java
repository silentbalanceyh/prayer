package com.prayer.meta;

/**
 * 格式校验接口
 *
 * @author Lang
 * @see
 */
public interface Validator {
	/**
	 * 校验字符串格式
	 * 
	 * @return
	 */
	boolean validate(String value);
}
