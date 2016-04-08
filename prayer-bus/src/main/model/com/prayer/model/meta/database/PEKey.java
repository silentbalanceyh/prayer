package com.prayer.model.meta.database;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.entity.Attributes;
import com.prayer.fantasm.model.AbstractEntity;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表SYS_KEYS
 *
 * @author Lang
 * @see
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PEKey extends AbstractEntity<String> { // NOPMD
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -2090226950871844055L;
    // ~ Instance Fields =====================================
    /** K_ID: Keys表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** S_NAME: Keys表的系统键的名字 **/
    @JsonProperty(NAME)
    private String name;
    /** S_CATEGORY：键类型 **/
    @JsonProperty(CATEGORY)
    private KeyCategory category;
    /** S_COLUMNS：当前键中包含的列信息 **/
    @JsonProperty(COLUMNS)
    private List<String> columns;
    /** IS_MULTI：是否跨字段 **/
    @JsonProperty(MULTI)
    private boolean multi;

    /** R_META_ID：外键约束，关联SYS_META **/
    @JsonProperty(REF_MID)
    private String refMetaId;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PEKey() {
    }

    /** **/
    public PEKey(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PEKey(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 写入Json **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeString(data, NAME, this::getName);
        writeEnum(data, CATEGORY, this::getCategory);
        writeList(data, COLUMNS, this::getColumns);
        writeBoolean(data, MULTI, this::isMulti);
        writeString(data, REF_MID, this::getRefMetaId);
        return data;
    }

    /** 从Json中读取数据 **/
    @Override
    public PEKey fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, NAME, this::setName);
        readEnum(data, CATEGORY, this::setCategory, KeyCategory.class);
        readList(data, COLUMNS, this::setColumns);
        readBoolean(data, MULTI, this::setMulti);
        readString(data, REF_MID, this::setRefMetaId);
        return this;
    }

    /** 写入Buffer **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getName);
        writeEnum(buffer, this::getCategory);
        writeList(buffer, this::getColumns);
        writeBoolean(buffer, this::isMulti);
        writeString(buffer, this::getRefMetaId);
    }

    /** 从Buffer中读取 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setName);
        pos = readEnum(pos, buffer, this::setCategory, KeyCategory.class);
        pos = readList(pos, buffer, this::setColumns);
        pos = readBoolean(pos, buffer, this::setMulti);
        pos = readString(pos, buffer, this::setRefMetaId);
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
     * @return the category
     */
    public KeyCategory getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(final KeyCategory category) {
        this.category = category;
    }

    /**
     * @return the columns
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * @param columns
     *            the columns to set
     */
    public void setColumns(final List<String> columns) {
        this.columns = columns;
    }

    /**
     * @return the multi
     */
    public boolean isMulti() {
        return multi;
    }

    /**
     * @param multi
     *            the multi to set
     */
    public void setMulti(final boolean multi) {
        this.multi = multi;
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
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + (multi ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((refMetaId == null) ? 0 : refMetaId.hashCode());
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
        final PEKey other = (PEKey) obj;
        if (category != other.category) {
            return false;
        }
        if (multi != other.multi) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (refMetaId == null) {
            if (other.refMetaId != null) {
                return false;
            }
        } else if (!refMetaId.equals(other.refMetaId)) {
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
