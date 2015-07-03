package com.prayer.meta.schema.json;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.AttrJsonTypeException;
import com.prayer.exception.schema.AttrZeroException;
import com.prayer.exception.schema.DuplicatedAttrException;
import com.prayer.exception.schema.DuplicatedColumnException;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
final class JArrayValidator {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JArrayValidator.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotBlank
	@NotEmpty
	private transient String name; // NOPMD
	/** **/
	@NotNull
	private transient ArrayNode verifiedNodes;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public JArrayValidator(@NotNull final ArrayNode verifiedNodes) {
		this(verifiedNodes, null);
	}

	/** **/
	@PostValidateThis
	public JArrayValidator(@NotNull final ArrayNode verifiedNodes,
			@NotEmpty @NotBlank final String name) {
		this.verifiedNodes = verifiedNodes;
		if (null == name) {
			this.name = "ROOT";
		} else {
			this.name = name;
		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * -10006：当前属性节点长度为0异常
	 * 
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyZero() {
		AbstractSchemaException retExp = null;
		if (this.verifiedNodes.size() <= 0) {
			retExp = new AttrZeroException(getClass(), this.name);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10006 ( Location: " + this.name
						+ " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10002：每个元素必须是JsonObject类型
	 * 
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyJObjectNodes() {
		AbstractSchemaException retExp = null;
		final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
		while (nodeIt.hasNext()) {
			final JsonNode item = nodeIt.next();
			if (!item.isContainerNode() || item.isArray()) {
				retExp = new AttrJsonTypeException(getClass(), "value = " // NOPMD
						+ item.toString());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(
							"[E] ==> Error-10002 ( Location: "
									+ item.toString() + " )", retExp);
				}
				break;
			}
		}
		return retExp;
	}

	/**
	 * -10007：重名属性验证
	 * 
	 * @param attr
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyDuplicated(
			@NotNull @NotBlank @NotEmpty final String attr) {
		final Set<String> setValues = new HashSet<>();
		final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
		String attrExpStr = null;
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final String value = node.path(attr).asText();
			final int start = setValues.size();
			setValues.add(value);
			if(start == setValues.size()){
				attrExpStr = value;
				break;
			}
		}

		AbstractSchemaException retExp = null;
		if (setValues.size() < this.verifiedNodes.size()) {
			// 特殊判断，主要针对columnName字段的信息
			if (attr.equals(Attributes.F_COL_NAME)) {
				retExp = new DuplicatedColumnException(getClass(), attrExpStr);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[E] ==> Error-10008 ( Location: " + this.name
							+ " -> " + attr + ")", retExp);
				}
			} else {
				retExp = new DuplicatedAttrException(getClass(), attrExpStr);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[E] ==> Error-10007 ( Location: " + this.name
							+ " -> " + attr + ")", retExp);
				}
			}
		}

		return retExp;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
