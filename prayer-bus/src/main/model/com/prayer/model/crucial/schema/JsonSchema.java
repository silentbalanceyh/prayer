package com.prayer.model.crucial.schema;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.facade.kernel.Schema;
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
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the meta
     */
    public PEMeta getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
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
     * @param keys the keys to set
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
     * @param fields the fields to set
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
     * @param indexes the indexes to set
     */
    public void setIndexes(final ConcurrentMap<String, PEIndex> indexes) {
        this.indexes = indexes;
    }
    // ~ hashCode,equals,toString ============================
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
    
}
