package com.prayer.mod.meta;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;

/**
 * Json的Schema信息
 * 
 * @author Lang
 * @see
 */
public class GenericSchema implements Serializable {	// NOPMD

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
