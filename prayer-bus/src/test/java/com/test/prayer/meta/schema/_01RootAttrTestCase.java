package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.exception.schema.UnsupportAttrException;

/**
 * 
 * @author Lang
 * @see
 */
public class _01RootAttrTestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_01RootAttrTestCase.class);

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
	public void testP001Fields10001() throws AbstractSchemaException {
		testImport("P001root-fields10001.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP001Keys10001() throws AbstractSchemaException {
		testImport("P001root-keys10001.json",
				"[E10001] Keys ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP001Meta10001() throws AbstractSchemaException {
		testImport("P001root-meta10001.json",
				"[E10001] Meta ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = JsonTypeConfusedException.class)
	public void testP002Fields10002() throws AbstractSchemaException {
		testImport("P002root-fields10002.json",
				"[E10002] Fields ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = JsonTypeConfusedException.class)
	public void testP002Keys10002() throws AbstractSchemaException {
		testImport("P002root-keys10002.json",
				"[E10002] Keys ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = JsonTypeConfusedException.class)
	public void testP002Meta10002() throws AbstractSchemaException {
		testImport("P002root-meta10002.json",
				"[E10002] Meta ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = UnsupportAttrException.class)
	public void testP003Meta10017() throws AbstractSchemaException {
		testImport("P003root-fields10017.json",
				"[E10017] Root ==> (Failure) There is unexpected exception!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
