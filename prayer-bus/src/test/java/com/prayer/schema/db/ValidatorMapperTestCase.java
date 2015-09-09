package com.prayer.schema.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.h2.vx.ValidatorModel;

/**
 * 
 * @author Lang
 *
 */
public class ValidatorMapperTestCase extends AbstractMapperCase<ValidatorModel, String> { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorMapperTestCase.class);

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
		return ValidatorMapper.class;
	}

	/** **/
	@Override
	public ValidatorModel instance() {
		return new ValidatorModel();
	}

	/** **/
	@Override
	public String[] filterFields() {
		return new String[] { "refUriId" };
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
