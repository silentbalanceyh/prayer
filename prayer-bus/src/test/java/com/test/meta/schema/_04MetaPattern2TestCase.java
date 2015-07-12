package com.test.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 * @see
 */
public class _04MetaPattern2TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_04MetaPattern2TestCase.class);
	/** **/
	private static final String ERR_TABLE_MSG = "[E10003] Meta -> table ==> (Failure) Pattern does not match!";
	/** **/
	private static final String ERR_ID_MSG = "[E10003] Meta -> identifier ==> (Failure) Pattern does not match!";

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
	 * table长度小于2
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP006Meta10003Table1() throws AbstractSchemaException {
		testImport("P006meta-table10003-1.json", ERR_TABLE_MSG);
	}

	/**
	 * table长度大于4
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP006Meta10003Table2() throws AbstractSchemaException {
		testImport("P006meta-table10003-2.json", ERR_TABLE_MSG);
	}

	/**
	 * table以_开头
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP006Meta10003Table3() throws AbstractSchemaException {
		testImport("P006meta-table10003-3.json", ERR_TABLE_MSG);
	}

	/**
	 * identifier以.号开始
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP006Meta10003Id1() throws AbstractSchemaException {
		testImport("P006meta-id10003-1.json", ERR_ID_MSG);
	}

	/**
	 * identifier中包含大写
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP006Meta10003Id2() throws AbstractSchemaException {
		testImport("P006meta-id10003-2.json", ERR_ID_MSG);
	}

	/**
	 * identifier中包含数字
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP006Meta10003Id3() throws AbstractSchemaException {
		testImport("P006meta-id10003-3.json", ERR_ID_MSG);
	}

	/**
	 * mapping的值不对
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP006Meta10003Mapping1() throws AbstractSchemaException {
		testImport("P006meta-mapping10003-1.json", "[E10003] Meta -> mapping ==> (Failure) Pattern does not match!");
	}

	/**
	 * policy的值不对
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP006Meta10003Policy1() throws AbstractSchemaException {
		testImport("P006meta-policy10003-1.json", "[E10003] Meta -> policy ==> (Failure) Pattern does not match!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
