package com.prayer.meta.schema.json;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.mod.sys.SystemEnum.MetaPolicy;

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
	private transient ArrayNode fieldsNode; // NOPMD
	/** **/
	@NotNull
	@NotEmpty
	@NotBlank
	private transient String policyStr; // NOPMD
	/** **/
	@NotNull
	@NotEmpty
	@NotBlank
	private transient String table; // NOPMD
	/** **/
	@NotNull
	private transient final JArrayValidator validator;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param fieldsNode
	 * @param policyStr
	 */
	@PostValidateThis
	public PrimaryKeyEnsurer(@NotNull final ArrayNode fieldsNode,
			@NotNull @NotBlank @NotEmpty final String policyStr,
			@NotNull @NotBlank @NotEmpty final String table) {
		this.fieldsNode = fieldsNode;
		this.policyStr = policyStr;
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
		// 2.policy是否COLLECTION
		validateDispatcher();
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
	private boolean validateDispatcher() {
		final MetaPolicy policy = MetaPolicy.valueOf(policyStr);
		// 18.属性policy顶层检查，是否COLLECTION
		if (MetaPolicy.COLLECTION == policy) {
			// 18.1.1.属性：policy == COLLECTION
			this.error = this.validator.verifyPKeyCOPolicy(Attributes.F_PK,
					policy.toString());
			if (null != this.error) {
				return false; // NOPMD
			}
		} else {
			// 18.2.1.属性：policy != COLLECTION
			this.error = this.validator.verifyPKeyNonCOPolicy(Attributes.F_PK,
					policy.toString());
			if (null != this.error) {
				return false; // NOPMD
			}
		}
		return null == this.error;
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
		this.error = this.validator.verifyPKeyMissing(Attributes.F_PK);
		if (null != this.error) {
			return false; // NOPMD
		}
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
