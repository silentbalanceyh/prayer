package com.prayer.meta.schema.json;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.meta.DataType;
import com.prayer.mod.sys.SystemEnum.MetaPolicy;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
final class PrimaryKeyEnsurer implements InternalEnsurer {
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
	/** **/
	private static final ConcurrentMap<String, Object> PK_FILTER = new ConcurrentHashMap<>();
	/** GUID **/
	private static final String[] PK_GUID_STR = new String[] { DataType.STRING
			.toString() };
	/** INCREMENT **/
	private static final String[] PK_INCREMENT_STR = new String[] {
			DataType.INT.toString(), DataType.LONG.toString() };
	/** ASSIGNED, COLLECTION **/
	private static final String[] PK_STR = new String[] {
			DataType.INT.toString(), DataType.LONG.toString(),
			DataType.STRING.toString() };
	// ~ Static Methods ======================================
	/** Put Filter **/
	static {
		PK_FILTER.put(Attributes.F_PK, Boolean.TRUE);
	}

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
	@Override
	public void validate() throws AbstractSchemaException {
		// 1.验证Primary Key是否定义
		validatePKeyMissing();
		interrupt();
		// 2.policy是否COLLECTION
		validateDispatcher();
		interrupt();
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Override
	public void interrupt() throws AbstractSchemaException {
		if (null != this.error) {
			throw this.error;
		}
	}

	// ~ Private Methods =====================================

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
					policy);
			if (null != this.error) {
				return false; // NOPMD
			}
		} else {
			// 18.2.1.属性：policy != COLLECTION
			this.error = this.validator.verifyPKeyNonCOPolicy(Attributes.F_PK,
					policy);
			if (null != this.error) {
				return false; // NOPMD
			}
			if (MetaPolicy.GUID == policy) {
				// 18.2.2.1.对应：policy == GUID
				this.error = this.validator.verifyPKeyPolicyType(policy,
						PK_FILTER, PK_GUID_STR);
			} else if (MetaPolicy.INCREMENT == policy) {
				// 18.2.2.2.对应：policy == INCREMENT
				this.error = this.validator.verifyPKeyPolicyType(policy,
						PK_FILTER, PK_INCREMENT_STR);
			}
			if (null != this.error) {
				return false; // NOPMD
			}
		}
		// 18.1.2.policy == COLLECTION
		// 18.2.2.3.policy == ASSIGNED
		if ((MetaPolicy.ASSIGNED == policy || MetaPolicy.COLLECTION == policy)
				&& null == this.error) {
			this.error = this.validator.verifyPKeyPolicyType(policy, PK_FILTER,
					PK_STR);
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validatePKeyMissing() {
		// // 17.1.验证主键是否存在：primarykey
		// this.error = this.validator.verifyPKeyRequired(Attributes.F_PK);
		// if (null != this.error) {
		// return false; // NOPMD
		// }
		// 17.2.验证主键定义primarykey的值是不是至少有一个为true
		this.error = this.validator.verifyPKeyMissing(Attributes.F_PK,
				Boolean.TRUE, 1);
		if (null != this.error) {
			return false; // NOPMD
		}
		return null == this.error;
	}

	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
