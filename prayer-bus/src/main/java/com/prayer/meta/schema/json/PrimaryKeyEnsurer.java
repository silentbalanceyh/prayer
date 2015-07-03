package com.prayer.meta.schema.json;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
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
	private transient ArrayNode fieldsNode;
	/** **/
	@NotNull
	@NotEmpty
	@NotBlank
	private transient String policy;
	/** **/
	@NotNull
	@NotEmpty
	@NotBlank
	private transient String table;
	/** **/
	@NotNull
	private transient final JArrayValidator validator;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param fieldsNode
	 * @param policy
	 */
	@PostValidateThis
	public PrimaryKeyEnsurer(@NotNull final ArrayNode fieldsNode,
			@NotNull @NotBlank @NotEmpty final String policy,
			@NotNull @NotBlank @NotEmpty final String table) {
		this.fieldsNode = fieldsNode;
		this.policy = policy;
		this.table = table;
		this.validator = new JArrayValidator(this.fieldsNode,
				Attributes.R_FIELDS);
		// 数据表Inject过程
		this.validator.setTable(table);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	public void validate() throws AbstractSchemaException {
		// 1.验证Primary Key是否定义
		validatePKeyMissing();
		interrupt();

	}

	// ~ Private Methods =====================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	private void interrupt() throws AbstractSchemaException {
		if (null != this.error) {
			throw this.error;
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean validatePKeyMissing() {
		// 17.1.验证主键是否存在：primarykey
		this.error = this.validator.verifyMissing(Attributes.F_PK);
		if (null != this.error) {
			return false; // NOPMD
		}
		// 17.2.验证主键定义primarykey的值是不是至少有一个为true
		this.error = this.validator.verifyAttrValue(Attributes.F_PK,
				Boolean.TRUE, 1);
		if (null != this.error) {
			return false; // NOPMD
		}
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
