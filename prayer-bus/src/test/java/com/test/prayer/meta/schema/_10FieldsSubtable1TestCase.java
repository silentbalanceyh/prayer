package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.SubtableNotMatchException;

/**
 * 
 * @author Lang
 *
 */
public class _10FieldsSubtable1TestCase extends AbstractSchemaTestCase {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_10FieldsSubtable1TestCase.class);
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
	@Test(expected = SubtableNotMatchException.class)
	public void testP019Subtable1Rel10013() throws AbstractSchemaException {
		testImport(
				"rels/P019field-Subtable10013-1.json",
				"[E10013] Fields -> subtalbe ==> (Failure) Attribute 'subtalbe' must exist because mapping = COMBINATED! ");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
