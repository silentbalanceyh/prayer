package com.prayer.model.database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.base.model.AbstractEntity;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.TargetType;
import com.prayer.constant.SystemEnum.TriggerMode;
import com.prayer.constant.SystemEnum.TriggerOp;
import com.prayer.facade.entity.Attributes;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表SYS_TRIGGER
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PETrigger extends AbstractEntity {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1574603277420498614L;
    // ~ Instance Fields =====================================
    /** K_ID：Trigger表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** D_NAME: Trigger触发器的名称 **/
    @JsonProperty(NAME)
    private String name;
    /** D_CATEGORY: Trigger的数据库类型 **/
    @JsonProperty(CATEGORY)
    private String category;
    /** D_TARGET **/
    @JsonProperty(TARGET)
    private TargetType target;
    /** D_OP_TYPE **/
    @JsonProperty(OP_TYPE)
    private TriggerOp opType;
    /** D_MODE **/
    @JsonProperty(MODE)
    private TriggerMode mode;
    /** D_STATEMENT **/
    @JsonProperty(STATEMENT)
    private String statement;
    /** R_REF_ID **/
    @JsonProperty(REF_ID)
    private String refId;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PETrigger() {
    }

    /** **/
    public PETrigger(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PETrigger(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /** **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeString(data, NAME, this::getName);
        writeString(data, CATEGORY, this::getCategory);
        writeEnum(data, TARGET, this::getTarget);
        writeEnum(data, OP_TYPE, this::getOpType);
        writeEnum(data, MODE, this::getMode);
        writeString(data, STATEMENT, this::getStatement);
        writeString(data, REF_ID, this::getRefId);
        return data;
    }

    /** **/
    @Override
    public PETrigger fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, NAME, this::setName);
        readString(data, CATEGORY, this::setCategory);
        readEnum(data, TARGET, this::setTarget, TargetType.class);
        readEnum(data, OP_TYPE, this::setOpType, TriggerOp.class);
        readEnum(data, MODE, this::setMode, TriggerMode.class);
        readString(data, STATEMENT, this::setStatement);
        readString(data, REF_ID, this::setRefId);
        return this;
    }

    /** **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getName);
        writeString(buffer, this::getCategory);
        writeEnum(buffer, this::getTarget);
        writeEnum(buffer, this::getOpType);
        writeEnum(buffer, this::getMode);
        writeString(buffer, this::getStatement);
        writeString(buffer, this::getRefId);
    }

    /** **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setName);
        pos = readString(pos, buffer, this::setCategory);
        pos = readEnum(pos, buffer, this::setTarget, TargetType.class);
        pos = readEnum(pos, buffer, this::setOpType, TriggerOp.class);
        pos = readEnum(pos, buffer, this::setMode, TriggerMode.class);
        pos = readString(pos, buffer, this::setStatement);
        pos = readString(pos, buffer, this::setRefId);
        return pos;
    }

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
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(final String category) {
        this.category = category;
    }

    /**
     * @return the target
     */
    public TargetType getTarget() {
        return target;
    }

    /**
     * @param target
     *            the target to set
     */
    public void setTarget(final TargetType target) {
        this.target = target;
    }

    /**
     * @return the opType
     */
    public TriggerOp getOpType() {
        return opType;
    }

    /**
     * @param opType
     *            the opType to set
     */
    public void setOpType(final TriggerOp opType) {
        this.opType = opType;
    }

    /**
     * @return the mode
     */
    public TriggerMode getMode() {
        return mode;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(final TriggerMode mode) {
        this.mode = mode;
    }

    /**
     * @return the statement
     */
    public String getStatement() {
        return statement;
    }

    /**
     * @param statement
     *            the statement to set
     */
    public void setStatement(final String statement) {
        this.statement = statement;
    }

    /**
     * @return the refId
     */
    public String getRefId() {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(final String refId) {
        this.refId = refId;
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
        result = prime * result + ((mode == null) ? 0 : mode.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((opType == null) ? 0 : opType.hashCode());
        result = prime * result + ((refId == null) ? 0 : refId.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        return result;
    }

    /**
     * 
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PETrigger other = (PETrigger) obj;
        if (category == null) {
            if (other.category != null) {
                return false;
            }
        } else if (!category.equals(other.category)) {
            return false;
        }
        if (mode != other.mode) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (opType != other.opType) {
            return false;
        }
        if (refId == null) {
            if (other.refId != null) {
                return false;
            }
        } else if (!refId.equals(other.refId)) {
            return false;
        }
        if (target != other.target) {
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
