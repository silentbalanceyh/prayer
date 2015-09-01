package com.prayer.model.h2.vx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.constant.Constants;

/**
 * 对应表EVX_VERTICLE
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class VerticleModel implements Serializable {	// NOPMD
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -1056134069321946140L;
	// ~ Instance Fields =====================================
	/** K_ID: EVX_VERTICLE表的主键 **/
	@JsonProperty("id")
	private String uniqueId;
	// ~ !基础配置数据 =======================================
	/** S_CLASS **/
	@JsonProperty("name")
	private String name;
	/** S_INSTANCES **/
	@JsonProperty("instances")
	private long instances = 1L;
	/** S_IGROUP **/
	@JsonProperty("group")
	private String group = null;	// NOPMD
	/** S_JSON_CONFIG **/
	@JsonProperty("jsonConfig")
	private String jsonConfig = Constants.EMPTY_JOBJ;	// NOPMD
	/** S_ISOLATED_CLASSES **/
	@JsonProperty("isolatedClasses")
	private List<String> isolatedClasses = new ArrayList<>();  // NOPMD
	// ~ !类路径配置数据 =====================================
	/** CP_EXT **/
	@JsonProperty("extraCp")
	private List<String> extraCp = new ArrayList<>();	// NOPMD

	// ~ !Boolean类型配置数据 ================================
	/** IS_HA **/
	@JsonProperty("ha")
	private boolean ha = false; // NOPMD
	/** IS_WORKER **/
	@JsonProperty("workder")
	private boolean worker = false;	// NOPMD
	/** IS_MULTI **/
	@JsonProperty("multi")
	private boolean multi = false; // NOPMD

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
	 * @param uniqueId the uniqueId to set
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
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the instances
	 */
	public long getInstances() {
		return instances;
	}

	/**
	 * @param instances the instances to set
	 */
	public void setInstances(final long instances) {
		this.instances = instances;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(final String group) {
		this.group = group;
	}

	/**
	 * @return the jsonConfig
	 */
	public String getJsonConfig() {
		return jsonConfig;
	}

	/**
	 * @param jsonConfig the jsonConfig to set
	 */
	public void setJsonConfig(final String jsonConfig) {
		this.jsonConfig = jsonConfig;
	}

	/**
	 * @return the isolatedClasses
	 */
	public List<String> getIsolatedClasses() {
		return isolatedClasses;
	}

	/**
	 * @param isolatedClasses the isolatedClasses to set
	 */
	public void setIsolatedClasses(final List<String> isolatedClasses) {
		this.isolatedClasses = isolatedClasses;
	}

	/**
	 * @return the extraCp
	 */
	public List<String> getExtraCp() {
		return extraCp;
	}

	/**
	 * @param extraCp the extraCp to set
	 */
	public void setExtraCp(final List<String> extraCp) {
		this.extraCp = extraCp;
	}

	/**
	 * @return the ha
	 */
	public boolean isHa() {
		return ha;
	}

	/**
	 * @param ha the ha to set
	 */
	public void setHa(final boolean ha) {	 // NOPMD
		this.ha = ha;
	}

	/**
	 * @return the worker
	 */
	public boolean isWorker() {
		return worker;
	}

	/**
	 * @param worker the worker to set
	 */
	public void setWorker(final boolean worker) {
		this.worker = worker;
	}

	/**
	 * @return the multi
	 */
	public boolean isMulti() {
		return multi;
	}

	/**
	 * @param multi the multi to set
	 */
	public void setMulti(final boolean multi) {
		this.multi = multi;
	}
	// ~ hashCode,equals,toString ============================
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "VerticleModel [uniqueId=" + uniqueId + ", name=" + name + ", instances=" + instances + ", group="
				+ group + ", jsonConfig=" + jsonConfig + ", isolatedClasses=" + isolatedClasses + ", extraCp=" + extraCp
				+ ", ha=" + ha + ", worker=" + worker + ", multi=" + multi + "]";
	}

	/** **/
	@Override
	public int hashCode() {	
		final int prime = Constants.HASH_BASE;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(final Object obj) {	// NOPMD
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		final VerticleModel other = (VerticleModel) obj;
		if (group == null) {
			if (other.group != null){
				return false;
			}
		} else if (!group.equals(other.group)){
			return false;
		}
		if (name == null) {
			if (other.name != null){
				return false;
			}
		} else if (!name.equals(other.name)){
			return false;
		}
		if (uniqueId == null) {
			if (other.uniqueId != null){
				return false;
			}
		} else if (!uniqueId.equals(other.uniqueId)){
			return false;
		}
		return true;
	}
}
