package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.PKColumnTypePolicyException;
import com.prayer.exception.schema.PKMissingException;
import com.prayer.exception.schema.PKNullableConflictException;
import com.prayer.exception.schema.PKPolicyConflictException;
import com.prayer.exception.schema.PKUniqueConflictException;

/**
 * 
 * @author Lang
 * @see
 */
public class _09FieldsKey1TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_09FieldsKey1TestCase.class);
	/** **/
	private static final String E10012_STR = "[E10012] Fields ==> primary key type conflicts! ";

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
	@Test(expected = PKMissingException.class)
	public void testP017Fields1PrimaryKey10010() throws AbstractSchemaException {
		testImport("keys/P017field-primarykey10010-1.json",
				"[E10010] Fields -> primarykey ==> (Failure) Attribute 'primarykey' missing in the definition! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKMissingException.class)
	public void testP017Fields2PrimaryKey10010() throws AbstractSchemaException {
		testImport("keys/P017field-primarykey10010-2.json",
				"[E10010] Fields -> primarykey ==> (Failure) All attributes 'primarykey' are conflict with specification to cause PK missing! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKPolicyConflictException.class)
	public void testP0181Fields1PKeyPolicy10011() throws AbstractSchemaException {
		testImport("keys/P0181field-pKEYpolicy10011-1.json",
				"[E10011] Fields -> primarykey ==> (Failure) Attribute 'primarykey' does not match the policy definition! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKPolicyConflictException.class)
	public void testP0181Fields2PKeyPolicy10011() throws AbstractSchemaException {
		testImport("keys/P0181field-pKEYpolicy10011-2.json",
				"[E10011] Fields -> primarykey ==> (Failure) Attribute 'primarykey' does not match the policy definition! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKColumnTypePolicyException.class)
	public void testP0182FieldsT1PKeyPolicy10012() throws AbstractSchemaException {
		testImport("keys/P0182field-TpKEYpolicy10012-1.json", E10012_STR);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKColumnTypePolicyException.class)
	public void testP0182FieldsT2PKeyPolicy10012() throws AbstractSchemaException {
		testImport("keys/P0182field-TpKEYpolicy10012-2.json", E10012_STR);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKColumnTypePolicyException.class)
	public void testP0182FieldsT3PKeyPolicy10012() throws AbstractSchemaException {
		testImport("keys/P0182field-TpKEYpolicy10012-3.json", E10012_STR);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKColumnTypePolicyException.class)
	public void testP0182FieldsT4PKeyPolicy10012() throws AbstractSchemaException {
		testImport("keys/P0182field-TpKEYpolicy10012-4.json", E10012_STR);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKUniqueConflictException.class)
	public void testP0183KeyUnique10025() throws AbstractSchemaException {
		testImport("keys/P0183field-unique10025-1.json", "[E10025] Keys ==> Primary key unique must be true.");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PKNullableConflictException.class)
	public void testP0184KeyNullable10025() throws AbstractSchemaException {
		testImport("keys/P0184field-nullable10026-1.json", "[E10026] Keys ==> Primary key unique must be true.");
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
