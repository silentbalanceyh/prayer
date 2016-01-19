package com.prayer.model.crucial.schema;

import static com.prayer.util.Generator.uuid;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.facade.entity.Entity;
import com.prayer.facade.schema.Schema;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEIndex;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public final class JsonSchema implements Schema {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 8706928808261541941L;
    // ~ Instance Fields =====================================
    /** **/
    private transient String identifier;
    /** **/
    private transient PEMeta meta;
    /** **/
    private transient ConcurrentMap<String, PEKey> keys = new ConcurrentHashMap<>();
    /** **/
    private transient ConcurrentMap<String, PEField> fields = new ConcurrentHashMap<>();
    /** **/
    @SuppressWarnings("unused")
    // TODO: Index信息处理
    private transient ConcurrentMap<String, PEIndex> indexes = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Builder Methods =====================================
    /** **/
    @Override
    public Set<String> getColumns() {
        return this.fields.values().stream().map(PEField::getColumnName).collect(Collectors.toSet());
    }

    /** **/
    @Override
    public PEField getColumn(@NotNull @NotBlank @NotEmpty String column) {
        return this.fields.values().stream().filter(field -> StringKit.equals(field.getColumnName(), column))
                .collect(Collectors.toList()).get(Constants.IDX);
    }

    /** **/
    @Override
    public List<PEField> getPrimaryKeys() {
        return this.fields.values().stream().filter(field -> field.isPrimaryKey()).collect(Collectors.toList());
    }

    /** **/
    @Override
    public List<PEField> getForeignField() {
        return this.fields.values().stream().filter(field -> field.isForeignKey()).collect(Collectors.toList());
    }

    /** **/
    @Override
    public List<PEKey> getForeignKey() {
        return this.keys.values().stream().filter(key -> KeyCategory.ForeignKey == key.getCategory())
                .collect(Collectors.toList());
    }

    // ~ Metadata Database ===================================
    /** Meta的Id **/
    @Override
    public Serializable totem() {
        return this.meta.id();
    }

    /** Meta的Id **/
    @Override
    public Serializable totem(@NotNull final Serializable metaId) {
        this.meta.id(metaId);
        return this.totem();
    }

    /** 同步所有关联信息 **/
    @Override
    public void synchronize(@NotNull @NotBlank @NotEmpty final Serializable metaId) {
        /** 1.Keys的信息设置 **/
        if (null != this.keys && !this.keys.isEmpty()) {
            for (final PEKey key : this.keys.values()) {
                key.id(uuid());
                key.setRefMetaId(metaId.toString());
            }
        }
        /** 2.Fields的信息设置 **/
        if (null != this.fields && !this.fields.isEmpty()) {
            for (final PEField field : this.fields.values()) {
                field.id(uuid());
                field.setRefMetaId(metaId.toString());
            }
        }
    }

    /** 从Schema中读取PEMeta **/
    @Override
    public Entity meta() {
        return this.meta;
    }

    /** 设置PEMeta **/
    @Override
    public void meta(@NotNull final PEMeta meta) {
        this.meta = meta;
        /** 必须设置identifier **/
        this.identifier = meta.getGlobalId();
    }

    /** 从Schema中读取PEKey集合 **/
    @Override
    public Entity[] keys() {
        return this.keys.values().toArray(new PEKey[] {});
    }

    /** 设置PEKey的集合 **/
    @Override
    public void keys(@NotNull final PEKey... keys) {
        if (null == this.keys) {
            this.keys = new ConcurrentHashMap<>();
        }
        this.keys.clear();
        for (final PEKey key : keys) {
            this.keys.put(key.getName(), key);
        }
    }

    /** 从Schema中读取PEField集合 **/
    @Override
    public Entity[] fields() {
        return this.fields.values().toArray(new PEField[] {});
    }

    /** 设置PEFields的集合 **/
    @Override
    public void fields(@NotNull final PEField... fields) {
        if (null == this.fields) {
            this.fields = new ConcurrentHashMap<>();
        }
        this.fields.clear();
        for (final PEField field : fields) {
            this.fields.put(field.getName(), field);
        }
    }

    // ~ Schema Information ==================================
    /** Global Id **/
    @Override
    public String identifier() {
        return this.identifier;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        final StringBuilder data = new StringBuilder();
        data.append("identifier = ").append(this.identifier).append(",");
        data.append("meta = ").append(this.meta.toJson().encode()).append(",");
        data.append("keys = [");
        if (null != this.keys) {
            for (final String name : this.keys.keySet()) {
                final PEKey key = this.keys.get(name);
                if (null == key) {
                    data.append(name + " = null,");
                } else {
                    data.append(name + " = " + key.toJson().encode() + ",");
                }
            }
            data.delete(data.length() - 1, data.length());
        }
        data.append("],");
        data.append("fields = [");
        if (null != this.fields) {
            for (final String name : this.fields.keySet()) {
                final PEField field = this.fields.get(name);
                if (null == field) {
                    data.append(name + " = null,");
                } else {
                    data.append(name + " = " + field.toJson().encode() + ",");
                }
            }
            data.delete(data.length() - 1, data.length());
        }
        data.append("]");
        return data.toString();
    }

    /** **/
    @Override
    public int hashCode() {
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + ((meta == null) ? 0 : meta.hashCode());
        /** Keys **/
        if (null == this.keys) {
            result = prime * result + 0;
        } else {
            for (final String name : this.keys.keySet()) {
                final PEKey key = this.keys.get(name);
                if (null == key) {
                    result = prime * result + 0;
                } else {
                    result = prime * result + key.hashCode();
                }
            }
        }
        /** Fields **/
        if (null == this.fields) {
            result = prime * result + 0;
        } else {
            for (final String name : this.fields.keySet()) {
                final PEField field = this.fields.get(name);
                if (null == field) {
                    result = prime * result + 0;
                } else {
                    result = prime * result + field.hashCode();
                }
            }
        }
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
        final JsonSchema other = (JsonSchema) obj;
        if (this.identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!this.identifier.equals(other.identifier)) {
            return false;
        }
        if (this.meta == null) {
            if (other.meta != null) {
                return false;
            }
        } else if (!this.meta.equals(other.meta)) {
            return false;
        }
        /** Keys **/
        if (this.keys == null) {
            if (other.keys != null) {
                return false;
            }
        } else {
            /** 针对Hash的映射检查Keys **/
            for (final String name : this.keys.keySet()) {
                final PEKey thisKey = this.keys.get(name);
                final PEKey thatKey = other.keys.get(name);
                if (thisKey == null) {
                    if (thatKey != null) {
                        return false;
                    }
                } else if (!thisKey.equals(thatKey)) {
                    return false;
                }
            }
        }
        /** Fields **/
        if (this.fields == null) {
            if (other.fields != null) {
                return false;
            }
        } else {
            /** 针对Hash的映射检查Fields **/
            for (final String name : this.fields.keySet()) {
                final PEField thisField = this.fields.get(name);
                final PEField thatField = other.fields.get(name);
                if (thisField == null) {
                    if (thatField != null) {
                        return false;
                    }
                } else if (!thisField.equals(thatField)) {
                    return false;
                }
            }
        }

        return true;
    }

}
