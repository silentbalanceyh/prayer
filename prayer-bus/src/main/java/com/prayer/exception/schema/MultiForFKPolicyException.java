package com.prayer.exception.schema;

import com.prayer.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10021：keys中的FK定义multi必须为false，否则冲突
 *  1.__keys__节点（Array）中的某个（Object）元素multi=false时，这个Key不可以是外键
 * @author Lang
 *
 */
public class MultiForFKPolicyException extends AbstractSchemaException {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -5506576812560745873L;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param clazz
	 * @param keyName
	 */
	public MultiForFKPolicyException(final Class<?> clazz, final String keyName){
		super(clazz, -10021, keyName);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public int getErrorCode(){
		return -10021;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
