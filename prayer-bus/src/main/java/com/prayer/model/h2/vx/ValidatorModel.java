package com.prayer.model.h2.vx;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.constant.Constants;
import com.prayer.kernel.jackson.JsonObjectDeserializer;
import com.prayer.kernel.jackson.JsonObjectSerializer;

import io.vertx.core.json.JsonObject;

/**
 * 对应EVX_PVRULE表
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class ValidatorModel implements Serializable { // NOPMD
	// ~ Static Fields =======================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -1136964904940513471L;

	// ~ Instance Fields =====================================
	/** K_ID: EVX_PVRULE表的主键 **/
	@JsonIgnore
	private String uniqueId;
	/** S_NAME **/
	@JsonProperty("name")
	private String name;
	/** S_ORDER **/
	@JsonProperty("order")
	private int order;

	/** J_VALIDATOR **/
	@JsonProperty("validator")
	private String validator;
	/** J_CONFIG **/
	@JsonProperty("config")
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private JsonObject config;		// NOPMD

	/** R_URI_ID **/
	@JsonIgnore
	private String refUriId;
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
	 * @return the validator
	 */
	public String getValidator() {
		return validator;
	}

	/**
	 * @param validator
	 *            the validator to set
	 */
	public void setValidator(final String validator) {
		this.validator = validator;
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
		return "ValidatorModel [uniqueId=" + uniqueId + ", name=" + name + ", order=" + order + ", validator="
				+ validator + ", config=" + config + ", refUriId=" + refUriId + "]";
	}

	/** **/
	@Override
	public int hashCode() { // NOPMD
		final int prime = Constants.HASH_BASE;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((refUriId == null) ? 0 : refUriId.hashCode());
		result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
		result = prime * result + ((validator == null) ? 0 : validator.hashCode());
		return result;
	}

	/** **/
	@Override
	public boolean equals(final Object obj) {	// NOPMD
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ValidatorModel other = (ValidatorModel) obj;
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
		if (validator == null) {
			if (other.validator != null) {
				return false;
			}
		} else if (!validator.equals(other.validator)) {
			return false;
		}
		return true;
	}
}
