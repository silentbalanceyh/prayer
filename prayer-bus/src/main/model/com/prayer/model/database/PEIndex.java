package com.prayer.model.database;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.base.model.AbstractEntity;
import com.prayer.constant.Constants;
import com.prayer.facade.entity.Attributes;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表SYS_INDEX
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PEIndex extends AbstractEntity {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 655221281383396821L;
    // ~ Instance Fields =====================================
    /** K_ID: Index表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** D_NAME：Index的名称，数据库的索引名 **/
    @JsonProperty(NAME)
    private String name;
    /** D_COLUMNS：Index中的列数据 **/
    @JsonProperty(COLUMNS)
    private List<JsonObject> columns;
    /** R_REF_ID: Index中的 关联非外键关联字段 **/
    @JsonProperty(REF_ID)
    private String refId;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PEIndex() {
    }

    /** **/
    public PEIndex(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PEIndex(final Buffer buffer) {
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
        writeList(data, COLUMNS, this::getColumns);
        writeString(data, REF_ID, this::getRefId);
        return data;
    }

    /** **/
    @Override
    public PEIndex fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, NAME, this::setName);
        readList(data, COLUMNS, this::setColumns);
        readString(data, REF_ID, this::setRefId);
        return this;
    }

    /** **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getName);
        writeList(buffer, this::getColumns);
        writeString(buffer, this::getRefId);
    }

    /** **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setName);
        pos = readList(pos, buffer, this::setColumns);
        pos = readString(pos, buffer, this::setRefId);
        return pos;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
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
     * @return the columns
     */
    public List<JsonObject> getColumns() {
        return columns;
    }

    /**
     * @param columns
     *            the columns to set
     */
    public void setColumns(final List<JsonObject> columns) {
        this.columns = columns;
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

    // ~ hashCode,equals,toString ============================
    /**
     * 
     */
    @Override
    public String toString() {
        return this.toJson().encode();
    }

    /**
     * 
     */
    @Override
    public int hashCode() { // NOPMD
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((columns == null) ? 0 : columns.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((refId == null) ? 0 : refId.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        return result;
    }

    /**
     * 
     */
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
        final PEIndex other = (PEIndex) obj;
        if (columns == null) {
            if (other.columns != null) {
                return false;
            }
        } else if (!columns.equals(other.columns)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (refId == null) {
            if (other.refId != null) {
                return false;
            }
        } else if (!refId.equals(other.refId)) {
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
