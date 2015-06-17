package com.prayer.mod.sys; // NOPMD

import java.io.Serializable;

import com.prayer.meta.DataType;
import com.prayer.mod.sys.SystemEnum.FieldDatetime;

/**
 * 对应表SYS_FIELDS
 *
 * @author Lang
 * @see
 */
public class FieldModel implements Serializable { // NOPMD
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 7466951277850489853L;
	// ~ Instance Fields =====================================
	/** K_ID：Fields表的主键 **/
	private String uniqueId;
	// !基础Field数据------------------------------------------
	/** S_NAME：Field字段名 **/
	private String name;
	/** S_TYPE：对应的Lyra的数据类型 **/
	private DataType type;

	// !Constraints数据---------------------------------------
	/** C_PATTERN：对应的Pattern **/
	private String pattern;
	/** C_VALIDATOR：Java的Class名 **/
	private String validator;
	/** C_LENGTH：String类型的长度定义 **/
	private int length;
	/** C_DATETIME：时间格式类型 **/
	private FieldDatetime datetime;
	/** C_DATEFORMAT：时间格式Pattern **/
	private String dateFormat;
	/** C_PRECISION：浮点数精度描述 **/
	private int precision;
	/** C_UNIT：数据后边的单位信息 **/
	private String unit;
	/** C_MAX_LENGTH：字符串的最大长度限制 **/
	private int maxLength;
	/** C_MIN_LENGTH：字符串的最小长度限制 **/
	private int minLength;
	/** C_MIN：数值的最小值 **/
	private long min;
	/** C_MAX：数值的最大值 **/
	private long max;

	// !数据库Boolean约束数据----------------------------------
	/** IS_PRIMARY_KEY：当前字段是否主键 **/
	private boolean primaryKey;
	/** IS_UNIQUE：当前字段是否Unique **/
	private boolean unique;
	/** IS_SUB_TABLE：当前字段是否存在于子表 **/
	private boolean subTable;
	/** IS_FOREIGN_KEY：当前字段是否外键 **/
	private boolean foreignKey;
	/** IS_NULLABLE：当前字段是否可为空 **/
	private boolean nullable;

	// !Fields数据库配置信息-----------------------------------
	/** D_COLUMN_NAME：字段对应的数据列名 **/
	private String columnName;
	/** D_COLUMN_TYPE：字段对应的数据类型名 **/
	private String columnType;
	/** D_REF_TABLE：字段为外键时引用的外键表名 **/
	private String refTable;
	/** D_REF_ID：字段为外键时引用的外键的主键名 **/
	private String refId;

	/** R_META_ID：外键约束，关联SYS_META **/
	private String refMetaId;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Get/Set =============================================

