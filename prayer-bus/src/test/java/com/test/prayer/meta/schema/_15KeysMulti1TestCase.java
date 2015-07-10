package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.KeysNameSpecificationException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _15KeysMulti1TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_15KeysMulti1TestCase.class);

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
	@Test(expected = PatternNotMatchException.class)
	public void testP291Keys1Multi10003() throws AbstractSchemaException {
		testImport("zkeys/P0291keys-10003-1.json",
				"[E10003] Keys ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP292Keys1Multi10003() throws AbstractSchemaException {
		testImport("zkeys/P0292keys-10003-2.json",
				"[E10003] Keys ==> (Failure) There is unexpected exception!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = KeysNameSpecificationException.class)
	public void testP30Keys1Spec10019() throws AbstractSchemaException {
		testImport("zkeys/P030keys-10019-1.json",
				"[E10019] Keys ==> (Failure) Name specification error for this case!");
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = KeysNameSpecificationException.class)
	public void testP30Keys2Spec10019() throws AbstractSchemaException {
		testImport("zkeys/P030keys-10019-2.json",
				"[E10019] Keys ==> (Failure) Name specification error for this case!");
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = KeysNameSpecificationException.class)
	public void testP30Keys3Spec10019() throws AbstractSchemaException {
		testImport("zkeys/P030keys-10019-3.json",
				"[E10019] Keys ==> (Failure) Name specification error for this case!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
