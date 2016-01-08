package com.prayer.model.vertx;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.base.model.AbstractEntity;
import com.prayer.constant.Constants;
import com.prayer.plugin.jackson.ClassDeserializer;
import com.prayer.plugin.jackson.ClassSerializer;
import com.prayer.util.io.JsonKit;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表EVX_ADDRESS
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class PEAddress extends AbstractEntity { // NOPMD

    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1215877643355466773L;
    // ~ Instance Fields =====================================
    /** K_ID: EVX_ADDRESS表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** S_WORK_CLASS **/
    @JsonProperty(WORK_CLASS)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> workClass;
    /** S_CONSUMER_ADDR **/
    @JsonProperty(CONSUMER_ADDR)
    private String consumerAddr;
    /** S_CONSUMER_HANDLER **/
    @JsonProperty(CONSUMER_HANDLER)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> consumerHandler;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Vert.X Serialization ================================
    /** 写入Buffer **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeClass(buffer, this::getWorkClass);
        writeString(buffer, this::getConsumerAddr);
        writeClass(buffer, this::getConsumerHandler);
    }

    /** 从Buffer中读取 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos += readString(pos, buffer, this::setUniqueId);
        pos += readClass(pos, buffer, this::setWorkClass);
        pos += readString(pos, buffer, this::setConsumerAddr);
        pos += readClass(pos, buffer, this::setConsumerHandler);
        return pos;
    }
    
    // ~ Entity Json/Buffer Serialization ====================
    
    
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
     * @return the workClass
     */
    public Class<?> getWorkClass() {
        return workClass;
    }

    /**
     * @param workClass
     *            the workClass to set
     */
    public void setWorkClass(final Class<?> workClass) {
        this.workClass = workClass;
    }

    /**
     * @return the consumerAddr
     */
    public String getConsumerAddr() {
        return consumerAddr;
    }

    /**
     * @param consumerAddr
     *            the consumerAddr to set
     */
    public void setConsumerAddr(final String consumerAddr) {
        this.consumerAddr = consumerAddr;
    }

    /**
     * @return the consumerHandler
     */
    public Class<?> getConsumerHandler() {
        return consumerHandler;
    }

    /**
     * @param consumerHandler
     *            the consumerHandler to set
     */
    public void setConsumerHandler(final Class<?> consumerHandler) {
        this.consumerHandler = consumerHandler;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public int hashCode() {
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((consumerAddr == null) ? 0 : consumerAddr.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        result = prime * result + ((workClass == null) ? 0 : workClass.hashCode());
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
        final PEAddress other = (PEAddress) obj;
        if (consumerAddr == null) {
            if (other.consumerAddr != null) {
                return false;
            }
        } else if (!consumerAddr.equals(other.consumerAddr)) {
            return false;
        }
        if (uniqueId == null) {
            if (other.uniqueId != null) {
                return false;
            }
        } else if (!uniqueId.equals(other.uniqueId)) {
            return false;
        }
        if (workClass == null) {
            if (other.workClass != null) {
                return false;
            }
        } else if (!workClass.equals(other.workClass)) {
            return false;
        }
        return true;
    }
}
