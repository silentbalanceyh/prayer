package com.prayer.model.meta.database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.constant.SystemEnum.Status;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.entity.Attributes;
import com.prayer.fantasm.model.AbstractEntity;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表SYS_VIEW
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = Attributes.ID)
public class PEView extends AbstractEntity<String> {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 6847069017189431500L;
    // ~ Instance Fields =====================================
    /** K_ID: View表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** S_STATUS: View表的状态 **/
    @JsonProperty(STATUS)
    private Status status;
    /** S_NAME: View的名称 **/
    @JsonProperty(NAME)
    private String name;
    /** S_NAMESPACE **/
    @JsonProperty(NAMESPACE)
    private String namespace;
    /** S_GLOBAL_ID **/
    @JsonProperty(GLOBAL_ID)
    private String globalId;
    /** D_CATEGORY **/
    @JsonProperty(CATEGORY)
    private String category;
    /** D_VIEW **/
    @JsonProperty(VIEW_NAME)
    private String view;
    /** D_STATEMENT **/
    @JsonProperty(STATEMENT)
    private String statement;
    /** D_CHECK_OPTION **/
    @JsonProperty(CHECK_OPTION)
    private Boolean check;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PEView() {
    }

    /** **/
    public PEView(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PEView(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /** **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeEnum(data, STATUS, this::getStatus);
        writeString(data, NAME, this::getName);
        writeString(data, NAMESPACE, this::getNamespace);
        writeString(data, GLOBAL_ID, this::getGlobalId);
        writeString(data, CATEGORY, this::getCategory);
        writeString(data, VIEW_NAME, this::getView);
        writeString(data, STATEMENT, this::getStatement);
        writeBoolean(data, CHECK_OPTION, this::isCheck);
        return data;
    }

    /** **/
    @Override
    public PEView fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readEnum(data, STATUS, this::setStatus, Status.class);
        readString(data, NAME, this::setName);
        readString(data, NAMESPACE, this::setNamespace);
        readString(data, GLOBAL_ID, this::setGlobalId);
        readString(data, CATEGORY, this::setCategory);
        readString(data, VIEW_NAME, this::setView);
        readString(data, STATEMENT, this::setStatement);
        readBoolean(data, CHECK_OPTION, this::setCheck);
        return this;
    }

    /** **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeEnum(buffer, this::getStatus);
        writeString(buffer, this::getName);
        writeString(buffer, this::getNamespace);
        writeString(buffer, this::getGlobalId);
        writeString(buffer, this::getCategory);
        writeString(buffer, this::getView);
        writeString(buffer, this::getStatement);
        writeBoolean(buffer, this::isCheck);
    }

    /** **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readEnum(pos, buffer, this::setStatus, Status.class);
        pos = readString(pos, buffer, this::setName);
        pos = readString(pos, buffer, this::setNamespace);
        pos = readString(pos, buffer, this::setGlobalId);
        pos = readString(pos, buffer, this::setCategory);
        pos = readString(pos, buffer, this::setView);
        pos = readString(pos, buffer, this::setStatement);
        pos = readBoolean(pos, buffer, this::setCheck);
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
     * @return the view
     */
    public String getView() {
        return view;
    }

    /**
     * @param view
     *            the view to set
     */
    public void setView(final String view) {
        this.view = view;
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
     * @return the checkOpt
     */
    public Boolean isCheck() {
        return check;
    }

    /**
     * @param checkOpt
     *            the checkOpt to set
     */
    public void setCheck(final Boolean check) {
        this.check = check;
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
        result = prime * result + ((check == null) ? 0 : check.hashCode());
        result = prime * result + ((globalId == null) ? 0 : globalId.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        result = prime * result + ((view == null) ? 0 : view.hashCode());
        return result;
    }

    /** **/
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
        final PEView other = (PEView) obj;
        if (category == null) {
            if (other.category != null) {
                return false;
            }
        } else if (!category.equals(other.category)) {
            return false;
        }
        if (check == null) {
            if (other.check != null) {
                return false;
            }
        } else if (!check.equals(other.check)) {
            return false;
        }
        if (globalId == null) {
            if (other.globalId != null) {
                return false;
            }
        } else if (!globalId.equals(other.globalId)) {
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
        if (status != other.status) {
            return false;
        }
        if (uniqueId == null) {
            if (other.uniqueId != null) {
                return false;
            }
        } else if (!uniqueId.equals(other.uniqueId)) {
            return false;
        }
        if (view == null) {
            if (other.view != null) {
                return false;
            }
        } else if (!view.equals(other.view)) {
            return false;
        }
        return true;
    }
}
