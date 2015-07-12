package com.test.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.exception.schema.ZeroLengthException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.exception.schema.RequiredAttrMissingException;

/**
 * 
 * @author Lang
 * @see
 */
public class _07FieldsAttr1TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_03MetaPattern1TestCase.class);

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
	@Test(expected = ZeroLengthException.class)
	public void testP012Fields1Attr10006() throws AbstractSchemaException {
		testImport("fields/P012fields-attr10006-1.json",
				"[E10006] Fields -> length ==> Attribute length is 0 (Zero) but it shouldn't be !");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = JsonTypeConfusedException.class)
	public void testP013Fields1Attr10002() throws AbstractSchemaException {
		testImport("fields/P013fields-attr10002-1.json",
				"[E10002] Fields ==> Every element of __fields__ node must be Json Object !");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = JsonTypeConfusedException.class)
	public void testP013Fields2Attr10002() throws AbstractSchemaException {
		testImport("fields/P013fields-attr10002-2.json",
				"[E10002] Fields ==> Every element of __fields__ node must be Json Object !");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP0141Fields1Required10001() throws AbstractSchemaException {
		testImport("fields/P0141field-required10001-1.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception, 'columnType' missing! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP0141Fields2Required10001() throws AbstractSchemaException {
		testImport("fields/P0141field-required10001-2.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception, 'columnName' missing! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP0141Fields3Required10001() throws AbstractSchemaException {
		testImport("fields/P0141field-required10001-3.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception, 'name' missing! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testP0141Fields4Required10001() throws AbstractSchemaException {
		testImport("fields/P0141field-required10001-4.json",
				"[E10001] Fields ==> (Failure) There is unexpected exception, 'type' missing! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP0142Fields1name10003() throws AbstractSchemaException {
		testImport("fields/P0142field-name10003-1.json",
				"[E10003] Fields -> name ==> (Failure) Attribute 'name' does not match pattern! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP0142Fields2name10003() throws AbstractSchemaException {
		testImport("fields/P0142field-name10003-2.json",
				"[E10003] Fields -> name ==> (Failure) Attribute 'name' does not match pattern! ");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP0142Fields3name10003() throws AbstractSchemaException {
		testImport("fields/P0142field-name10003-3.json",
				"[E10003] Fields -> name ==> (Failure) Attribute 'name' does not match pattern! ");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
