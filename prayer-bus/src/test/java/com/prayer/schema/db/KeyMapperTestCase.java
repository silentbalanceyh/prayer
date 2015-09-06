package com.prayer.schema.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.h2.KeyModel;

/**
 * 
 * @author Lang
 *
 */
public class KeyMapperTestCase extends AbstractMapperCase<KeyModel, String> { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(KeyMapperTestCase.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	/** **/
	@Override
	public Class<?> getMapperClass() {
		return KeyMapper.class;
	}

	/** **/
	@Override
	public KeyModel instance() {
		return new KeyModel();
	}

	/** **/
	@Override
	public String[] filterFields() {
		return new String[] { "refMetaId" };
	}
	// ~ Methods =============================================
	
	
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
