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
public class _5MetaRelationTestCase extends AbstractSchemaTestCase {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_5MetaRelationTestCase.class);
	/** **/
	private static final String ERR_OPTIONAL_MSG = "[E10004] Meta -> category (RELATION) ==> Optional Attribute Error!";

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
		testImport("/schema/system/data/json/meta-categoryRELATION10004-1.json",
				ERR_OPTIONAL_MSG);
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = OptionalAttrMorEException.class)
	public void testMeta2RelationExisting() throws AbstractSchemaException {
		testImport("/schema/system/data/json/meta-categoryRELATION10004-2.json",
				ERR_OPTIONAL_MSG);
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = InvalidValueException.class)
	public void testMeta1RelationValue() throws AbstractSchemaException {
		testImport("/schema/system/data/json/meta-mappingRELATION10005-1.json",
				"[E10005] Meta -> mapping (RELATION) ==> Attribute mapping must be DIRECT!");
	}
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = InvalidValueException.class)
	public void testMeta2RelationValue() throws AbstractSchemaException {
		testImport("/schema/system/data/json/meta-mappingRELATION10005-2.json",
				"[E10005] Meta -> policy (RELATION) ==> Attribute policy mustn't be COLLECTION, ASSIGNED!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
