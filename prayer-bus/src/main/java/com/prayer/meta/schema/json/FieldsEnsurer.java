package com.prayer.meta.schema.json;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.JsonNode;
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
	/** **/
	private static final ConcurrentMap<String,String> REGEX_MAP = new ConcurrentHashMap<>();
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
	/** Put Regex **/
	static{
		REGEX_MAP.put(Attributes.F_NAME, Attributes.RGX_F_NAME);
	}
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

		if (null != this.error) {
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
		this.error = validator.verifyJObjectNodes();
		if (null != this.error) {
			return false; // NOPMD
		}
		// 14.验证__fields__字段元素
		final Iterator<JsonNode> nodeIt = this.fieldsNode.iterator();
		int count = 1;
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final JObjectValidator validator = new JObjectValidator(node, // NOPMD
					"Node: __fields__ ==> index : " + count);
			// 14.1.验证每一个__fields__中的Required元素
			this.error = validator.verifyRequired(Attributes.F_NAME,
					Attributes.F_COL_NAME, Attributes.F_COL_TYPE);
			if (null != this.error) {
				return false; // NOPMD
			}
			count++;
		}
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
