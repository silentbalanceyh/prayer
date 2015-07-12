package com.prayer.schema.json.internal;

import static com.prayer.util.Error.message;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.JsonKit.findNodes;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.meta.DataType;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class ForeignKeyEnsurer implements InternalEnsurer {
	// ~ Static Fields =======================================
	/** **/
	private static final ConcurrentMap<String, Object> FK_FILTER = new ConcurrentHashMap<>();
	/** **/
	private static final ConcurrentMap<String, String> REGEX_MAP = new ConcurrentHashMap<>();
	/** PK_TYPE **/
	private static final String[] PK_STR = new String[] { DataType.INT.toString(), DataType.LONG.toString(),
			DataType.STRING.toString() };
	/** **/
	private static final String RGX_REFID_CTYPE = "(INT|LONG|STRING)[0-9]*";
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	@NotNull
	private transient final ArrayNode fieldsNode;
	/** **/
	@NotNull
	private transient final JArrayValidator validator;
	// ~ Static Block ========================================

	// ~ Static Methods ======================================
	/** **/
	static {
		/** Put Filter **/
		FK_FILTER.put(Attributes.F_FK, Boolean.TRUE);
		/** Put Regex **/
		REGEX_MAP.put(Attributes.F_REF_TABLE, Attributes.RGX_F_REF_TABLE);
		REGEX_MAP.put(Attributes.F_REF_ID, Attributes.RGX_F_REF_ID);
	}

	// ~ Constructors ========================================
	/**
	 * 
	 * @param fieldsNode
	 */
	@PostValidateThis
	public ForeignKeyEnsurer(@NotNull final ArrayNode fieldsNode) {
		this.fieldsNode = fieldsNode;
		this.error = null; // NOPMD
		this.validator = new JArrayValidator(this.fieldsNode, Attributes.R_FIELDS);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Override
	public void validate() throws AbstractSchemaException {
		// 1.外键基础属性验证
		validateForeignKey();
		interrupt();
		// 2.外键类型定义验证
		validateFKType();
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

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 
	 * @return
	 */
	private boolean validateFKType() {
		// 21.1.验证外键的类型
		this.error = this.validator.verifyFkType(Attributes.F_TYPE, FK_FILTER, PK_STR);
		if (null != this.error) {
			return false;
		}
		// 21.2.验证外键的数据库列类型
		this.error = this.validator.verifyFkColumnType(Attributes.F_COL_TYPE, FK_FILTER, RGX_REFID_CTYPE);
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateForeignKey() {
		// foreignkey = true的节点一定存在，在上层验证过
		// 20.外键验证
		final List<JsonNode> fkNodes = findNodes(this.fieldsNode, FK_FILTER);
		Iterator<JsonNode> fkNIt = fkNodes.iterator();
		int idx = 0;
		while (fkNIt.hasNext()) {
			final JsonNode node = fkNIt.next();
			final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
					message("D10000.FKIDX", idx, node.path(Attributes.F_NAME).asText()));
			// new JObjectValidator(node,message("D10000.FKIDX", idx,
			// node.path(Attributes.F_NAME).asText()));
			// 20.1.验证foreignkey = true的节点必须包含两个特殊可选属性
			this.error = validator.verifyMissing(Attributes.F_REF_ID, Attributes.F_REF_TABLE);
			if (null != this.error) {
				break;
			}
			idx++;
		}
		if (null != this.error) {
			return false;
		}
		// 重置进行第二轮验证
		fkNIt = fkNodes.iterator();
		idx = 0;
		while (fkNIt.hasNext()) {
			final JsonNode node = fkNIt.next();
			final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
					message("D10000.FKIDX", idx, node.path(Attributes.F_NAME).asText()));
			// new JObjectValidator(node,message("D10000.FKIDX", idx,
			// node.path(Attributes.F_NAME).asText()));
			// 20.2.验证foreignkey = true的节点中特殊可选属性
			for (final String attr : REGEX_MAP.keySet()) {
				this.error = validator.verifyPattern(attr, REGEX_MAP.get(attr));
				if (null != this.error) {
					break;
				}
			}
			idx++;
		}
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
