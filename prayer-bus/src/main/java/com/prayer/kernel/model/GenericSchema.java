package com.prayer.kernel.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.model.meta.FieldModel;
import com.prayer.model.meta.KeyModel;
import com.prayer.model.meta.MetaModel;
import com.prayer.util.StringKit;

import jodd.util.StringUtil;
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
public class GenericSchema implements Serializable { // NOPMD

	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -2433873121160152084L;
	// ~ Instance Fields =====================================
	/** **/
	private transient String identifier;
	/** **/
	private transient MetaModel meta;
	/** **/
	private transient ConcurrentMap<String, KeyModel> keys = new ConcurrentHashMap<>();
	/** **/
	private transient ConcurrentMap<String, FieldModel> fields = new ConcurrentHashMap<>();

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 功能函数
	 * 
	 * @param keys
	 * @return
	 */
	public static ConcurrentMap<String, KeyModel> getKeysMap(final List<KeyModel> keys) {
		final ConcurrentMap<String, KeyModel> retMap = new ConcurrentHashMap<>();
		for (final KeyModel key : keys) {
			if (StringKit.isNonNil(key.getName())) {
				retMap.put(key.getName(), key);
			}
		}
		return retMap;
	}

	/**
	 * 功能函数
	 * 
	 * @param fields
	 * @return
	 */
	public static ConcurrentMap<String, FieldModel> getFieldsMap(final List<FieldModel> fields) {
		final ConcurrentMap<String, FieldModel> retMap = new ConcurrentHashMap<>();
		for (final FieldModel field : fields) {
			if (StringKit.isNonNil(field.getName())) {
				retMap.put(field.getName(), field);
			}
		}
		return retMap;
	}
	
	
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
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
	public MetaModel getMeta() {
		return meta;
	}

	/**
	 * @param meta
	 *            the meta to set
	 */
	public void setMeta(final MetaModel meta) {
		this.meta = meta;
	}

	/**
	 * @return the keys
	 */
	public ConcurrentMap<String, KeyModel> getKeys() {
		return keys;
	}

	/**
	 * @param keys
	 *            the keys to set
	 */
	public void setKeys(final ConcurrentMap<String, KeyModel> keys) {
		this.keys = keys;
	}

	/**
	 * @return the fields
	 */
	public ConcurrentMap<String, FieldModel> getFields() {
		return fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(final ConcurrentMap<String, FieldModel> fields) {
		this.fields = fields;
	}
	/**
	 * 排序列出数据列，PK在最前边
	 * @return
	 */
	public Set<String> getColumns(){
		final Set<String> columns = new TreeSet<>();
		for (final FieldModel field : this.getFields().values()) {
			if (StringKit.isNonNil(field.getColumnName())) {
				columns.add(field.getColumnName());
			}
		}
		return columns;
	}

	/**
	 * 按照列名获取FieldModel
	 * 
	 * @param colName
	 * @return
	 */
	public FieldModel getColumn(@NotNull @NotBlank @NotEmpty final String colName){
		FieldModel ret = null;
		for (final FieldModel field : this.getFields().values()) {
			if (StringKit.isNonNil(field.getColumnName()) && StringUtil.equals(colName, field.getColumnName())) {
				ret = field;
				break;
			}
		}
		return ret;
	}

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

	// ~ hashCode,equals,toString ============================

}
