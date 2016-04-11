package com.prayer.model.meta.database; // NOPMD

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.constant.SystemEnum.Category;
import com.prayer.constant.SystemEnum.Mapping;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.constant.SystemEnum.Status;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.entity.Attributes;
import com.prayer.fantasm.model.AbstractEntity;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表SYS_META
 *
 * @author Lang
 * @see
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PEMeta extends AbstractEntity<String> { // NOPMD
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1482546183811705597L;
    // ~ Instance Fields =====================================
    // !基础Meta数据-------------------------------------------
    /** K_ID：Meta表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;

    /** S_STATUS：Meta的状态 **/
    @JsonProperty(STATUS)
    private Status status;

    // !Meta基本配置数据---------------------------------------
    /** S_NAME：Meta对应的Model名称 **/
    @JsonProperty(NAME)
    private String name;
    /** S_NAMESPACE：Meta对应的Namespace名空间 **/
    @JsonProperty(NAMESPACE)
    private String namespace;
    /** S_CATEGORY：Meta对应的类型 **/
    @JsonProperty(CATEGORY)
    private Category category;
    /** S_GLOBAL_ID：Meta对应的当前Global全局标识符 **/
    @JsonProperty(IDENTIFIER)
    private String globalId;
    /** S_MAPPING：Meta对应的Mapping类型 **/
    @JsonProperty(MAPPING)
    private Mapping mapping;
    /** S_POLICY：Meta对应的主键策略信息 **/
    @JsonProperty(POLICY)
    private MetaPolicy policy;

    // !Meta数据库配置信息-------------------------------------
    /** D_TABLE：数据库表名 **/
    @JsonProperty(TABLE)
    private String table;
    /** D_SUB_TABLE：数据库子表名 **/
    @JsonProperty(SUB_TABLE)
    private String subTable;
    /** D_SUB_KEY：数据库子表主键 **/
    @JsonProperty(SUB_KEY)
    private String subKey;
    /** D_SEQ_NAME：使用了序列的序列名，类似Oracle **/
    @JsonProperty(SEQ_NAME)
    private String seqName;
    /** D_SEQ_STEP：自增长的梯度 **/
    @JsonProperty(SEQ_STEP)
    private int seqStep = 1;
    /** D_SEQ_INIT：自增长的初始值 **/
    @JsonProperty(SEQ_INIT)
    private int seqInit = 1;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PEMeta() { // NOPMD
    }

    /** **/
    public PEMeta(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PEMeta(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 写入Json **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeEnum(data, STATUS, this::getStatus);
        writeString(data, NAME, this::getName);
        writeString(data, NAMESPACE, this::getNamespace);
        writeEnum(data, CATEGORY, this::getCategory);
        writeString(data, IDENTIFIER, this::getGlobalId);
        writeEnum(data, MAPPING, this::getMapping);
        writeEnum(data, POLICY, this::getPolicy);
        writeString(data, TABLE, this::getTable);
        writeString(data, SUB_TABLE, this::getSubTable);
        writeString(data, SUB_KEY, this::getSubKey);
        writeString(data, SEQ_NAME, this::getSeqName);
        writeInt(data, SEQ_STEP, this::getSeqStep);
        writeInt(data, SEQ_INIT, this::getSeqInit);
        return data;
    }

    /** 从Json中读取数据 **/
    @Override
    public PEMeta fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readEnum(data, STATUS, this::setStatus, Status.class);
        readString(data, NAME, this::setName);
        readString(data, NAMESPACE, this::setNamespace);
        readEnum(data, CATEGORY, this::setCategory, Category.class);
        readString(data, IDENTIFIER, this::setGlobalId);
        readEnum(data, MAPPING, this::setMapping, Mapping.class);
        readEnum(data, POLICY, this::setPolicy, MetaPolicy.class);
        readString(data, TABLE, this::setTable);
        readString(data, SUB_TABLE, this::setSubTable);
        readString(data, SUB_KEY, this::setSubKey);
        readString(data, SEQ_NAME, this::setSeqName);
        readInt(data, SEQ_STEP, this::setSeqStep);
        readInt(data, SEQ_INIT, this::setSeqInit);
        return this;
    }
    /** 写入Buffer **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer,this::getUniqueId);
        writeEnum(buffer,this::getStatus);
        writeString(buffer,this::getName);
        writeString(buffer,this::getNamespace);
        writeEnum(buffer,this::getCategory);
        writeString(buffer,this::getGlobalId);
        writeEnum(buffer,this::getMapping);
        writeEnum(buffer,this::getPolicy);
        writeString(buffer,this::getTable);
        writeString(buffer,this::getSubTable);
        writeString(buffer,this::getSubKey);
        writeString(buffer,this::getSeqName);
        writeInt(buffer,this::getSeqStep);
        writeInt(buffer,this::getSeqInit);
    }
    /** 从Buffer中读取 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer,this::setUniqueId);
        pos = readEnum(pos, buffer, this::setStatus,Status.class);
        pos = readString(pos, buffer, this::setName);
        pos = readString(pos, buffer, this::setNamespace);
        pos = readEnum(pos, buffer, this::setCategory,Category.class);
        pos = readString(pos, buffer, this::setGlobalId);
        pos = readEnum(pos, buffer, this::setMapping, Mapping.class);
        pos = readEnum(pos, buffer, this::setPolicy, MetaPolicy.class);
        pos = readString(pos, buffer, this::setTable);
        pos = readString(pos, buffer, this::setSubTable);
        pos = readString(pos, buffer, this::setSubKey);
        pos = readString(pos, buffer, this::setSeqName);
        pos = readInt(pos, buffer, this::setSeqStep);
        pos = readInt(pos, buffer, this::setSeqInit);
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
    public Category getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(final Category category) {
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
    public Mapping getMapping() {
        return mapping;
    }

    /**
     * @param mapping
     *            the mapping to set
     */
    public void setMapping(final Mapping mapping) {
        this.mapping = mapping;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(final Status status) {
        this.status = status;
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
        return this.toJson().encode();
    }

    /** **/
    @Override
    public int hashCode() { // NOPMD
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((globalId == null) ? 0 : globalId.hashCode());
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
        final PEMeta other = (PEMeta) obj;
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
