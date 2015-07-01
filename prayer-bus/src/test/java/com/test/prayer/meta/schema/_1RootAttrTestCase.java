package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.AttrJsonTypeException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.exception.schema.UnsupportAttrException;

/**
 * 
 * @author Lang
 * @see
 */
public class _1RootAttrTestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_1RootAttrTestCase.class);

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
	public void testFields10001() throws AbstractSchemaException {
		testImport("root-fields10001.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testKeys10001() throws AbstractSchemaException {
		testImport("root-keys10001.json",
				"[E10001] Keys ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testMeta10001() throws AbstractSchemaException {
		testImport("root-meta10001.json",
				"[E10001] Meta ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = AttrJsonTypeException.class)
	public void testFields10002() throws AbstractSchemaException {
		testImport("root-fields10002.json",
				"[E10002] Fields ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = AttrJsonTypeException.class)
	public void testKeys10002() throws AbstractSchemaException {
		testImport("root-keys10002.json",
				"[E10002] Keys ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = AttrJsonTypeException.class)
	public void testMeta10002() throws AbstractSchemaException {
		testImport("root-meta10002.json",
				"[E10002] Meta ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = UnsupportAttrException.class)
	public void testMeta10017() throws AbstractSchemaException {
		testImport("root-fields10017.json",
				"[E10017] Root ==> (Failure) There is unexpected exception!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