	/**
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId
	 *            the uniqueId to set
	 */
	public void setUniqueId(final String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public DataType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final DataType type) {
		this.type = type;
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern
	 *            the pattern to set
	 */
	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the validator
	 */
	public String getValidator() {
		return validator;
	}

	/**
	 * @param validator
	 *            the validator to set
	 */
	public void setValidator(final String validator) {
		this.validator = validator;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(final int length) {
		this.length = length;
	}

	/**
	 * @return the datetime
	 */
	public FieldDatetime getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime
	 *            the datetime to set
	 */
	public void setDatetime(final FieldDatetime datetime) {
		this.datetime = datetime;
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(final String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @return the precision
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * @param precision
	 *            the precision to set
	 */
	public void setPrecision(final int precision) {
		this.precision = precision;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(final String unit) {
		this.unit = unit;
	}

	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength
	 *            the maxLength to set
	 */
	public void setMaxLength(final int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @return the minLength
	 */
	public int getMinLength() {
		return minLength;
	}

	/**
	 * @param minLength
	 *            the minLength to set
	 */
	public void setMinLength(final int minLength) {
		this.minLength = minLength;
	}

	/**
	 * @return the min
	 */
	public long getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(final long min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public long getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(final long max) {
		this.max = max;
	}

	/**
	 * @return the primaryKey
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey
	 *            the primaryKey to set
	 */
	public void setPrimaryKey(final boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * @param unique
	 *            the unique to set
	 */
	public void setUnique(final boolean unique) {
		this.unique = unique;
	}

	/**
	 * @return the subTable
	 */
	public boolean isSubTable() {
		return subTable;
	}

	/**
	 * @param subTable
	 *            the subTable to set
	 */
	public void setSubTable(final boolean subTable) {
		this.subTable = subTable;
	}

	/**
	 * @return the foreignKey
	 */
	public boolean isForeignKey() {
		return foreignKey;
	}

	/**
	 * @param foreignKey
	 *            the foreignKey to set
	 */
	public void setForeignKey(final boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	/**
	 * @return the nullable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * @param nullable
	 *            the nullable to set
	 */
	public void setNullable(final boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType
	 *            the columnType to set
	 */
	public void setColumnType(final String columnType) {
		this.columnType = columnType;
	}

	/**
	 * @return the refTable
	 */
	public String getRefTable() {
		return refTable;
	}

	/**
	 * @param refTable
	 *            the refTable to set
	 */
	public void setRefTable(final String refTable) {
		this.refTable = refTable;
	}

	/**
	 * @return the refId
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * @param refId
	 *            the refId to set
	 */
	public void setRefId(final String refId) {
		this.refId = refId;
	}

	/**
	 * @return the refMetaId
	 */
	public String getRefMetaId() {
		return refMetaId;
	}

	/**
	 * @param refMetaId
	 *            the refMetaId to set
	 */
	public void setRefMetaId(final String refMetaId) {
		this.refMetaId = refMetaId;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return "FieldModel [uniqueId=" + uniqueId + ", name=" + name
				+ ", type=" + type + ", pattern=" + pattern + ", validator="
				+ validator + ", length=" + length + ", datetime=" + datetime
				+ ", dateFormat=" + dateFormat + ", precision=" + precision
				+ ", unit=" + unit + ", maxLength=" + maxLength
				+ ", minLength=" + minLength + ", min=" + min + ", max=" + max
				+ ", primaryKey=" + primaryKey + ", unique=" + unique
				+ ", subTable=" + subTable + ", foreignKey=" + foreignKey
				+ ", nullable=" + nullable + ", columnName=" + columnName
				+ ", columnType=" + columnType + ", refTable=" + refTable
				+ ", refId=" + refId + ", refMetaId=" + refMetaId + "]";
	}

	/** **/
	@Override
	public int hashCode() {	// NOPMD
		final int prime = 31; // NOPMD
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((columnType == null) ? 0 : columnType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((uniqueId == null) ? 0 : uniqueId.hashCode());
		return result;
	}

	/** **/
	@Override
	public boolean equals(final Object obj) { // NOPMD
		if (this == obj) {
			return true; // NOPMD
		}
		if (obj == null) {
			return false; // NOPMD
		}
		if (getClass() != obj.getClass()) {
			return false; // NOPMD
		}
		final FieldModel other = (FieldModel) obj;
		if (columnName == null) {
			if (other.columnName != null) {
				return false; // NOPMD
			}
		} else if (!columnName.equals(other.columnName)) {
			return false; // NOPMD
		}
		if (columnType == null) {
			if (other.columnType != null) {
				return false; // NOPMD
			}
		} else if (!columnType.equals(other.columnType)) {
			return false; // NOPMD
		}
		if (name == null) {
			if (other.name != null) {
				return false; // NOPMD
			}
		} else if (!name.equals(other.name)) {
			return false; // NOPMD
		}
		if (type != other.type) {
			return false; // NOPMD
		}
		if (uniqueId == null) {
			if (other.uniqueId != null) {
				return false; // NOPMD
			}
		} else if (!uniqueId.equals(other.uniqueId)) {
			return false; // NOPMD
		}
		return true;
	}
}
