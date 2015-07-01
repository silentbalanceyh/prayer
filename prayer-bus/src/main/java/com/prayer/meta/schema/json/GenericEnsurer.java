package com.prayer.meta.schema.json;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.meta.schema.Ensurer;
import com.prayer.mod.sys.GenericSchema;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public class GenericEnsurer implements Ensurer {
	// ~ Static Fields =======================================
	/** Meta Required **/
	private static final String[] M_REQUIRED = new String[] {
			Attributes.M_NAMESPACE, Attributes.M_NAME, Attributes.M_CATEGORY,
			Attributes.M_TABLE, Attributes.M_IDENTIFIER, Attributes.M_MAPPING,
			Attributes.M_POLICY };
	/** Meta Attributes **/
	private static final String[] M_ATTRS = new String[] {
			Attributes.M_NAMESPACE, Attributes.M_NAME, Attributes.M_CATEGORY,
			Attributes.M_TABLE, Attributes.M_IDENTIFIER, Attributes.M_MAPPING,
			Attributes.M_POLICY, // Required
			Attributes.M_SUB_KEY, Attributes.M_SUB_TABLE, // SubTable
			Attributes.M_SEQ_NAME, Attributes.M_SEQ_STEP, Attributes.M_SEQ_INIT // Sequence
	};
	/** **/
	private static final ConcurrentMap<String, String> REGEX_MAP = new ConcurrentHashMap<>();
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	private transient JsonNode rootNode;
	// ~ Static Block ========================================
	/** Put Regex **/
	static {
		REGEX_MAP.put(Attributes.M_NAME, Attributes.RGX_M_NAME);
		REGEX_MAP.put(Attributes.M_NAMESPACE, Attributes.RGX_M_NAMESPACE);
		REGEX_MAP.put(Attributes.M_CATEGORY, Attributes.RGX_M_CATEGORY);
		REGEX_MAP.put(Attributes.M_TABLE, Attributes.RGX_M_TABLE);
		REGEX_MAP.put(Attributes.M_IDENTIFIER, Attributes.RGX_M_IDENTIFITER);
		REGEX_MAP.put(Attributes.M_MAPPING, Attributes.RGX_M_MAPPING);
		REGEX_MAP.put(Attributes.M_POLICY, Attributes.RGX_M_POLICY);
	}

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
		// 2.验证Meta节点，借助“截断”
		ret = ret && validateMetaAttr();

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
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 
	 * @return
	 */
	public boolean validateMetaAttr() {
		final JsonNode metaNode = this.rootNode.path(Attributes.R_META);
		final JsonSchemaValidator validator = new JsonSchemaValidator(metaNode,
				Attributes.R_META);
		// 4.__meta__ Required
		this.error = validator.verifyRequired(M_REQUIRED);
		if (null != this.error) {
			return false; // NOPMD
		}
		// 5.__meta__ Exclude Unsupported Attributes
		this.error = validator.verifyUnsupported(M_ATTRS);
		if (null != this.error) {
			return false; // NOPMD
		}
		// 6.__meta__ Required Attribute Patterns
		for(final String attr: REGEX_MAP.keySet()){
			this.error = validator.verifyPattern(attr, REGEX_MAP.get(attr));
			if(null != this.error){
				return false;	// NOPMD
			}
		}
		return null == this.error;
	}

	/**
	 * 验证__meta__, __fields__, __keys__
	 * 
	 * @return
	 */
	public boolean validateRoot() {
		final JsonSchemaValidator validator = new JsonSchemaValidator(
				this.rootNode, null);
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
