package com.prayer.model.kernel;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.facade.kernel.Schema;
import com.prayer.model.database.PEField;
import com.prayer.model.database.PEKey;
import com.prayer.model.database.PEMeta;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Json的Schema信息
 * 
 * @author Lang
 * @see
 */
@Guarded
public class GenericSchema implements Schema, Serializable { // NOPMD

    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -2433873121160152084L;
    // ~ Instance Fields =====================================
    /** **/
    private transient String identifier;
    /** **/
    private transient PEMeta meta;
    /** **/
    private transient ConcurrentMap<String, PEKey> keys = new ConcurrentHashMap<>();
    /** **/
    private transient ConcurrentMap<String, PEField> fields = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 排序列出数据列，PK在最前边
     * 
     * @return
     */
    public Set<String> getColumns() {
        return SchemaExpander.getColumns(this.getFields());
    }

    /**
     * 按照列名获取FieldModel
     * 
     * @param colName
     * @return
     */
    public PEField getColumn(@NotNull @NotBlank @NotEmpty final String colName) {
        return SchemaExpander.getColumn(this.getFields(), colName);
    }

    /**
     * 获取主键的Schema，有可能是多个主键
     * 
     * @return
     */
    public List<PEField> getPrimaryKeys() {
        return SchemaExpander.getPrimaryKeys(this.getFields());
    }

    /**
     * 获取外键规范
     * 
     * @return
     */
    public List<PEField> getForeignField() {
        return SchemaExpander.getForeignField(this.getFields());
    }

    /**
     * 获取外键定义
     * 
     * @return
     */
    public List<PEKey> getForeignKey() {
        return SchemaExpander.getForeignKey(this.getKeys());
    }

    // ~ Private Methods =====================================
    // ~ Get / Set ===========================================
    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier
     *            the identifier to set
     */
    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the meta
     */
    @InstanceOfAny(PEMeta.class)
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

    // ~ hashCode,equals,toString ============================
    /**
     * 
     */
    @Override
    public int hashCode() {
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        return result;
    }

    /**
     * 
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) // NOPMD
            return true;
        if (obj == null) // NOPMD
            return false;
        if (getClass() != obj.getClass()) // NOPMD
            return false;
        final GenericSchema other = (GenericSchema) obj;
        if (identifier == null) {
            if (other.identifier != null) // NOPMD
                return false;
        } else if (!identifier.equals(other.identifier)) // NOPMD
            return false;
        return true;
    }
}
