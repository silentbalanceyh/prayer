package com.prayer.meta.schema.json;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
/**
 * 
 * @author Lang
 * @see
 */
/**
 * 
 * @author Lang
 * @see
 */
/**
 * 
 * @author Lang
 * @see
 */
@Guarded
class PrimaryKeyEnsurer {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	@NotNull
	private transient ArrayNode keysNode;
	/** **/
	@NotNull @NotEmpty @NotBlank
	private transient String policy;
	
	
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
