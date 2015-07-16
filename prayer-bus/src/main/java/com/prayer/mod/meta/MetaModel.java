package com.prayer.mod.meta; // NOPMD

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.constant.Constants;
import com.prayer.mod.SystemEnum.MetaCategory;
import com.prayer.mod.SystemEnum.MetaMapping;
import com.prayer.mod.SystemEnum.MetaPolicy;

/**
 * 对应表SYS_META
 *
 * @author Lang
 * @see
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class MetaModel implements Serializable { // NOPMD
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -1482546183811705597L;
	// ~ Instance Fields =====================================
	// !基础Meta数据-------------------------------------------
	/** K_ID：Meta表的主键 **/
	@JsonProperty("id")
	private String uniqueId;
	/** C_OOBDATA_FILE：OOB数据文件目录，CSV文件 **/
	@JsonIgnore
	private String oobFile;
	/** C_IN_USE：当前Model是否正在使用 **/
	@JsonIgnore
	private boolean using;

	// !Meta基本配置数据---------------------------------------
	/** S_NAME：Meta对应的Model名称 **/
	@JsonProperty("name")
	private String name;
	/** S_NAMESPACE：Meta对应的Namespace名空间 **/
	@JsonProperty("namespace")
	private String namespace;
	/** S_CATEGORY：Meta对应的类型 **/
	@JsonProperty("category")
	private MetaCategory category;
	/** S_GLOBAL_ID：Meta对应的当前Global全局标识符 **/
	@JsonProperty("identifier")
	private String globalId;
	/** S_MAPPING：Meta对应的Mapping类型 **/
	@JsonProperty("mapping")
	private MetaMapping mapping;
	/** S_POLICY：Meta对应的主键策略信息 **/
	@JsonProperty("policy")
	private MetaPolicy policy;

	// !Meta数据库配置信息-------------------------------------
	/** D_TABLE：数据库表名 **/
	@JsonProperty("table")
	private String table;
	/** D_SUB_TABLE：数据库子表名 **/
	@JsonProperty("subtable")
	private String subTable;
	/** D_SUB_KEY：数据库子表主键 **/
	@JsonProperty("subkey")
	private String subKey;
	/** D_SEQ_NAME：使用了序列的序列名，类似Oracle **/
	@JsonProperty("seqname")
	private String seqName;
	/** D_SEQ_STEP：自增长的梯度 **/
	@JsonProperty("seqstep")
	private int seqStep = 1;
	/** D_SEQ_INIT：自增长的初始值 **/
	@JsonProperty("seqinit")
	private int seqInit = 1;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public MetaModel() { // NOPMD
		// For Jackson Serializer
	}

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
	 * @return the oobFile
	 */
	public String getOobFile() {
		return oobFile;
	}

	/**
	 * @param oobFile
	 *            the oobFile to set
	 */
	public void setOobFile(final String oobFile) {
		this.oobFile = oobFile;
	}

	/**
	 * @return the using
	 */
	public boolean isUsing() {
		return using;
	}

	/**
	 * @param using
	 *            the using to set
	 */
	public void setUsing(final boolean using) {
		this.using = using;
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
	 * @return the category
	 */
	public MetaCategory getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(final MetaCategory category) {
		this.category = category;
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
	 * @return the mapping
	 */
	public MetaMapping getMapping() {
		return mapping;
	}

	/**
	 * @param mapping
	 *            the mapping to set
	 */
	public void setMapping(final MetaMapping mapping) {
		this.mapping = mapping;
	}

	/**
	 * @return the policy
	 */
	public MetaPolicy getPolicy() {
		return policy;
	}

	/**
	 * @param policy
	 *            the policy to set
	 */
	public void setPolicy(final MetaPolicy policy) {
		this.policy = policy;
	}

	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(final String table) {
		this.table = table;
	}

	/**
	 * @return the subTable
	 */
	public String getSubTable() {
		return subTable;
	}

	/**
	 * @param subTable
	 *            the subTable to set
	 */
	public void setSubTable(final String subTable) {
		this.subTable = subTable;
	}

	/**
	 * @return the subKey
	 */
	public String getSubKey() {
		return subKey;
	}

	/**
	 * @param subKey
	 *            the subKey to set
	 */
	public void setSubKey(final String subKey) {
		this.subKey = subKey;
	}

	/**
	 * @return the seqName
	 */
	public String getSeqName() {
		return seqName;
	}

	/**
	 * @param seqName
	 *            the seqName to set
	 */
	public void setSeqName(final String seqName) {
		this.seqName = seqName;
	}

	/**
	 * @return the seqStep
	 */
	public int getSeqStep() {
		return seqStep;
	}

	/**
	 * @param seqStep
	 *            the seqStep to set
	 */
	public void setSeqStep(final int seqStep) {
		this.seqStep = seqStep;
	}

	/**
	 * @return the seqInit
	 */
	public int getSeqInit() {
		return seqInit;
	}

	/**
	 * @param seqInit
	 *            the seqInit to set
	 */
	public void setSeqInit(final int seqInit) {
		this.seqInit = seqInit;
	}

	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return "MetaModel [uniqueId=" + uniqueId + ", oobFile=" + oobFile + ", using=" + using + ", name=" + name + ", namespace=" + namespace
				+ ", category=" + category + ", globalId=" + globalId + ", mapping=" + mapping + ", policy=" + policy
				+ ", table=" + table + ", subTable=" + subTable + ", subKey=" + subKey + ", seqName=" + seqName
				+ ", seqStep=" + seqStep + ", seqInit=" + seqInit + "]";
	}

	/** **/
	@Override
	public int hashCode() { // NOPMD
		final int prime = Constants.HASH_BASE;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((globalId == null) ? 0 : globalId.hashCode());
		result = prime * result + (using ? 1231 : 1237);
		result = prime * result + ((mapping == null) ? 0 : mapping.hashCode());
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
		final MetaModel other = (MetaModel) obj;
		if (category != other.category) {
			return false;
		}
		if (globalId == null) {
			if (other.globalId != null) {
				return false;
			}
		} else if (!globalId.equals(other.globalId)) {
			return false;
		}
		if (using != other.using) {
			return false;
		}
		if (mapping != other.mapping) {
			return false;
		}
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
