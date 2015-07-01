package com.prayer.meta.schema.json;

import static com.prayer.util.sys.Converter.fromStr;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.meta.schema.Ensurer;
import com.prayer.mod.sys.GenericSchema;
import com.prayer.mod.sys.SystemEnum.MetaCategory;
import com.prayer.mod.sys.SystemEnum.MetaMapping;
import com.prayer.mod.sys.SystemEnum.MetaPolicy;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public class MetaEnsurer implements Ensurer {
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
	private transient JsonNode metaNode;
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
	public MetaEnsurer() {
		this(null);
	}

	/**
	 * 单参构造函数
	 * 
	 * @param metaNode
	 */
	public MetaEnsurer(final JsonNode metaNode) {
		this.metaNode = metaNode;
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
		boolean ret = validateMetaAttr();
		// 2.验证子流程
		if (ret) {
			ret = ret && validateDispatcher();
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
	public void refreshData(@NotNull final JsonNode metaNode) {
		this.metaNode = metaNode;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 
	 * @return
	 */
	private boolean validateDispatcher() {
		final JsonSchemaValidator validator = new JsonSchemaValidator(
				this.metaNode, Attributes.R_META);
		final MetaCategory category = fromStr(MetaCategory.class, this.metaNode
				.path(Attributes.M_CATEGORY).textValue());
		final MetaMapping mapping = fromStr(MetaMapping.class, this.metaNode
				.path(Attributes.M_MAPPING).textValue());
		// Category
		switch (category) {
		case ENTITY: {
			// Mapping
			switch (mapping) {
			case PARTIAL: {
				return verifyCEntityDirect();
			}
			case DIRECT: {

			}
				break;
			case COMBINATED: {

			}
				break;
			default: {
				this.error = validator.verifyPattern(Attributes.M_MAPPING,
						REGEX_MAP.get(Attributes.M_MAPPING));
			}
				break;
			}
		}
			break;
		case RELATION: {
			verifyCRelation(); // NOPMD
		}
			break;
		default: {
			this.error = validator.verifyPattern(Attributes.M_CATEGORY,
					REGEX_MAP.get(Attributes.M_CATEGORY));
		}
			break;
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateMetaAttr() {
		final JsonSchemaValidator validator = new JsonSchemaValidator(
				this.metaNode, Attributes.R_META);
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
		for (final String attr : REGEX_MAP.keySet()) {
			this.error = validator.verifyPattern(attr, REGEX_MAP.get(attr));
			if (null != this.error) {
				return false; // NOPMD
			}
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean verifyCEntityDirect() {
		final JsonSchemaValidator validator = new JsonSchemaValidator(
				this.metaNode, Attributes.R_META);
		// 7.2.1.category == ENTITY && mapping == PARTIAL
		this.error = validator.verifyExisting(Attributes.M_SUB_KEY,
				Attributes.M_SUB_TABLE);
		if (null != this.error) {
			return false; // NOPMD
		}
		// 7.2.2.seqname, seqinit, seqstep Checking
		this.error = validator.verifyExisting(Attributes.M_SEQ_INIT,
				Attributes.M_SEQ_NAME, Attributes.M_SEQ_STEP);
		if (null != this.error) {
			return false; // NOPMD
		}
		// 7.2.3.policy in ASSIGNED
		this.error = validator.verifyIn(Attributes.M_POLICY,
				MetaPolicy.ASSIGNED.toString());
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean verifyCRelation() {
		final JsonSchemaValidator validator = new JsonSchemaValidator(
				this.metaNode, Attributes.R_META);
		// 7.1.1.category == RELATION
		this.error = validator.verifyExisting(Attributes.M_SUB_KEY,
				Attributes.M_SUB_TABLE);
		if (null != this.error) {
			return false; // NOPMD
		}
		// 7.1.2.mapping in DIRECT
		this.error = validator.verifyIn(Attributes.M_MAPPING,
				MetaMapping.DIRECT.toString());
		if (null != this.error) {
			return false; // NOPMD
		}
		// 7.1.3.policy not COLLECTION | ASSIGNED
		this.error = validator.verifyNotIn(Attributes.M_POLICY,
				MetaPolicy.COLLECTION.toString(),
				MetaPolicy.ASSIGNED.toString());
		return null == this.error; // NOPMD
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
