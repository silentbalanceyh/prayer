package com.test.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.schema.json.internal.CommunionImporter;

/**
 * 
 * @author Lang
 *
 */
public class _17DeserializerTestCase extends AbstractSchemaTestCase {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_17DeserializerTestCase.class);

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
	 */
	@Test
	public void testDeserializer() {
		importer = new CommunionImporter("/schema/system/data/json/role.json");
		// 1.Read Schema File
		try {
			importer.importFile();
		} catch (AbstractSystemException ex) {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("1.Error when reading json schema file.", ex);
			}
			failure(ex.toString());
		}
		// 2.Validate Schema File
		try {
			importer.ensureSchema();
		} catch (AbstractSchemaException ex) {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("2.Error when verifying json schema.", ex);
			}
			failure(ex.toString());
		}
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
