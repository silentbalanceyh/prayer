package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.PrimaryKeyMissingException;
import com.prayer.exception.schema.PrimaryKeyPolicyException;

/**
 * 
 * @author Lang
 * @see
 */
public class _9FieldsKey1TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_9FieldsKey1TestCase.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PrimaryKeyMissingException.class)
	public void testP017Fields1PrimaryKey10010() throws AbstractSchemaException {
		testImport(
				"keys/P017field-primarykey10010-1.json",
				"[E10010] Fields -> primarykey ==> (Failure) Attribute 'primarykey' missing in the definition! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PrimaryKeyMissingException.class)
	public void testP017Fields2PrimaryKey10010() throws AbstractSchemaException {
		testImport(
				"keys/P017field-primarykey10010-2.json",
				"[E10010] Fields -> primarykey ==> (Failure) All attributes 'primarykey' are conflict with specification to cause PK missing! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PrimaryKeyPolicyException.class)
	public void testP018Fields1PKeyPolicy10011() throws AbstractSchemaException {
		testImport(
				"keys/P018field-pKEYpolicy10011-1.json",
				"[E10011] Fields -> primarykey ==> (Failure) Attribute 'primarykey' does not match the policy definition! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PrimaryKeyPolicyException.class)
	public void testP018Fields2PKeyPolicy10011() throws AbstractSchemaException {
		testImport(
				"keys/P018field-pKEYpolicy10011-2.json",
				"[E10011] Fields -> primarykey ==> (Failure) Attribute 'primarykey' does not match the policy definition! ");
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
