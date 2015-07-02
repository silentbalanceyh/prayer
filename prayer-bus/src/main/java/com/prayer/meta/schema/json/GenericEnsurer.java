package com.prayer.meta.schema.json;

import static com.prayer.util.JsonKit.fromJObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

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
	// ~ Instance Fields =====================================
	/** **/
	private transient MetaEnsurer metaEnsurer;
	/** **/
	private transient FieldsEnsurer fieldsEnsurer;
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
	private void initEnsurers() {
		this.metaEnsurer = new MetaEnsurer(
				this.rootNode.path(Attributes.R_META));
		final ArrayNode fieldsNode = fromJObject(this.rootNode
				.path(Attributes.R_FIELDS));
		if (null != fieldsNode) {
			this.fieldsEnsurer = new FieldsEnsurer(fieldsNode);
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
