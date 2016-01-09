package com.prayer.model.vertx;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.base.model.AbstractEntity;
import com.prayer.constant.Constants;
import com.prayer.plugin.jackson.ClassDeserializer;
import com.prayer.plugin.jackson.ClassSerializer;
import com.prayer.plugin.jackson.JsonObjectDeserializer;
import com.prayer.plugin.jackson.JsonObjectSerializer;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 对应表EVX_VERTICLE
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class PEVerticle extends AbstractEntity { // NOPMD
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1056134069321946140L;
    // ~ Instance Fields =====================================
    /** K_ID: EVX_VERTICLE表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    // ~ !基础配置数据 =======================================
    /** S_CLASS **/
    @JsonProperty(NAME)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> name;
    /** S_INSTANCES **/
    @JsonProperty(INSTANCES)
    private int instances = 1;
    /** S_IGROUP **/
    @JsonProperty(GROUP)
    private String group = Constants.VX_GROUP; // NOPMD，__DEFAULT__是HA中Group的默认值
    /** S_JSON_CONFIG **/
    @JsonProperty(JSON_CONFIG)
    @JsonSerialize(using = JsonObjectSerializer.class)
    @JsonDeserialize(using = JsonObjectDeserializer.class)
    private JsonObject jsonConfig = new JsonObject(); // NOPMD
    /** S_ISOLATED_CLASSES **/
    @JsonProperty(ISOLATED_CLASSES)
    private List<String> isolatedClasses = new ArrayList<>(); // NOPMD
    // ~ !类路径配置数据 =====================================
    /** CP_EXT **/
    @JsonProperty(EXTRA_CP)
    private List<String> extraCp = new ArrayList<>(); // NOPMD

    // ~ !Boolean类型配置数据 ================================
    /** IS_HA **/
    @JsonProperty(HA)
    private boolean ha = false; // NOPMD
    /** IS_WORKER **/
    @JsonProperty(WORKER)
    private boolean worker = false; // NOPMD
    /** IS_MULTI **/
    @JsonProperty(MULTI)
    private boolean multi = false; // NOPMD

    // ~ !和发布相关的配置====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PEVerticle() {
    }

    /** **/
    public PEVerticle(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PEVerticle(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Vert.X Serialization ================================
    /** 写入Buffer **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer,this::getUniqueId);
        writeClass(buffer,this::getName);
        writeInt(buffer,this::getInstances);
        writeString(buffer,this::getGroup);
        writeJObject(buffer,this::getJsonConfig);
        writeList(buffer,this::getIsolatedClasses);
        writeList(buffer,this::getExtraCp);
        writeBoolean(buffer,this::isHa);
        writeBoolean(buffer,this::isWorker);
        writeBoolean(buffer,this::isMulti);
    }

    /** 从Buffer中读取 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos,buffer,this::setUniqueId);
        pos = readClass(pos,buffer,this::setName);
        pos = readInt(pos,buffer,this::setInstances);
        pos = readString(pos,buffer,this::setGroup);
        pos = readJObject(pos,buffer,this::setJsonConfig);
        pos = readList(pos,buffer,this::setIsolatedClasses);
        pos = readList(pos,buffer,this::setExtraCp);
        pos = readBoolean(pos,buffer,this::setHa);
        pos = readBoolean(pos,buffer,this::setWorker);
        pos = readBoolean(pos,buffer,this::setMulti);
        return pos;
    }

    // ~ Entity Json/Buffer Serialization ====================
    /** 从Json中读取数据 **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeClass(data, NAME, this::getName);
        writeInt(data, INSTANCES, this::getInstances);
        writeString(data, GROUP, this::getGroup);
        writeJObject(data, JSON_CONFIG, this::getJsonConfig);
        writeList(data, ISOLATED_CLASSES, this::getIsolatedClasses);
        writeList(data, EXTRA_CP, this::getExtraCp);
        writeBoolean(data, HA, this::isHa);
        writeBoolean(data, WORKER, this::isWorker);
        writeBoolean(data, MULTI, this::isMulti);
        return data;
    }

    /** 从Json反序列化 **/
    @Override
    public PEVerticle fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readClass(data, NAME, this::setName);
        readInt(data, INSTANCES, this::setInstances);
        readString(data, GROUP, this::setGroup);
        readJObject(data, JSON_CONFIG, this::setJsonConfig);
        readList(data, ISOLATED_CLASSES, this::setIsolatedClasses);
        readList(data, EXTRA_CP, this::setExtraCp);
        readBoolean(data, HA, this::setHa);
        readBoolean(data, WORKER, this::setWorker);
        readBoolean(data, MULTI, this::setMulti);
        return this;
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
    public Class<?> getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final Class<?> name) {
        this.name = name;
    }

    /**
     * @return the instances
     */
    public int getInstances() {
        return instances;
    }

    /**
     * @param instances
     *            the instances to set
     */
    public void setInstances(final int instances) {
        this.instances = instances;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group
     *            the group to set
     */
    public void setGroup(final String group) {
        this.group = group;
    }

    /**
     * @return the jsonConfig
     */
    public JsonObject getJsonConfig() {
        return jsonConfig;
    }

    /**
     * @param jsonConfig
     *            the jsonConfig to set
     */
    public void setJsonConfig(final JsonObject jsonConfig) {
        this.jsonConfig = jsonConfig;
    }

    /**
     * @return the isolatedClasses
     */
    public List<String> getIsolatedClasses() {
        return isolatedClasses;
    }

    /**
     * @param isolatedClasses
     *            the isolatedClasses to set
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
     * @param extraCp
     *            the extraCp to set
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
     * @param ha
     *            the ha to set
     */
    public void setHa(final boolean ha) { // NOPMD
        this.ha = ha;
    }

    /**
     * @return the worker
     */
    public boolean isWorker() {
        return worker;
    }

    /**
     * @param worker
     *            the worker to set
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
     * @param multi
     *            the multi to set
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
        return this.toJson().encode();
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
        final PEVerticle other = (PEVerticle) obj;
        if (group == null) {
            if (other.group != null) {
                return false;
            }
        } else if (!group.equals(other.group)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
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
