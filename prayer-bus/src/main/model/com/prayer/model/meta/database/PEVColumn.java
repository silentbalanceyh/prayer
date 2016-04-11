package com.prayer.model.meta.database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.entity.Attributes;
import com.prayer.fantasm.model.AbstractEntity;
import com.prayer.model.type.DataType;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表SYS_VCOLUMNS
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PEVColumn extends AbstractEntity<String> { // NOPMD
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 9027079347277641161L;
    // ~ Instance Fields =====================================
    /** K_ID: Column表主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** S_NAME **/
    @JsonProperty(NAME)
    private String name;
    /** S_TYPE **/
    @JsonProperty(TYPE)
    private DataType type;
    /** D_COLUMN_NAME **/
    @JsonProperty(COLUMN_NAME)
    private String columnName;
    /** D_COLUMN_TYPE **/
    @JsonProperty(COLUMN_TYPE)
    private String columnType;
    /** R_VIEW_ID **/
    @JsonProperty(REF_VID)
    private String refViewId;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PEVColumn() {
    }

    /** **/
    public PEVColumn(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PEVColumn(final Buffer buffer) {
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
        writeString(data, COLUMN_NAME, this::getColumnName);
        writeString(data, COLUMN_TYPE, this::getColumnType);
        writeString(data, REF_VID, this::getRefViewId);
        return data;
    }

    /** **/
    @Override
    public PEVColumn fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, NAME, this::setName);
        readEnum(data, TYPE, this::setType, DataType.class);
        readString(data, COLUMN_NAME, this::setColumnName);
        readString(data, COLUMN_TYPE, this::setColumnType);
        readString(data, REF_VID, this::setRefViewId);
        return this;
    }

    /** **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getName);
        writeEnum(buffer, this::getType);
        writeString(buffer, this::getColumnName);
        writeString(buffer, this::getColumnType);
        writeString(buffer, this::getRefViewId);
    }

    /** **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setName);
        pos = readEnum(pos, buffer, this::setType, DataType.class);
        pos = readString(pos, buffer, this::setColumnName);
        pos = readString(pos, buffer, this::setColumnType);
        pos = readString(pos, buffer, this::setRefViewId);
        return pos;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
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
     * @return the refViewId
     */
    public String getRefViewId() {
        return refViewId;
    }

    /**
     * @param refViewId
     *            the refViewId to set
     */
    public void setRefViewId(final String refViewId) {
        this.refViewId = refViewId;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString(){
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
        result = prime * result + ((refViewId == null) ? 0 : refViewId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        return result;
    }

    /** **/
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PEVColumn other = (PEVColumn) obj;
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
        if (refViewId == null) {
            if (other.refViewId != null) {
                return false;
            }
        } else if (!refViewId.equals(other.refViewId)) {
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
