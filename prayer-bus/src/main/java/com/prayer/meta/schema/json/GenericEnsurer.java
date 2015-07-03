package com.prayer.meta.schema.json;

import static com.prayer.util.JsonKit.fromJObject;
import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.meta.schema.Ensurer;
import com.prayer.mod.sys.GenericSchema;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public final class GenericEnsurer implements Ensurer {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GenericEnsurer.class);
	// ~ Instance Fields =====================================
	/** **/
	private transient MetaEnsurer metaEnsurer;
	/** **/
	private transient FieldsEnsurer fieldsEnsurer;
	/** **/
	private transient PrimaryKeyEnsurer pKeyEnsurer;
	/** **/
	private transient JsonNode rootNode;
	/** **/
	private transient AbstractSchemaException error;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 无参构造函数
	 */
	public GenericEnsurer() {
		this(null);
	}

	/**
	 * 单参构造函数
	 * 
	 * @param jsonMap
	 */
	public GenericEnsurer(final JsonNode rootNode) {
		this.rootNode = rootNode;
		if (null != this.rootNode) {
			this.initEnsurers();
		}
		this.error = null; // NOPMD
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public GenericSchema getResult() {
		return null;
	}

	/**
	 * 
	 */
	@Override
	public boolean validate() {
		// 1.验证root节点：__keys__, __meta__, __fields__
		boolean ret = validateRoot();
		// 2.验证Meta节点
		if (ret) {
			try {
				metaEnsurer.validate();
				ret = true;
			} catch (AbstractSchemaException ex) {
				this.error = ex;
				ret = false;
			}
		}
		// 3.验证Fields节点
		if (ret) {
			try {
				fieldsEnsurer.validate();
				ret = true;
			} catch (AbstractSchemaException ex) {
				this.error = ex;
				ret = false;
			}
		}
		// 4.开始验证Primary Key定义
		if (ret) {
			try {
				pKeyEnsurer.validate();
				ret = true;
			} catch (AbstractSchemaException ex) {
				this.error = ex;
				ret = false;
			}
		}
		return ret;
	}

	/**
	 * 
	 */
	@Override
	public AbstractSchemaException getError() {
		return this.error;
	}

	/**
	 * 
	 */
	@Override
	public void refreshData(@NotNull final JsonNode rootNode) {
		this.rootNode = rootNode;
		this.initEnsurers();
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 
	 */
	private void initEnsurers() { // NOPMD
		// 顶层验证器
		this.metaEnsurer = new MetaEnsurer(
				this.rootNode.path(Attributes.R_META));
		// 字段验证器
		final ArrayNode fieldsNode = fromJObject(this.rootNode
				.path(Attributes.R_FIELDS));
		if (null != fieldsNode) {
			this.fieldsEnsurer = new FieldsEnsurer(fieldsNode);
		}

		if (null != fieldsNode) {
			// 主键验证器
			final String table = this.rootNode.path(Attributes.R_META)
					.path(Attributes.M_TABLE).asText();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[I] ==> table = " + table);
			}
			final String policy = this.rootNode.path(Attributes.R_META)
					.path(Attributes.M_POLICY).asText();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[I] ==> policy = " + policy);
			}
			/**
			 * 判断policy和table的情况，必须保证policy和table两个值，
			 * 也就是__meta__中验证OK了过后才能执行PrimaryKey对应的验证
			 */
			if (null != table && null != policy && StringUtil.isNotBlank(table)
					&& StringUtil.isNotEmpty(table)
					&& StringUtil.isNotBlank(policy)
					&& StringUtil.isNotEmpty(policy)) {
				pKeyEnsurer = new PrimaryKeyEnsurer(fieldsNode, policy, table);
			}
		}
	}

	/**
	 * 验证__meta__, __fields__, __keys__
	 * 
	 * @return
	 */
	public boolean validateRoot() {
		final JObjectValidator validator = new JObjectValidator(this.rootNode,
				null);
		// 1.Root Required
		this.error = validator.verifyRequired(Attributes.R_META,
				Attributes.R_KEYS, Attributes.R_FIELDS);
		if (null != this.error) {
			return false; // NOPMD
		}
		// 2.Root Json Type
		this.error = validator.verifyJObject(Attributes.R_META);
		if (null != this.error) {
			return false; // NOPMD
		}
		this.error = validator.verifyJArray(Attributes.R_KEYS);
		if (null != this.error) {
			return false; // NOPMD
		}
		this.error = validator.verifyJArray(Attributes.R_FIELDS);
		// 3.Root Exclude Unsupported Attributes
		if (null != this.error) {
			return false; // NOPMD
		}
		this.error = validator.verifyUnsupported(Attributes.R_META,
				Attributes.R_KEYS, Attributes.R_FIELDS, Attributes.R_ORDERS,
				Attributes.R_INDEXES);
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
