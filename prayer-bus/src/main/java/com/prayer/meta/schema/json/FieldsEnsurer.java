package com.prayer.meta.schema.json;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
final class FieldsEnsurer {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	@NotNull
	private transient ArrayNode fieldsNode;
	/** **/
	@NotNull
	private transient final JArrayValidator validator;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param fieldsNode
	 */
	@PostValidateThis
	public FieldsEnsurer(@NotNull final ArrayNode fieldsNode) {
		this.fieldsNode = fieldsNode;
		this.error = null; // NOPMD
		this.validator = new JArrayValidator(this.fieldsNode,
				Attributes.R_FIELDS);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	public void validate() throws AbstractSchemaException {
		// 1.验证Zero长度异常
		boolean ret = validateFieldsAttr();
		
		if( null != this.error){
			throw this.error;
		}
	}

	/**
	 * 
	 * @param fieldsNode
	 */
	public void setFieldsNode(@NotNull final ArrayNode fieldsNode) {
		this.fieldsNode = fieldsNode;
	}

	// ~ Private Methods =====================================
	/**
	 * 
	 * @return
	 */
	private boolean validateFieldsAttr() {
		// 12.验证Zero长度异常的属性节点
		this.error = validator.verifyZero();
		if (null != this.error) {
			return false; // NOPMD
		}
		// 13.验证是否所有元素都为JsonObject
		this.error = validator.verifyPojoNodes();
		if( null != this.error){
			return false; // NOPMD
		}
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
