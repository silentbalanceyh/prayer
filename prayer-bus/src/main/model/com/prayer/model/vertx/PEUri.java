package com.prayer.model.vertx;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.base.model.AbstractEntity;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.facade.entity.Attributes;
import com.prayer.plugin.jackson.ClassDeserializer;
import com.prayer.plugin.jackson.ClassSerializer;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

/**
 * 对应EVX_URI表
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PEUri extends AbstractEntity<String> { // NOPMD
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -685942970952420895L;
    // ~ Instance Fields =====================================
    /** K_ID: EVX_URI表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;

    /** S_URI **/
    @JsonProperty(URI)
    private String uri;

    /** S_METHOD **/
    @JsonProperty(METHOD)
    private HttpMethod method;

    /** S_PARAM_TYPE **/
    @JsonProperty(PARAM_TYPE)
    private ParamType paramType;

    /** S_REQUIRED_PARAM **/
    @JsonProperty(REQUIRED_PARAM)
    private List<String> requiredParam = new ArrayList<>();

    /** S_GLOBAL_ID **/
    @JsonProperty(GLOBAL_ID)
    private String globalId;

    /** MSG_ADDRESS **/
    @JsonProperty(ADDRESS)
    private String address;

    /** S_SCRIPT **/
    @JsonProperty(SCRIPT)
    private String script;

    /** L_RETURN_FILTERS **/
    @JsonProperty(RETURN_FILTERS)
    private List<String> returnFilters = new ArrayList<>();

    /** S_SENDER **/
    @JsonProperty(SENDER)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> sender;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PEUri() {
    }

    /** **/
    public PEUri(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PEUri(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 写入Json **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeString(data, URI, this::getUri);
        writeEnum(data, METHOD, this::getMethod);
        writeEnum(data, PARAM_TYPE, this::getParamType);
        writeList(data, REQUIRED_PARAM, this::getRequiredParam);
        writeString(data, GLOBAL_ID, this::getGlobalId);
        writeString(data, ADDRESS, this::getAddress);
        writeString(data, SCRIPT, this::getScript);
        writeList(data, RETURN_FILTERS, this::getReturnFilters);
        writeClass(data, SENDER, this::getSender);
        return data;
    }

    /** 从Json中读取数据 **/
    @Override
    public PEUri fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, ID, this::setUniqueId);
        readString(data, URI, this::setUri);
        readEnum(data, METHOD, this::setMethod, HttpMethod.class);
        readEnum(data, PARAM_TYPE, this::setParamType, ParamType.class);
        readList(data, REQUIRED_PARAM, this::setRequiredParam);
        readString(data, GLOBAL_ID, this::setGlobalId);
        readString(data, ADDRESS, this::setAddress);
        readString(data, SCRIPT, this::setScript);
        readList(data, RETURN_FILTERS, this::setReturnFilters);
        readClass(data, SENDER, this::setSender);
        return this;
    }

    /** 写入Buffer **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getUri);
        writeEnum(buffer, this::getMethod);
        writeEnum(buffer, this::getParamType);
        writeList(buffer, this::getRequiredParam);
        writeString(buffer, this::getGlobalId);
        writeString(buffer, this::getAddress);
        writeString(buffer, this::getScript);
        writeList(buffer, this::getReturnFilters);
        writeClass(buffer, this::getSender);
    }

    /** 从Buffer中读取 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setUri);
        pos = readEnum(pos, buffer, this::setMethod, HttpMethod.class);
        pos = readEnum(pos, buffer, this::setParamType, ParamType.class);
        pos = readList(pos, buffer, this::setRequiredParam);
        pos = readString(pos, buffer, this::setGlobalId);
        pos = readString(pos, buffer, this::setAddress);
        pos = readString(pos, buffer, this::setScript);
        pos = readList(pos, buffer, this::setReturnFilters);
        pos = readClass(pos, buffer, this::setSender);
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
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(final String uri) {
        this.uri = uri;
    }

    /**
     * @return the method
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * @param method
     *            the method to set
     */
    public void setMethod(final HttpMethod method) {
        this.method = method;
    }

    /**
     * @return the paramType
     */
    public ParamType getParamType() {
        return paramType;
    }

    /**
     * @param paramType
     *            the paramType to set
     */
    public void setParamType(final ParamType paramType) {
        this.paramType = paramType;
    }

    /**
     * @return the requiredParam
     */
    public List<String> getRequiredParam() {
        return requiredParam;
    }

    /**
     * @param requiredParam
     *            the requiredParam to set
     */
    public void setRequiredParam(final List<String> requiredParam) {
        this.requiredParam = requiredParam;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * @return the globalId
     */
    public String getGlobalId() {
        return globalId;
    }

    /**
     * @param globalId
     *            the globalId to set
     */
    public void setGlobalId(final String globalId) {
        this.globalId = globalId;
    }

    /**
     * @return the script
     */
    public String getScript() {
        return script;
    }

    /**
     * @param script
     *            the script to set
     */
    public void setScript(final String script) {
        this.script = script;
    }

    /**
     * @return the returnFilters
     */
    public List<String> getReturnFilters() {
        return returnFilters;
    }

    /**
     * @param returnFilters
     *            the returnFilters to set
     */
    public void setReturnFilters(final List<String> returnFilters) {
        this.returnFilters = returnFilters;
    }

    /**
     * @return the sender
     */
    public Class<?> getSender() {
        return sender;
    }

    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(final Class<?> sender) {
        this.sender = sender;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
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
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((paramType == null) ? 0 : paramType.hashCode());
        result = prime * result + ((requiredParam == null) ? 0 : requiredParam.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
        final PEUri other = (PEUri) obj;
        if (paramType != other.paramType) {
            return false;
        }
        if (method == null) {
            if (other.method != null) {
                return false;
            }
        } else if (!method.equals(other.method)) {
            return false;
        }
        if (requiredParam == null) {
            if (other.requiredParam != null) {
                return false;
            }
        } else if (!requiredParam.equals(other.requiredParam)) {
            return false;
        }
        if (uniqueId == null) {
            if (other.uniqueId != null) {
                return false;
            }
        } else if (!uniqueId.equals(other.uniqueId)) {
            return false;
        }
        if (uri == null) {
            if (other.uri != null) {
                return false;
            }
        } else if (!uri.equals(other.uri)) {
            return false;
        }
        return true;
    }
}
