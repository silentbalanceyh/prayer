package com.prayer.model.h2.vx;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.kernel.jackson.JsonObjectDeserializer;
import com.prayer.kernel.jackson.JsonObjectSerializer;
import com.prayer.model.jackson.DataTypeDeserializer;
import com.prayer.model.jackson.DataTypeSerializer;
import com.prayer.model.type.DataType;

import io.vertx.core.json.JsonObject;

/**
 * 对应EVX_PVRULE表
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class RuleModel implements Serializable { // NOPMD
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
	/** S_TYPE：对应的Lyra的数据类型 **/
	@JsonProperty("type")
	private DataType type;
	/** S_ORDER **/
	@JsonProperty("order")
	private int order;
	
	/** J_COMPONENT_TYPE **/
	@JsonProperty("componentType")
	private ComponentType componentType;
	/** J_COMPONENT_CLASS **/
	@JsonProperty("componentClass")
	private String componentClass;
	/** J_CONFIG **/
	@JsonProperty("config")
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private JsonObject config;		// NOPMD
	
	/** J_ERROR_MSG **/
	@JsonProperty("errorMessage")
	private String errorMessage;

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
	 * @return the type
	 */
	@JsonSerialize(using = DataTypeSerializer.class)
	public DataType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	@JsonDeserialize(using = DataTypeDeserializer.class)
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
	 * @param componentType the componentType to set
	 */
	public void setComponentType(final ComponentType componentType) {
		this.componentType = componentType;
	}

	/**
	 * @return the componentClass
	 */
	public String getComponentClass() {
		return componentClass;
	}

	/**
	 * @param componentClass the componentClass to set
	 */
	public void setComponentClass(final String componentClass) {
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
	 * @param errorMessage the errorMessage to set
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
		return "RuleModel [uniqueId=" + uniqueId + ", name=" + name + ", order=" + order + ", componentClass="
				+ componentClass + ", config=" + config + ", refUriId=" + refUriId + "]";
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
		final RuleModel other = (RuleModel) obj;
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
