package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.exception.schema.UnsupportAttrException;

/**
 * 
 * @author Lang
 * @see
 */
public class _2MetaAttrTestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_2MetaAttrTestCase.class);

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
	public void testMetaName10001() throws AbstractSchemaException {
		testImport("meta-name10001.json",
				"[E10001] Meta -> name ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testMetaNamespace10001() throws AbstractSchemaException {
		testImport("meta-namespace10001.json",
				"[E10001] Meta -> namespace ==> (Failure) There is unexpected exception!");
	}
	
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testMetaCategory10001() throws AbstractSchemaException {
		testImport("meta-category10001.json",
				"[E10001] Meta -> category ==> (Failure) There is unexpected exception!");
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testMetaTable10001() throws AbstractSchemaException {
		testImport("meta-table10001.json",
				"[E10001] Meta -> table ==> (Failure) There is unexpected exception!");
	}
	
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testMetaIdentifier10001() throws AbstractSchemaException {
		testImport("meta-identifier10001.json",
				"[E10001] Meta -> identifier ==> (Failure) There is unexpected exception!");
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testMetaMapping10001() throws AbstractSchemaException {
		testImport("meta-mapping10001.json",
				"[E10001] Meta -> mapping ==> (Failure) There is unexpected exception!");
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testMetaPolicy10001() throws AbstractSchemaException {
		testImport("meta-policy10001.json",
				"[E10001] Meta -> policy ==> (Failure) There is unexpected exception!");
	}
	
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = UnsupportAttrException.class)
	public void testMetaAttr10002() throws AbstractSchemaException {
		testImport("meta-attr10002.json",
				"[E10001] Meta (Unsupported) ==> (Failure) There is unexpected exception!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
