package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.AttrZeroException;

/**
 * 
 * @author Lang
 * @see
 */
public class _7FieldsAttr1TestCase extends AbstractSchemaTestCase{	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_3MetaPattern1TestCase.class);
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
	@Test(expected = AttrZeroException.class)
	public void testP012Fields1Attr10006() throws AbstractSchemaException{
		testImport(
				"P012fields-attr10006-1.json",
				"[E10006] Fields -> length ==> Attribute length is 0 (Zero) but it shouldn't be !");
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = AttrZeroException.class)
	public void testP013Fields1Attr10002() throws AbstractSchemaException{
		testImport(
				"P013fields-attr10002-1.json",
				"[E10002] Fields ==> Every element of __fields__ node must be Json Object !");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
