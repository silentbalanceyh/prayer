package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.exception.schema.DuplicatedTablesException;

/**
 * 
 * @author Lang
 * @see
 */
public class _06MetaER2TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_06MetaER2TestCase.class);

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
	@Test(expected = OptionalAttrMorEException.class)
	public void testP00731Meta10004EDirectExisting1()
			throws AbstractSchemaException {
		testImport(
				"P00731meta-mappingE-DIRECT10004-1.json",
				"[E10004] Meta -> category (ENTITY), mapping (DIRECT) ==> Optional {subkey,subtable} Attribute Error!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testP00731Meta10004EDirectExisting2()
			throws AbstractSchemaException {
		testImport(
				"P00731meta-mappingE-DIRECT10004-2.json",
				"[E10004] Meta -> category (ENTITY), mapping (DIRECT) ==> Optional {subkey,subtable} Attribute Error!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testP00741Meta10004ECombinatedExisting1()
			throws AbstractSchemaException {
		testImport(
				"P00741meta-mappingE-COMBINATED10004-1.json",
				"[E10004] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subkey,subtable} Attribute Error!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testP00741Meta10004ECombinatedExisting2()
			throws AbstractSchemaException {
		testImport(
				"P00741meta-mappingE-COMBINATED10004-2.json",
				"[E10004] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subkey,subtable} Attribute Error!");
	}

	/**
	 * 表名以下划线开头
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP00742Meta10005ECombinatedValue1()
			throws AbstractSchemaException {
		testImport(
				"P00742meta-mappingE-COMBINATED10003-1.json",
				"[E10003] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute must be matching!");
	}

	/**
	 * 表名前缀长度不对
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP00742Meta10005ECombinatedValue2()
			throws AbstractSchemaException {
		testImport(
				"P00742meta-mappingE-COMBINATED10003-2.json",
				"[E10003] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute must be matching!");
	}

	/**
	 * 包含了特殊符号
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP00742Meta10005ECombinatedValue3()
			throws AbstractSchemaException {
		testImport(
				"P00742meta-mappingE-COMBINATED10003-3.json",
				"[E10003] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute must be matching!");
	}

	/**
	 * 包含了特殊符号
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = DuplicatedTablesException.class)
	public void testP00742Meta10005ECombinatedValue4()
			throws AbstractSchemaException {
		testImport(
				"P00743meta-mappingE-COMBINATED10020-1.json",
				"[E10020] Meta -> category (ENTITY), mapping (COMBINATED) ==> Optional {subtable} Attribute must be not the same as {table}!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testP010Meta10004PolicyIncrement1()
			throws AbstractSchemaException {
		testImport(
				"P010meta-policyINCREMENT10004-1.json",
				"[E10004] Meta -> policy (INCREMENT) ==> Optional {seqinit,seqstep} Attribute Error!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testP010Meta10004PolicyIncrement2()
			throws AbstractSchemaException {
		testImport(
				"P010meta-policyINCREMENT10004-2.json",
				"[E10004] Meta -> policy (INCREMENT) ==> Optional {seqinit,seqstep} Attribute Error!");
	}

	/**
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP011Meta10005PolicyIncrement1()
			throws AbstractSchemaException {
		testImport(
				"P011meta-policyINCREMENT10003-1.json",
				"[E10003] Meta -> policy (INCREMENT) ==> Optional {seqinit, seqstep} Attribute must be matching!");
	}

	/**
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testP011Meta10005PolicyIncrement2()
			throws AbstractSchemaException {
		testImport(
				"P011meta-policyINCREMENT10003-2.json",
				"[E10003] Meta -> policy (INCREMENT) ==> Optional {seqinit, seqstep} Attribute must be matching!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
