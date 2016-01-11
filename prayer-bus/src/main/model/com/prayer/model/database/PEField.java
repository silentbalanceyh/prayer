package com.prayer.model.database; // NOPMD

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.base.model.AbstractEntity;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.DateMode;
import com.prayer.facade.entity.Attributes;
import com.prayer.model.type.DataType;
import com.prayer.plugin.jackson.ClassDeserializer;
import com.prayer.plugin.jackson.ClassSerializer;
import com.prayer.plugin.jackson.DataTypeDeserializer;
import com.prayer.plugin.jackson.DataTypeSerializer;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表SYS_FIELDS
 *
 * @author Lang
 * @see
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PEField extends AbstractEntity<String> { // NOPMD
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 7466951277850489853L;
    // ~ Instance Fields =====================================
    /** K_ID：Fields表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    // !基础Field数据------------------------------------------
    /** S_NAME：Field字段名 **/
    @JsonProperty(NAME)
    private String name;
    /** S_TYPE：对应的Lyra的数据类型 **/
    @JsonProperty(TYPE)
    private DataType type;

    // !Constraints数据---------------------------------------
    /** C_PATTERN：对应的Pattern **/
    @JsonProperty(PATTERN)
    private String pattern;
    /** C_VALIDATOR：Java的Class名 **/
    @JsonProperty(VALIDATOR)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> validator;
    /** C_LENGTH：String类型的长度定义 **/
    @JsonProperty(LENGTH)
    private int length;
    /** C_DATETIME：时间格式类型 **/
    @JsonProperty(DATE_TIME)
    private DateMode datetime;
    /** C_DATEFORMAT：时间格式Pattern **/
    @JsonProperty(DATE_FORMAT)
    private String dateFormat;
    /** C_PRECISION：浮点数精度描述 **/
    @JsonProperty(PRECISION)
    private int precision;
    /** C_UNIT：数据后边的单位信息 **/
    @JsonProperty(UNIT)
    private String unit;
    /** C_MAX_LENGTH：字符串的最大长度限制 **/
    @JsonProperty(MAX_LENGTH)
    private int maxLength = -1;
    /** C_MIN_LENGTH：字符串的最小长度限制 **/
    @JsonProperty(MIN_LENGTH)
    private int minLength = -1;
    /** C_MIN：数值的最小值 **/
    @JsonProperty(MIN)
    private long min = -1;
    /** C_MAX：数值的最大值 **/
    @JsonProperty(MAX)
    private long max = -1;

    // !数据库Boolean约束数据----------------------------------
    /** IS_PRIMARY_KEY：当前字段是否主键 **/
    @JsonProperty(PRIMARY_KEY)
    private boolean primaryKey;
    /** IS_UNIQUE：当前字段是否Unique **/
    @JsonProperty(UNIQUE)
    private boolean unique;
    /** IS_SUB_TABLE：当前字段是否存在于子表 **/
    @JsonProperty(SUB_TABLE)
    private boolean subTable;
    /** IS_FOREIGN_KEY：当前字段是否外键 **/
    @JsonProperty(FOREIGN_KEY)
    private boolean foreignKey;
    /** IS_NULLABLE：当前字段是否可为空 **/
    @JsonProperty(NULLABLE)
    private boolean nullable = true;

    // !Fields数据库配置信息-----------------------------------
    /** D_COLUMN_NAME：字段对应的数据列名 **/
    @JsonProperty(COLUMN_NAME)
    private String columnName;
    /** D_COLUMN_TYPE：字段对应的数据类型名 **/
    @JsonProperty(COLUMN_TYPE)
    private String columnType;
    /** D_REF_TABLE：字段为外键时引用的外键表名 **/
    @JsonProperty(REF_TABLE)
    private String refTable;
    /** D_REF_ID：字段为外键时引用的外键的主键名 **/
    @JsonProperty(REF_ID)
    private String refId;

    /** R_META_ID：外键约束，关联SYS_META **/
    @JsonProperty(REF_MID)
    private String refMetaId;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PEField() {
    }

    /** **/
    public PEField(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PEField(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeString(data, NAME, this::getName);
        writeEnum(data, TYPE, this::getType);
        writeString(data, PATTERN, this::getPattern);
        writeClass(data, VALIDATOR, this::getValidator);
        writeInt(data, LENGTH, this::getLength);
        writeEnum(data, DATE_TIME, this::getDatetime);
        writeString(data, DATE_FORMAT, this::getDateFormat);
        writeInt(data, PRECISION, this::getPrecision);
        writeString(data, UNIT, this::getUnit);
        writeInt(data, MAX_LENGTH, this::getMaxLength);
        writeInt(data, MIN_LENGTH, this::getMinLength);
        writeLong(data, MAX, this::getMax);
        writeLong(data, MIN, this::getMin);
        writeBoolean(data, PRIMARY_KEY, this::isPrimaryKey);
        writeBoolean(data, UNIQUE, this::isUnique);
        writeBoolean(data, SUB_TABLE, this::isSubTable);
        writeBoolean(data, FOREIGN_KEY, this::isForeignKey);
        writeBoolean(data, NULLABLE, this::isNullable);
        writeString(data, COLUMN_NAME, this::getColumnName);
        writeString(data, COLUMN_TYPE, this::getColumnType);
        writeString(data, REF_TABLE, this::getRefTable);
        writeString(data, REF_ID, this::getRefId);
        writeString(data, REF_MID, this::getRefMetaId);
        return data;
    }

    /** **/
    @Override
    public PEField fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, NAME, this::setName);
        readEnum(data, TYPE, this::setType, DataType.class);
        readString(data, PATTERN, this::setPattern);
        readClass(data, VALIDATOR, this::setValidator);
        readInt(data, LENGTH, this::setLength);
        readEnum(data, DATE_TIME, this::setDatetime, DateMode.class);
        readString(data, DATE_FORMAT, this::setDateFormat);
        readInt(data, PRECISION, this::setPrecision);
        readString(data, UNIT, this::setUnit);
        readInt(data, MAX_LENGTH, this::setMaxLength);
        readInt(data, MIN_LENGTH, this::setMinLength);
        readLong(data, MAX, this::setMax);
        readLong(data, MIN, this::setMin);
        readBoolean(data, PRIMARY_KEY, this::setPrimaryKey);
        readBoolean(data, UNIQUE, this::setUnique);
        readBoolean(data, SUB_TABLE, this::setSubTable);
        readBoolean(data, FOREIGN_KEY, this::setForeignKey);
        readBoolean(data, NULLABLE, this::setNullable);
        readString(data, COLUMN_NAME, this::setColumnName);
        readString(data, COLUMN_TYPE, this::setColumnType);
        readString(data, REF_TABLE, this::setRefTable);
        readString(data, REF_ID, this::setRefId);
        readString(data, REF_MID, this::setRefMetaId);
        return this;
    }

    /** **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getName);
        writeEnum(buffer, this::getType);
        writeString(buffer, this::getPattern);
        writeClass(buffer, this::getValidator);
        writeInt(buffer, this::getLength);
        writeEnum(buffer, this::getDatetime);
        writeString(buffer, this::getDateFormat);
        writeInt(buffer, this::getPrecision);
        writeString(buffer, this::getUnit);
        writeInt(buffer, this::getMaxLength);
        writeInt(buffer, this::getMinLength);
        writeLong(buffer, this::getMax);
        writeLong(buffer, this::getMin);
        writeBoolean(buffer, this::isPrimaryKey);
        writeBoolean(buffer, this::isUnique);
        writeBoolean(buffer, this::isSubTable);
        writeBoolean(buffer, this::isForeignKey);
        writeBoolean(buffer, this::isNullable);
        writeString(buffer, this::getColumnName);
        writeString(buffer, this::getColumnType);
        writeString(buffer, this::getRefTable);
        writeString(buffer, this::getRefId);
        writeString(buffer, this::getRefMetaId);
    }

    /** **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setName);
        pos = readEnum(pos, buffer, this::setType, DataType.class);
        pos = readString(pos, buffer, this::setPattern);
        pos = readClass(pos, buffer, this::setValidator);
        pos = readInt(pos, buffer, this::setLength);
        pos = readEnum(pos, buffer, this::setDatetime, DateMode.class);
        pos = readString(pos, buffer, this::setDateFormat);
        pos = readInt(pos, buffer, this::setPrecision);
        pos = readString(pos, buffer, this::setUnit);
        pos = readInt(pos, buffer, this::setMaxLength);
        pos = readInt(pos, buffer, this::setMinLength);
        pos = readLong(pos, buffer, this::setMax);
        pos = readLong(pos, buffer, this::setMin);
        pos = readBoolean(pos, buffer, this::setPrimaryKey);
        pos = readBoolean(pos, buffer, this::setUnique);
        pos = readBoolean(pos, buffer, this::setSubTable);
        pos = readBoolean(pos, buffer, this::setForeignKey);
        pos = readBoolean(pos, buffer, this::setNullable);
        pos = readString(pos, buffer, this::setColumnName);
        pos = readString(pos, buffer, this::setColumnType);
        pos = readString(pos, buffer, this::setRefTable);
        pos = readString(pos, buffer, this::setRefId);
        pos = readString(pos, buffer, this::setRefMetaId);
        return pos;
    }
    // ~ Get/Set =============================================

    /**
     * @return the uniqueId
     */
    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @param uniqueId
     *            the uniqueId to set
     */
    @Override
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
    @JsonSerialize(using = DataTypeSerializer.class)
    public DataType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    @JsonDeserialize(using = DataTypeDeserializer.class)
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
    public Class<?> getValidator() {
        return validator;
    }

    /**
     * @param validator
     *            the validator to set
     */
    public void setValidator(final Class<?> validator) {
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
    public DateMode getDatetime() {
        return datetime;
    }

    /**
     * @param datetime
     *            the datetime to set
     */
    public void setDatetime(final DateMode datetime) {
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
        return this.toJson().encode();
    }

    /** **/
    @Override
    public int hashCode() { // NOPMD
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
        result = prime * result + ((columnType == null) ? 0 : columnType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        return result;
    }

    /** **/
    @Override
    public boolean equals(final Object obj) { // NOPMD
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PEField other = (PEField) obj;
        if (columnName == null) {
            if (other.columnName != null) {
                return false;
            }
        } else if (!columnName.equals(other.columnName)) {
            return false;
        }
        if (columnType == null) {
            if (other.columnType != null) {
                return false;
            }
        } else if (!columnType.equals(other.columnType)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        if (uniqueId == null) {
            if (other.uniqueId != null) {
                return false;
            }
        } else if (!uniqueId.equals(other.uniqueId)) {
            return false;
        }
        return true;
    }
}
