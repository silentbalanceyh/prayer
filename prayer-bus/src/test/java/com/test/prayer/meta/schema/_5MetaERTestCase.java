package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.InvalidValueException;
import com.prayer.exception.schema.OptionalAttrMorEException;

/**
 * 
 * @author Lang
 * @see
 */
public class _5MetaERTestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_5MetaERTestCase.class);
	/** **/
	private static final String ERR_OPTIONAL_MSG = "[E10004] Meta -> category (RELATION) ==> Optional {subkey,subtable} Attribute Error!";

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
	public void testMeta1RelationExisting() throws AbstractSchemaException {
		testImport("meta-categoryRELATION10004-1.json", ERR_OPTIONAL_MSG);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testMeta2RelationExisting() throws AbstractSchemaException {
		testImport("meta-categoryRELATION10004-2.json", ERR_OPTIONAL_MSG);
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testMeta1EPartialExisting() throws AbstractSchemaException {
		testImport(
				"meta-mappingE-PARTIAL10004-1.json",
				"[E10004] Meta -> category (ENTITY), mapping (PARTIAL) ==> Optional {subkey,subtable} Attribute Error!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testMeta2EPartialExisting() throws AbstractSchemaException {
		testImport(
				"meta-mappingE-PARTIAL10004-2.json",
				"[E10004] Meta -> category (ENTITY), mapping (PARTIAL) ==> Optional {seqname} Attribute Error!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testMeta3EPartialExisting() throws AbstractSchemaException {
		testImport(
				"meta-mappingE-PARTIAL10004-2.json",
				"[E10004] Meta -> category (ENTITY), mapping (PARTIAL) ==> Optional {seqstep} Attribute Error!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testMeta4EPartialExisting() throws AbstractSchemaException {
		testImport(
				"meta-mappingE-PARTIAL10004-2.json",
				"[E10004] Meta -> category (ENTITY), mapping (PARTIAL) ==> Optional {seqinit} Attribute Error!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = InvalidValueException.class)
	public void testMeta1RelationValue() throws AbstractSchemaException {
		testImport("meta-mappingRELATION10005-1.json",
				"[E10005] Meta -> mapping (RELATION) ==> Attribute mapping must be DIRECT!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = InvalidValueException.class)
	public void testMeta2RelationValue() throws AbstractSchemaException {
		testImport(
				"meta-mappingRELATION10005-2.json",
				"[E10005] Meta -> policy (RELATION) ==> Attribute policy mustn't be COLLECTION, ASSIGNED!");
	}

	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = InvalidValueException.class)
	public void testMeta1EPartialValue() throws AbstractSchemaException {
		testImport("meta-mappingE-PARTIAL10005-1.json",
				"[E10005] Meta -> category (ENTITY), mapping (PARTIAL) ==> Attribute policy must be ASSIGNED!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
