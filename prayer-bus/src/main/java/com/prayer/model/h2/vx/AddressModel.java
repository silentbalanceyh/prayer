package com.prayer.model.h2.vx;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.constant.Constants;

/**
 * 对应表EVX_ADDRESS
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class AddressModel implements Serializable { // NOPMD

	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -1215877643355466773L;
	// ~ Instance Fields =====================================
	/** K_ID: EVX_ADDRESS表的主键 **/
	@JsonIgnore
	private String uniqueId;
	/** S_WORK_CLASS **/
	@JsonProperty("workClass")
	private String workClass;
	/** S_CONSUMER_ADDR **/
	@JsonProperty("consumerAddr")
	private String consumerAddr;
	/** S_CONSUMER_HANDLER **/
	@JsonProperty("consumerHandler")
	private String consumerHandler;
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
	 * @return the workClass
	 */
	public String getWorkClass() {
		return workClass;
	}

	/**
	 * @param workClass
	 *            the workClass to set
	 */
	public void setWorkClass(final String workClass) {
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
	public String getConsumerHandler() {
		return consumerHandler;
	}

	/**
	 * @param consumerHandler the consumerHandler to set
	 */
	public void setConsumerHandler(final String consumerHandler) {
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
		final AddressModel other = (AddressModel) obj;
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
