package com.prayer.meta.schema.json;

import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.meta.schema.Ensurer;
import com.prayer.mod.sys.GenericSchema;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public class GenericEnsurer implements Ensurer {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	private transient ConcurrentMap<String, JsonNode> jsonMap;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 无参构造函数
	 */
	public GenericEnsurer() {
		this(null);
	}

	/**
	 * 单参构造函数
	 * 
	 * @param jsonMap
	 */
	public GenericEnsurer(final ConcurrentMap<String, JsonNode> jsonMap) {
		this.jsonMap = jsonMap;
		this.error = null; // NOPMD
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public GenericSchema getResult() {
		return null;
	}

	/**
	 * 
	 */
	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 */
	@Override
	public AbstractSchemaException getError() {
		return this.error;
	}

	/**
	 * 
	 */
	@Override
	@PostValidateThis
	public void refreshData(
			@NotNull final ConcurrentMap<String, JsonNode> jsonMap) {
		this.jsonMap = jsonMap;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
