package com.prayer.model.meta.vertx;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.entity.Attributes;
import com.prayer.fantasm.model.AbstractEntity;
import com.prayer.model.type.DataType;
import com.prayer.plugin.jackson.ClassDeserializer;
import com.prayer.plugin.jackson.ClassSerializer;
import com.prayer.plugin.jackson.DataTypeDeserializer;
import com.prayer.plugin.jackson.DataTypeSerializer;
import com.prayer.plugin.jackson.JsonObjectDeserializer;
import com.prayer.plugin.jackson.JsonObjectSerializer;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应EVX_PVRULE表
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PERule extends AbstractEntity<String> { // NOPMD
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = -1136964904940513471L;

    // ~ Instance Fields =====================================
    /** K_ID: EVX_PVRULE表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** S_NAME **/
    @JsonProperty(NAME)
    private String name;
    /** S_TYPE：对应的Lyra的数据类型 **/
    @JsonProperty(TYPE)
    @JsonSerialize(using = DataTypeSerializer.class)
    @JsonDeserialize(using = DataTypeDeserializer.class)
    private DataType type;
    /** S_ORDER **/
    @JsonProperty(ORDER)
    private int order;

    /** J_COMPONENT_TYPE **/
    @JsonProperty(COMPONENT_TYPE)
    private ComponentType componentType;
    /** J_COMPONENT_CLASS **/
    @JsonProperty(COMPONENT_CLASS)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> componentClass;
    /** J_CONFIG **/
    @JsonProperty(CONFIG)
    @JsonSerialize(using = JsonObjectSerializer.class)
    @JsonDeserialize(using = JsonObjectDeserializer.class)
    private JsonObject config; // NOPMD

    /** J_ERROR_MSG **/
    @JsonProperty(ERROR_MESSAGE)
    private String errorMessage;

    /** R_URI_ID **/
    @JsonProperty(REF_UID)
    private String refUriId;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PERule() {
    }

    /** **/
    public PERule(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PERule(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeString(data, NAME, this::getName);
        writeEnum(data, TYPE, this::getType);
        writeInt(data, ORDER, this::getOrder);
        writeEnum(data, COMPONENT_TYPE, this::getComponentType);
        writeClass(data, COMPONENT_CLASS, this::getComponentClass);
        writeJObject(data, CONFIG, this::getConfig);
        writeString(data, ERROR_MESSAGE, this::getErrorMessage);
        writeString(data, REF_UID, this::getRefUriId);
        return data;
    }

    /** **/
    @Override
    public PERule fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, NAME, this::setName);
        readEnum(data, TYPE, this::setType, DataType.class);
        readInt(data, ORDER, this::setOrder);
        readEnum(data, COMPONENT_TYPE, this::setComponentType, ComponentType.class);
        readClass(data, COMPONENT_CLASS, this::setComponentClass);
        readJObject(data, CONFIG, this::setConfig);
        readString(data, ERROR_MESSAGE, this::setErrorMessage);
        readString(data, REF_UID, this::setRefUriId);
        return this;
    }

    /** 写入Buffer **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getName);
        writeEnum(buffer, this::getType);
        writeInt(buffer, this::getOrder);
        writeEnum(buffer, this::getComponentType);
        writeClass(buffer, this::getComponentClass);
        writeJObject(buffer, this::getConfig);
        writeString(buffer, this::getErrorMessage);
        writeString(buffer, this::getRefUriId);
    }

    /** 从Buffer中读取 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setName);
        pos = readEnum(pos, buffer, this::setType, DataType.class);
        pos = readInt(pos, buffer, this::setOrder);
        pos = readEnum(pos, buffer, this::setComponentType, ComponentType.class);
        pos = readClass(pos, buffer, this::setComponentClass);
        pos = readJObject(pos, buffer, this::setConfig);
        pos = readString(pos, buffer, this::setErrorMessage);
        pos = readString(pos, buffer, this::setRefUriId);
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
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(final int order) {
        this.order = order;
    }

    /**
     * @return the componentType
     */
    public ComponentType getComponentType() {
        return componentType;
    }

    /**
     * @param componentType
     *            the componentType to set
     */
    public void setComponentType(final ComponentType componentType) {
        this.componentType = componentType;
    }

    /**
     * @return the componentClass
     */
    public Class<?> getComponentClass() {
        return componentClass;
    }

    /**
     * @param componentClass
     *            the componentClass to set
     */
    public void setComponentClass(final Class<?> componentClass) {
        this.componentClass = componentClass;
    }

    /**
     * @return the config
     */
    public JsonObject getConfig() {
        return config;
    }

    /**
     * @param config
     *            the config to set
     */
    public void setConfig(final JsonObject config) {
        this.config = config;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the refUriId
     */
    public String getRefUriId() {
        return refUriId;
    }

    /**
     * @param refUriId
     *            the refUriId to set
     */
    public void setRefUriId(final String refUriId) {
        this.refUriId = refUriId;
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((refUriId == null) ? 0 : refUriId.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        result = prime * result + ((componentClass == null) ? 0 : componentClass.hashCode());
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
        final PERule other = (PERule) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (refUriId == null) {
            if (other.refUriId != null) {
                return false;
            }
        } else if (!refUriId.equals(other.refUriId)) {
            return false;
        }
        if (uniqueId == null) {
            if (other.uniqueId != null) {
                return false;
            }
        } else if (!uniqueId.equals(other.uniqueId)) {
            return false;
        }
        if (componentClass == null) {
            if (other.componentClass != null) {
                return false;
            }
        } else if (!componentClass.equals(other.componentClass)) {
            return false;
        }
        return true;
    }
}
