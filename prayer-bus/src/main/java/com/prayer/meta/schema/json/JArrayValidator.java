package com.prayer.meta.schema.json;

import static com.prayer.util.JsonKit.occursAttr;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jodd.util.StringUtil;
import net.sf.oval.constraint.Min;
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
import com.prayer.exception.schema.PrimaryKeyMissingException;

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

	/** **/
	private transient String table; // Optional Attribute

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
	 * 
	 * @param table
	 */
	@PreValidateThis
	public void setTable(@NotNull @NotBlank @NotEmpty final String table) {
		this.table = table;
	}

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
	 * -10010：验证主键是否丢失的函数
	 * 
	 * @param pkAttrName
	 * @param table
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyMissing(
			@NotNull @NotBlank @NotEmpty final String pkAttrName) {
		final int occurs = occursAttr(this.verifiedNodes, pkAttrName);
		AbstractSchemaException retExp = null;
		if (0 == occurs) {
			retExp = new PrimaryKeyMissingException(getClass(), this.table);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10010 For Attribute ( Location: "
						+ pkAttrName + " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10010：验证主键是否定义，函数验证一个Array中的所有Object之内： attr = value出现的次数
	 * 
	 * @param attr
	 * @param value
	 * @param minOccurs
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyAttrValue(
			@NotNull @NotBlank @NotEmpty final String attr, final Object value,
			@Min(1) final int minOccurs) {
		// Pre Condition：假设attr是存在的，即上边verifyMissing函数已经验证通过
		final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
		int occurs = 0;
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			if (null == value) {
				// null值空检测
				final String jsonValue = node.get(attr).asText();
				if (null == jsonValue) {
					occurs++;
				}
			} else {
				// 非null值检测
				final String jsonValue = node.get(attr).asText();
				if (StringUtil.equals(StringUtil.toUpperCase(jsonValue),
						StringUtil.toUpperCase(value.toString()))) {
					occurs++;
				}
			}
		}

		AbstractSchemaException retExp = null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[I] ==> minOccurs = " + minOccurs + ", occurs = "
					+ occurs);
		}
		if (minOccurs > occurs) {
			retExp = new PrimaryKeyMissingException(getClass(), this.table);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10010 For Value ( Location: "
						+ attr + " = " + value.toString() + " ) occurs: " + occurs, retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10007/-10008：重名属性验证
	 * 
	 * @param attr
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyDuplicated(
			@NotNull @NotBlank @NotEmpty final String attr) {
		// Occurs的计算不能使用，attrExpStr的计算存在，所以不可使用Occurs计算
		final Set<String> setValues = new HashSet<>();
		final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
		String attrExpStr = null;
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final String value = node.path(attr).asText();
			final int start = setValues.size();
			setValues.add(value);
			if (start == setValues.size()) {
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
