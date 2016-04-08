package com.prayer.model.meta.vertx;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.entity.Attributes;
import com.prayer.fantasm.model.AbstractEntity;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PEScript extends AbstractEntity<String> { // NOPMD
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -4378311650545473656L;
    // ~ Instance Fields =====================================
    /** K_ID: ENG_SCRIPT表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** S_NAME：脚本名称 **/
    @JsonProperty(NAME)
    private String name;
    /** S_NAMESPACE：脚本名空间 **/
    @JsonProperty(NAMESPACE)
    private String namespace;
    /** S_CONTENT **/
    @JsonProperty(CONTENT)
    private String content;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 无参构造函数 **/
    public PEScript() {
    }

    /** 使用JsonObject构造PEScript **/
    public PEScript(final JsonObject data) {
        this.fromJson(data);
    }

    /** 使用Buffer构造PEScript **/
    public PEScript(final Buffer data) {
        this.readFromBuffer(Constants.POS, data);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Vert.X Serialization ================================
    /** 写入Buffer **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getName);
        writeString(buffer, this::getNamespace);
        writeString(buffer, this::getContent);
    }

    /** 从Buffer中读取 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setName);
        pos = readString(pos, buffer, this::setNamespace);
        pos = readString(pos, buffer, this::setContent);
        return pos;
    }

    // ~ Entity Json/Buffer Serialization ====================
    /** 转成Json **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeString(data, NAME, this::getName);
        writeString(data, NAMESPACE, this::getNamespace);
        writeString(data, CONTENT, this::getContent);
        return data;
    }

    /** 从Json反序列化 **/
    @Override
    public PEScript fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, NAME, this::setName);
        readString(data, NAMESPACE, this::setNamespace);
        readString(data, CONTENT, this::setContent);
        return this;
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
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace
     *            the namespace to set
     */
    public void setNamespace(final String namespace) {
        this.namespace = namespace;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(final String content) {
        this.content = content;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return this.toJson().encode();
    }

    /** **/
    @Override
    public int hashCode() {
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
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
        final PEScript other = (PEScript) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (namespace == null) {
            if (other.namespace != null) {
                return false;
            }
        } else if (!namespace.equals(other.namespace)) {
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
