package com.prayer.meta.schema.json;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class CrossEnsurer implements InternalEnsurer {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	@NotNull
	private transient final ArrayNode fieldsNode;
	/** **/
	private transient final ArrayNode keysNode;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param keysNode
	 * @param fieldsNode
	 */
	@PostValidateThis
	public CrossEnsurer(@NotNull final ArrayNode keysNode,
			@NotNull final ArrayNode fieldsNode) {
		this.keysNode = keysNode;
		this.fieldsNode = fieldsNode;
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Override
	public void validate() throws AbstractSchemaException{
		
	}
	/**
	 * 根据结果判断返回值
	 * 
	 * @param result
	 */
	@Override
	public void interrupt() throws AbstractSchemaException {
		if (null != this.error) {
			throw this.error;
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
