package com.test.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.RequiredAttrMissingException;

/**
 * 
 * @author Lang
 *
 */
public class _11TypeRequired1TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_11TypeRequired1TestCase.class);

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

	// ~ Methods =============================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP22Fields1String10001() throws AbstractSchemaException {
		testImport("types/P022field-Type1STRING-length10001.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP22Fields2Xml10001() throws AbstractSchemaException {
		testImport("types/P022field-Type2XML-length10001.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP22Fields3Json10001() throws AbstractSchemaException {
		testImport("types/P022field-Type3JSON-length10001.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
