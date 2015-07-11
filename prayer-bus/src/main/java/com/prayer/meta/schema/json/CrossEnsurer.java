package com.prayer.meta.schema.json;

import static com.prayer.util.JsonKit.fromJObject;
import static com.prayer.util.JsonKit.occursAttr;
import static com.prayer.util.sys.Converter.fromStr;
import static com.prayer.util.sys.Instance.instance;

import java.util.Iterator;

import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.ColumnsMissingException;
import com.prayer.exception.schema.FKNotOnlyOneException;
import com.prayer.exception.schema.MultiForPKPolicyException;
import com.prayer.exception.schema.PKNotOnlyOneException;
import com.prayer.exception.schema.WrongTimeAttrException;
import com.prayer.mod.sys.SystemEnum.KeyCategory;
import com.prayer.mod.sys.SystemEnum.MetaPolicy;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class CrossEnsurer implements InternalEnsurer { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrossEnsurer.class);
	/** **/
	private static final String ERR_PRE = "[ERR";
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	@NotNull
	private transient final ArrayNode fieldsNode;
	/** **/
	@NotNull
	private transient final ArrayNode keysNode;
	/** **/
	@NotNull
	private transient final MetaPolicy pkPolicy;

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
			@NotNull final ArrayNode fieldsNode,
			@NotNull final MetaPolicy policy) {
		this.keysNode = keysNode;
		this.fieldsNode = fieldsNode;
		this.pkPolicy = policy;
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Override
	public void validate() throws AbstractSchemaException {
		// 1.验证PK的Policy和Multi的冲突
		validateMetaPKPolicy();
		interrupt();
		// 2.主键必须在__keys__中唯一定义异常
		validatePKOnlyOne();
		interrupt();
		// 3.外键如果存在则在__keys__中只能出现0次或者1次
		validateFKOnlyOne();
		interrupt();
		// 4.检查columns中的列是否在__fields__中定义过
		validateColumnMissing();
		interrupt();
		// 5.检查columns中的定义和__fields__中定义相同
		validateAttrConflict();
		interrupt();
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
	/**
	 * 
	 * @return
	 */
	private boolean validateAttrConflict() {
		// 35.验证__keys__和__fields__中的属性定义
		final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final Boolean isMulti = node.path(Attributes.K_MULTI).asBoolean();
			final KeyCategory category = fromStr(KeyCategory.class,
					node.path(Attributes.K_CATEGORY).asText());
			final ArrayNode columns = fromJObject(node
					.path(Attributes.K_COLUMNS));
			if (isMulti) {
				if (KeyCategory.PrimaryKey == category) {
					countAttr(columns, Attributes.F_PK, Boolean.TRUE, category);
				} else if (KeyCategory.UniqueKey == category) {
					countAttr(columns, Attributes.F_UNIQUE, Boolean.FALSE,
							category);
				}
			} else {
				if (KeyCategory.PrimaryKey == category) {
					countAttr(columns, Attributes.F_PK, Boolean.TRUE, category);
				} else if (KeyCategory.UniqueKey == category) {
					countAttr(columns, Attributes.F_UNIQUE, Boolean.TRUE,
							category);
				} else {
					countAttr(columns, Attributes.F_FK, Boolean.TRUE, category);
				}
			}
			if (null != this.error) {
				break;
			}
		}
		return null == this.error;
	}

	/**
	 * 
	 * @param columns
	 * @param attr
	 * @param value
	 * @param category
	 * @return
	 */
	private void countAttr(final ArrayNode columns, final String attr,
			final Object value, final KeyCategory category) {
		int occurs = 0;
		final Iterator<JsonNode> nodeIt = columns.iterator();
		while (nodeIt.hasNext()) {
			final String colName = nodeIt.next().asText();
			final JsonNode fieldNode = this.findNode(colName);
			occurs += occursAttr(fieldNode, attr, value, true);
		}
		if (occurs != columns.size()) {
			this.error = instance(WrongTimeAttrException.class.getName(),
					getClass(), attr, value.toString(),
					String.valueOf(columns.size()), String.valueOf(occurs),
					category.toString());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ERR_PRE + this.error.getErrorCode()
						+ "] ==> Pair (" + attr + " = " + value
						+ ") of category : " + category, this.error);
			}
		}
	}

	/**
	 * 根据columnName找Node
	 * 
	 * @param colValue
	 * @return
	 */
	private JsonNode findNode(final String colValue) {
		final Iterator<JsonNode> nodeIt = this.fieldsNode.iterator();
		JsonNode retNode = null;
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final String nodeColValue = node.path(Attributes.F_COL_NAME)
					.asText();
			if (StringUtil.equals(colValue, nodeColValue)) {
				retNode = node;
				break;
			}
		}
		return retNode;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateColumnMissing() {
		// 34.验证columns中出现过的列是否是__fields__中定义的合法列
		final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
		int count = 0;
		outer: while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final ArrayNode columns = fromJObject(node
					.path(Attributes.K_COLUMNS));
			final Iterator<JsonNode> columnIt = columns.iterator();
			while (columnIt.hasNext()) {
				final String colName = columnIt.next().asText();
				final int occurs = occursAttr(this.fieldsNode,
						Attributes.F_COL_NAME, colName, true);
				if (0 == occurs) {
					final String keyName = node.path(Attributes.K_NAME)
							.asText();
					this.error = new ColumnsMissingException(getClass(), // NOPMD
							colName, keyName);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(ERR_PRE + this.error.getErrorCode()
								+ "] ==> ( Location: index = " + count
								+ ", keyName = " + keyName + ", column = "
								+ colName + " )", this.error);
					}
					break outer;
				}
			}
			count++;
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validatePKOnlyOne() {
		// 32.验证Keys中的PrimaryKey只能有一个
		final int occurs = occursAttr(this.keysNode, Attributes.K_CATEGORY,
				KeyCategory.PrimaryKey, true);
		if (1 != occurs) { // NOPMD
			this.error = new PKNotOnlyOneException(getClass());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ERR_PRE + this.error.getErrorCode()
						+ "] ==> Primary Key definition redundancy.",
						this.error);
			}
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateFKOnlyOne() {
		// 33.验证Keys中的ForeignKey如果存在只能有一个
		final int occurs = occursAttr(this.keysNode, Attributes.K_CATEGORY,
				KeyCategory.ForeignKey, true);
		if (1 < occurs) { // NOPMD
			this.error = new FKNotOnlyOneException(getClass());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ERR_PRE + this.error.getErrorCode()
						+ "] ==> Foreign Key definition redundancy.",
						this.error);
			}
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateMetaPKPolicy() { // NOPMD
		// 31.验证Keys中的PrimaryKey对应的Policy
		final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
		int count = 0;
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final KeyCategory category = fromStr(KeyCategory.class,
					node.path(Attributes.K_CATEGORY).asText());
			if (KeyCategory.PrimaryKey != category) {
				count++;
				continue;
			}
			final Boolean isMulti = node.path(Attributes.K_MULTI).asBoolean();
			if (isMulti && MetaPolicy.COLLECTION != this.pkPolicy) {
				this.error = new MultiForPKPolicyException(getClass(), // NOPMD
						this.pkPolicy.toString(), isMulti.toString());
			} else if (!isMulti && MetaPolicy.ASSIGNED != this.pkPolicy
					&& MetaPolicy.GUID != this.pkPolicy
					&& MetaPolicy.INCREMENT != this.pkPolicy) {
				this.error = new MultiForPKPolicyException(getClass(), // NOPMD
						this.pkPolicy.toString(), isMulti.toString());
			}
			if (null != this.error) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ERR_PRE + this.error.getErrorCode()
							+ "] ==> ( Location: index = " + count
							+ ", policy = " + this.pkPolicy.toString()
							+ ", multi=" + isMulti + " )", this.error);
				}
				break;
			}
		}
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
