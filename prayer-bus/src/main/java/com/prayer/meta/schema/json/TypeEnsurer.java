package com.prayer.meta.schema.json;

import static com.prayer.util.sys.Error.message;
import static com.prayer.util.sys.Instance.instance;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.meta.DataType;
import com.prayer.util.StringKit;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class TypeEnsurer implements InternalEnsurer {
	// ~ Static Fields =======================================
	/** **/
	private static final ConcurrentMap<DataType, String[]> T_REQUIRED = new ConcurrentHashMap<>();
	/** **/
	private static final ConcurrentMap<DataType, String[]> T_SUPPORT = new ConcurrentHashMap<>();
	/** **/
	private static final ConcurrentMap<String, String> T_PATTERN = new ConcurrentHashMap<>();
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	@NotNull
	private transient final ArrayNode fieldsNode;
	// ~ Static Block ========================================

	/** **/
	static {
		// Required Mapping ( Remove name, type, columnName, columnType )
		T_REQUIRED.put(DataType.STRING, new String[] { Attributes.F_LENGTH });
		T_REQUIRED.put(DataType.XML, new String[] { Attributes.F_LENGTH });
		T_REQUIRED.put(DataType.JSON, new String[] { Attributes.F_LENGTH });
		T_REQUIRED.put(DataType.DATE, new String[] { Attributes.F_DATETIME, Attributes.F_DATEFORMAT });
		T_REQUIRED.put(DataType.LONG, new String[] {});
		T_REQUIRED.put(DataType.INT, new String[] {});
		T_REQUIRED.put(DataType.DECIMAL, new String[] { Attributes.F_PRECISION });
		T_REQUIRED.put(DataType.BOOLEAN, new String[] {});
		T_REQUIRED.put(DataType.BINARY, new String[] { Attributes.F_LENGTH });
		T_REQUIRED.put(DataType.SCRIPT, new String[] { Attributes.F_LENGTH });
		// Supported Attributes
		T_SUPPORT.put(DataType.STRING,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_PATTERN, Attributes.F_PK, Attributes.F_UNIQUE,
						Attributes.F_FK, Attributes.F_SUB_TABLE, Attributes.F_REF_ID, Attributes.F_REF_TABLE,
						Attributes.F_LENGTH, Attributes.F_NULLABLE, Attributes.F_MAX_LENGTH, Attributes.F_MIN_LENGTH });
		T_SUPPORT.put(DataType.XML,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_PATTERN, Attributes.F_UNIQUE, Attributes.F_SUB_TABLE,
						Attributes.F_LENGTH, Attributes.F_NULLABLE, Attributes.F_MAX_LENGTH, Attributes.F_MIN_LENGTH });
		T_SUPPORT.put(DataType.JSON,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_PATTERN, Attributes.F_UNIQUE, Attributes.F_SUB_TABLE,
						Attributes.F_LENGTH, Attributes.F_NULLABLE, Attributes.F_MAX_LENGTH, Attributes.F_MIN_LENGTH });
		T_SUPPORT.put(DataType.DATE,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_UNIQUE, Attributes.F_SUB_TABLE, Attributes.F_DATETIME,
						Attributes.F_DATEFORMAT, Attributes.F_NULLABLE, Attributes.F_MAX, Attributes.F_MIN });
		T_SUPPORT.put(DataType.LONG,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_PK, Attributes.F_UNIQUE, Attributes.F_FK,
						Attributes.F_SUB_TABLE, Attributes.F_REF_ID, Attributes.F_REF_TABLE, Attributes.F_LENGTH,
						Attributes.F_DATEFORMAT, Attributes.F_DATETIME, Attributes.F_NULLABLE, Attributes.F_UNIT,
						Attributes.F_MAX, Attributes.F_MIN });
		T_SUPPORT.put(DataType.INT,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_PK, Attributes.F_UNIQUE, Attributes.F_FK,
						Attributes.F_SUB_TABLE, Attributes.F_REF_ID, Attributes.F_REF_TABLE, Attributes.F_LENGTH,
						Attributes.F_NULLABLE, Attributes.F_UNIT, Attributes.F_MAX, Attributes.F_MIN });
		T_SUPPORT.put(DataType.DECIMAL,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_UNIQUE, Attributes.F_SUB_TABLE, Attributes.F_LENGTH,
						Attributes.F_NULLABLE, Attributes.F_PRECISION, Attributes.F_UNIT, Attributes.F_MAX,
						Attributes.F_MIN });
		T_SUPPORT.put(DataType.BOOLEAN, new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME,
				Attributes.F_COL_TYPE, Attributes.F_VALIDATOR, Attributes.F_SUB_TABLE, Attributes.F_NULLABLE });
		T_SUPPORT.put(DataType.BINARY,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_UNIQUE, Attributes.F_SUB_TABLE, Attributes.F_LENGTH,
						Attributes.F_NULLABLE, Attributes.F_MAX_LENGTH, Attributes.F_MIN_LENGTH });
		T_SUPPORT.put(DataType.SCRIPT,
				new String[] { Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME, Attributes.F_COL_TYPE,
						Attributes.F_VALIDATOR, Attributes.F_PATTERN, Attributes.F_UNIQUE, Attributes.F_SUB_TABLE,
						Attributes.F_LENGTH, Attributes.F_NULLABLE, Attributes.F_MAX_LENGTH, Attributes.F_MIN_LENGTH });
		// Attribute patterns filling
		T_PATTERN.put(Attributes.F_NAME, Attributes.RGX_F_NAME);
		T_PATTERN.put(Attributes.F_TYPE, Attributes.RGX_F_TYPE);
		T_PATTERN.put(Attributes.F_COL_NAME, Attributes.RGX_F_COL_NAME);
		T_PATTERN.put(Attributes.F_COL_TYPE, Attributes.RGX_F_COL_TYPE);

		T_PATTERN.put(Attributes.F_VALIDATOR, Attributes.RGX_F_VALIDATOR);
		T_PATTERN.put(Attributes.F_PK, Attributes.RGX_F_PK);
		T_PATTERN.put(Attributes.F_UNIQUE, Attributes.RGX_F_UNIQUE);
		T_PATTERN.put(Attributes.F_FK, Attributes.RGX_F_FK);
		T_PATTERN.put(Attributes.F_REF_ID, Attributes.RGX_F_REF_ID);
		T_PATTERN.put(Attributes.F_REF_TABLE, Attributes.RGX_F_REF_TABLE);
		T_PATTERN.put(Attributes.F_SUB_TABLE, Attributes.RGX_F_SUB_TABLE);
		T_PATTERN.put(Attributes.F_NULLABLE, Attributes.RGX_F_NULLABLE);
		T_PATTERN.put(Attributes.F_LENGTH, Attributes.RGX_F_LENGTH);
		T_PATTERN.put(Attributes.F_DATETIME, Attributes.RGX_F_DATETIME);
		T_PATTERN.put(Attributes.F_PRECISION, Attributes.RGX_F_PRECISION);
		T_PATTERN.put(Attributes.F_MAX_LENGTH, Attributes.RGX_F_MAX_LENGTH);
		T_PATTERN.put(Attributes.F_MIN_LENGTH, Attributes.RGX_F_MIN_LENGTH);
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param fieldsNode
	 */
	@PostValidateThis
	public TypeEnsurer(@NotNull final ArrayNode fieldsNode) {
		this.fieldsNode = fieldsNode;
		this.error = null; // NOPMD
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public void validate() throws AbstractSchemaException {
		// 1.验证-10001的必须属性是否存在
		validateRequired();
		interrupt();
		// 2.验证-10017的不支持属性
		validateUnsupported();
		interrupt();
		// 3.验证属性正则表达式
		validatePatterns();
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
	private boolean validateRequired() {
		final Iterator<JsonNode> fieldIt = this.fieldsNode.iterator();
		int idx = 0;
		// 22.验证Field中的Required属性
		while (fieldIt.hasNext()) {
			final JsonNode node = fieldIt.next();
			final DataType type = this.getType(node);
			final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
					message("D10000.FTIDX", idx, node.path(Attributes.F_NAME).asText(), type));
			// new JObjectValidator(node,message("D10000.FTIDX", idx,
			// node.path(Attributes.F_NAME).asText(), type));
			final String[] requiredAttrs = T_REQUIRED.get(type);
			this.error = validator.verifyRequired(requiredAttrs);
			if (null != this.error) {
				break;
			}
			idx++;
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validatePatterns() {
		final Iterator<JsonNode> fieldIt = this.fieldsNode.iterator();
		int idx = 0;
		// 23.验证Field中的Unsupport属性
		outer: while (fieldIt.hasNext()) {
			final JsonNode node = fieldIt.next();
			final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
					message("D10000.FIDX", idx, node.path(Attributes.F_NAME).asText()));
			// new JObjectValidator(node,message("D10000.FIDX", idx,
			// node.path(Attributes.F_NAME).asText()));
			for (final String attr : T_PATTERN.keySet()) {
				final String attrValue = node.path(attr).asText();
				if (StringKit.isNonNil(attrValue)) {
					this.error = validator.verifyPattern(attr, T_PATTERN.get(attr));
					if (null != this.error) {
						break outer;
					}
				}
			}
			idx++;
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateUnsupported() {
		final Iterator<JsonNode> fieldIt = this.fieldsNode.iterator();
		int idx = 0;
		// 24.验证Field中的属性的正则表达式
		while (fieldIt.hasNext()) {
			final JsonNode node = fieldIt.next();
			final DataType type = this.getType(node);
			final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
					message("D10000.FTIDX", idx, node.path(Attributes.F_NAME).asText(), type));
			// new JObjectValidator(node,message("D10000.FTIDX", idx,
			// node.path(Attributes.F_NAME).asText(), type));
			final String[] supportedAttrs = T_SUPPORT.get(type);
			this.error = validator.verifyUnsupported(supportedAttrs);
			if (null != this.error) {
				break;
			}
			idx++;
		}
		return null == this.error;
	}

	/**
	 * 
	 * @param fieldNode
	 * @return
	 */
	private DataType getType(final JsonNode fieldNode) {
		final String type = fieldNode.path(Attributes.F_TYPE).asText();
		// 自定义转换
		return DataType.fromString(type);
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
