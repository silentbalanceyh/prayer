package com.prayer.model.h2.vx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.kernel.jackson.JsonObjectDeserializer;
import com.prayer.kernel.jackson.JsonObjectSerializer;

import io.vertx.core.json.JsonObject;

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

	/** S_PARAM_TYPE **/
	@JsonProperty("paramType")
	private ParamType paramType;

	/** S_REQUIRED_PARAM **/
	@JsonProperty("requiredParam")
	private List<String> requiredParam = new ArrayList<>();

	/** J_CONVERTORS **/
	@JsonProperty("convertors")
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private JsonObject convertors;	// NOPMD

	/** J_VALIDATORS **/
	@JsonProperty("validators")
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private JsonObject validators;	// NOPMD
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
	 * @return the convertors
	 */
	public JsonObject getConvertors() {
		return convertors;
	}

	/**
	 * @param convertors
	 *            the convertors to set
	 */
	public void setConvertors(final JsonObject convertors) {
		this.convertors = convertors;
	}

	/**
	 * @return the validators
	 */
	public JsonObject getValidators() {
		return validators;
	}

	/**
	 * @param validators
	 *            the validators to set
	 */
	public void setValidators(final JsonObject validators) {
		this.validators = validators;
	}

	// ~ hashCode,equals,toString ============================

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "UriModel [uniqueId=" + uniqueId + ", uri=" + uri + ", paramType=" + paramType + ", requiredParam="
				+ requiredParam + ", convertors=" + convertors + ", validators=" + validators + "]";
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = Constants.HASH_BASE;
		int result = 1;
		result = prime * result + ((paramType == null) ? 0 : paramType.hashCode());
		result = prime * result + ((requiredParam == null) ? 0 : requiredParam.hashCode());
		result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	/** **/
	@Override
	public boolean equals(final Object obj) { // NOPMD
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UriModel other = (UriModel) obj;
		if (paramType != other.paramType)
			return false;
		if (requiredParam == null) {
			if (other.requiredParam != null)
				return false;
		} else if (!requiredParam.equals(other.requiredParam))
			return false;
		if (uniqueId == null) {
			if (other.uniqueId != null)
				return false;
		} else if (!uniqueId.equals(other.uniqueId))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

}
