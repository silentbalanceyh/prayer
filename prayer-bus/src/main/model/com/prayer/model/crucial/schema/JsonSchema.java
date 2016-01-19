package com.prayer.model.crucial.schema;

import static com.prayer.util.Generator.uuid;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
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
    private transient ConcurrentMap<String, PEKey> keys;
    /** **/
    private transient ConcurrentMap<String, PEField> fields;
    /** **/
    private transient ConcurrentMap<String, PEIndex> indexes;

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

    /** 从Schema中读取PEKey集合 **/
    @Override
    public Entity[] keys() {
        return this.keys.values().toArray(new PEKey[] {});
    }

    /** 从Schema中读取PEField集合 **/
    @Override
    public Entity[] fields() {
        return this.fields.values().toArray(new PEField[] {});
    }

    // ~ Schema Information ==================================
    /** Global Id **/
    @Override
    public String identifier() {
        return this.identifier;
    }

    /** Global Id Setting **/
    @Override
    public void identifier(@NotNull @NotBlank @NotEmpty final String identifier) {
        this.identifier = identifier;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================

    /**
     * @return the meta
     */
    public PEMeta getMeta() {
        return meta;
    }

    /**
     * @param meta
     *            the meta to set
     */
    public void setMeta(final PEMeta meta) {
        this.meta = meta;
    }

    /**
     * @return the keys
     */
    public ConcurrentMap<String, PEKey> getKeys() {
        return keys;
    }

    /**
     * @param keys
     *            the keys to set
     */
    public void setKeys(final ConcurrentMap<String, PEKey> keys) {
        this.keys = keys;
    }

    /**
     * @return the fields
     */
    public ConcurrentMap<String, PEField> getFields() {
        return fields;
    }

    /**
     * @param fields
     *            the fields to set
     */
    public void setFields(final ConcurrentMap<String, PEField> fields) {
        this.fields = fields;
    }

    /**
     * @return the indexes
     */
    public ConcurrentMap<String, PEIndex> getIndexes() {
        return indexes;
    }

    /**
     * @param indexes
     *            the indexes to set
     */
    public void setIndexes(final ConcurrentMap<String, PEIndex> indexes) {
        this.indexes = indexes;
    }
    // ~ hashCode,equals,toString ============================
}
