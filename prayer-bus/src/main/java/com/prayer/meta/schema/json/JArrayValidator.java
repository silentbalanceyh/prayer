package com.prayer.meta.schema.json; // NOPMD

import static com.prayer.util.JsonKit.findNodes;
import static com.prayer.util.JsonKit.isAttrIn;
import static com.prayer.util.JsonKit.occursAttr;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinLength;
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
import com.prayer.exception.schema.FKColumnTypeException;
import com.prayer.exception.schema.ForeignKeyTypeException;
import com.prayer.exception.schema.PKColumnTypePolicyException;
import com.prayer.exception.schema.PrimaryKeyMissingException;
import com.prayer.exception.schema.PrimaryKeyPolicyException;
import com.prayer.exception.schema.SubtableNotMatchException;
import com.prayer.mod.sys.SystemEnum.MetaPolicy;
import com.prayer.res.cv.Constants;

/**
 * 
 * @author Yu
 *
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
				LOGGER.debug("[ERR-10006] ==> Error-10006 ( Location: "
						+ this.name + " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10015：外键数据类型问题
	 * 
	 * @param attr
	 * @param filter
	 * @param regexStr
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyFkColumnType(
			@NotNull @NotBlank @NotEmpty final String attr,
			@NotNull final ConcurrentMap<String, Object> filter,
			@NotNull @NotBlank @NotEmpty final String regexStr) {
		AbstractSchemaException retExp = null;
		final List<JsonNode> fkNodes = findNodes(this.verifiedNodes, filter);
		final Iterator<JsonNode> fkNIt = fkNodes.iterator();
		// Regex
		final Pattern pattern = Pattern.compile(regexStr);
		while (fkNIt.hasNext()) {
			final JsonNode node = fkNIt.next();
			final String value = node.path(attr).asText();
			final Matcher matcher = pattern.matcher(value);
			if (!matcher.matches()) {
				retExp = new FKColumnTypeException(getClass(), // NOPMD
						Attributes.F_COL_TYPE);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[ERR-10015] ==> Error-10015 ( Location: "
							+ this.name + " ) pattern machting, ", retExp);
				}
			}
		}
		return retExp;
	}

	/**
	 * -10014：外键属性类型异常
	 * 
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyFkType(
			@NotNull @NotBlank @NotEmpty final String attr,
			@NotNull final ConcurrentMap<String, Object> filter,
			@MinLength(1) final String... values) {
		AbstractSchemaException retExp = null;
		final List<JsonNode> fkNodes = findNodes(this.verifiedNodes, filter);
		final Iterator<JsonNode> fkNIt = fkNodes.iterator();
		while (fkNIt.hasNext()) {
			final JsonNode node = fkNIt.next();
			if (!isAttrIn(node, attr, values)) {
				retExp = new ForeignKeyTypeException(getClass(), node.path( // NOPMD
						Attributes.F_REF_ID).asText(), node.path(
						Attributes.F_TYPE).asText());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[ERR-10014] ==> Error-10014 ( Location: "
							+ this.name + " ) In, ", retExp);
				}
				if (null != retExp) {
					break;
				}
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
							"[ERR-10002] ==> Error-10002 Object ( Location: "
									+ item.toString() + " )", retExp);
				}
				break;
			}
		}
		return retExp;
	}

	/**
	 * -10002
	 * 
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyStringNodes() {
		AbstractSchemaException retExp = null;
		final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
		while (nodeIt.hasNext()) {
			final JsonNode item = nodeIt.next();
			if (item.isContainerNode() || !item.isTextual()) {
				retExp = new AttrJsonTypeException(getClass(), "value = " // NOPMD
						+ item.toString());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(
							"[ERR-10002] ==> Error-10002 String ( Location: "
									+ item.toString() + " )", retExp);
				}
				break;
			}
		}
		return retExp;
	}

	/**
	 * 
	 * @param policy
	 * @param filter
	 * @param expectedValues
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyPKeyPolicyType(
			@NotNull final MetaPolicy policy,
			@NotNull final ConcurrentMap<String, Object> filter,
			@MinLength(1) final String... expectedValues) {
		final List<JsonNode> pkList = findNodes(this.verifiedNodes, filter);
		AbstractSchemaException retExp = null;
		for (final JsonNode jsonNode : pkList) {
			if (!isAttrIn(jsonNode, Attributes.F_TYPE, expectedValues)) {
				retExp = new PKColumnTypePolicyException(getClass(), // NOPMD
						policy.toString(), jsonNode.path(Attributes.F_TYPE)
								.asText());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[ERR-10012] ==> Error-10012 Policy = "
							+ policy + ", " + Attributes.F_TYPE + " = "
							+ jsonNode.path(Attributes.F_TYPE).asText()
							+ ", they are conflicts.");
				}
				break;
			}
		}
		return retExp;
	}

	/**
	 * -10011：Policy != COLLECTION
	 * 
	 * @param attr
	 * @param policy
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyPKeyNonCOPolicy(
			@NotNull @NotBlank @NotEmpty final String attr,
			@NotNull final MetaPolicy policy) {
		final int occurs = occursAttr(this.verifiedNodes, attr, Boolean.TRUE,
				false);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[I] ==> (policy != COLLECTION) occurs = " + occurs);
		}

		AbstractSchemaException retExp = null;
		if (Constants.ONE != occurs) {
			retExp = new PrimaryKeyPolicyException(getClass(),
					policy.toString(), this.table);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-10011] ==> Error-10011 ( Location: " + attr
						+ " ) [NonCO] occurs: " + occurs, retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10011：Policy == COLLECTION
	 * 
	 * @param attr
	 * @param policy
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyPKeyCOPolicy(
			@NotNull @NotBlank @NotEmpty final String attr,
			@NotNull final MetaPolicy policy) {
		final int occurs = occursAttr(this.verifiedNodes, attr, Boolean.TRUE,
				false);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[I] ==> (policy == COLLECTION) occurs = " + occurs);
		}

		AbstractSchemaException retExp = null;
		if (Constants.ONE >= occurs) {
			retExp = new PrimaryKeyPolicyException(getClass(),
					policy.toString(), this.table);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-10011] ==> Error-10011 ( Location: " + attr
						+ " ) [CO] occurs: " + occurs, retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10013：subtable不合法
	 * 
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyRelMissing(
			@NotNull @NotBlank @NotEmpty final String attr, final Object value,
			@Min(1) final int minOccurs) {
		// Pre Condition：假设attr是存在的，即上边verifyMissing函数已经验证通过
		final int occurs = occursAttr(this.verifiedNodes, attr, value, false);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[I] ==> minOccurs = " + minOccurs + ", occurs = "
					+ occurs);
		}

		AbstractSchemaException retExp = null;
		if (minOccurs > occurs) {
			retExp = new SubtableNotMatchException(getClass());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(
						"[ERR-10013] ==> Error-10013 For Value ( Location: "
								+ attr + " = " + value.toString()
								+ " ) [Subtable] occurs: " + occurs, retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10010：验证主键定义，至少有一个primarykey属性为true
	 * 
	 * @param attr
	 * @param value
	 * @param minOccurs
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyPKeyMissing(
			@NotNull @NotBlank @NotEmpty final String attr, final Object value,
			@Min(1) final int minOccurs) {
		// Pre Condition：假设attr是存在的，即上边verifyMissing函数已经验证通过
		final int occurs = occursAttr(this.verifiedNodes, attr, value, false);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[I] ==> minOccurs = " + minOccurs + ", occurs = "
					+ occurs);
		}

		AbstractSchemaException retExp = null;
		if (minOccurs > occurs) {
			retExp = new PrimaryKeyMissingException(getClass(), this.table);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(
						"[ERR-10010] ==> Error-10010 For Value ( Location: "
								+ attr + " = " + value.toString()
								+ " ) [PrimaryKey] occurs: " + occurs, retExp);
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
					LOGGER.debug("[ERR-10008] ==> Error-10008 ( Location: "
							+ this.name + " -> " + attr + ")", retExp);
				}
			} else {
				retExp = new DuplicatedAttrException(getClass(), attrExpStr);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[ERR-10007] ==> Error-10007 ( Location: "
							+ this.name + " -> " + attr + ")", retExp);
				}
			}
		}
		return retExp;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
