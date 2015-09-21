package com.prayer.model.h2.vx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ParamType;

import io.vertx.core.http.HttpMethod;

/**
 * 对应EVX_URI表
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class UriModel implements Serializable { // NOPMD
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -685942970952420895L;
    // ~ Instance Fields =====================================
    /** K_ID: EVX_URI表的主键 **/
    @JsonIgnore
    private String uniqueId;

    /** S_URI **/
    @JsonProperty("uri")
    private String uri;

    /** S_METHOD **/
    @JsonProperty("method")
    private HttpMethod method;

    /** S_PARAM_TYPE **/
    @JsonProperty("paramType")
    private ParamType paramType;

    /** S_REQUIRED_PARAM **/
    @JsonProperty("requiredParam")
    private List<String> requiredParam = new ArrayList<>();
    
    /** S_GLOBAL_ID **/
    @JsonProperty("globalId")
    private String globalId;
    
    /** MSG_ADDRESS **/
    @JsonProperty("address")
    private String address;
    
    /** S_SCRIPT **/
    @JsonProperty("script")
    private String script;
    
    /** L_RETURN_FILTERS **/
    @JsonProperty("returnFilters")
    private List<String> returnFilters = new ArrayList<>();
    
    /** S_SENDER **/
    @JsonProperty("sender")
    private String sender;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
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
     * @param address the address to set
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
     * @param globalId the globalId to set
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
     * @param script the script to set
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
     * @param returnFilters the returnFilters to set
     */
    public void setReturnFilters(final List<String> returnFilters) {
        this.returnFilters = returnFilters;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(final String sender) {
        this.sender = sender;
    }

    // ~ hashCode,equals,toString ============================
    /**
     * 
     */
    @Override
    public String toString() {
        return "UriModel [uniqueId=" + uniqueId + ", uri=" + uri + ", method=" + method + ", paramType=" + paramType
                + ", requiredParam=" + requiredParam + "]";
    }

    /**
     * 
     */
    @Override
    public int hashCode() {    // NOPMD
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
        final UriModel other = (UriModel) obj;
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
