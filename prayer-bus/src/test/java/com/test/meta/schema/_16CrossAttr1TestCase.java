package com.test.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.WrongTimeAttrException;

/**
 * 
 * @author Lang
 *
 */
public class _16CrossAttr1TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_16CrossAttr1TestCase.class);
	/** **/
	private static final String ERR_MSG_10024 = "[E10024] Cross ==> (Failure) There is unexpected exception!";

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
	@Test(expected = WrongTimeAttrException.class)
	public void testP35Cross1Attr10024() throws AbstractSchemaException {
		testImport("zkeys/P035cross-10024-1.json", ERR_MSG_10024);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = WrongTimeAttrException.class)
	public void testP35Cross2Attr10024() throws AbstractSchemaException {
		testImport("zkeys/P035cross-10024-2.json", ERR_MSG_10024);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = WrongTimeAttrException.class)
	public void testP35Cross3Attr10024() throws AbstractSchemaException {
		testImport("zkeys/P035cross-10024-3.json", ERR_MSG_10024);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = WrongTimeAttrException.class)
	public void testP35Cross4Attr10024() throws AbstractSchemaException {
		testImport("zkeys/P035cross-10024-4.json", ERR_MSG_10024);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
