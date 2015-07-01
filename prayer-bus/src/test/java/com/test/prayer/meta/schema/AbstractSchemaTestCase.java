package com.test.prayer.meta.schema;

import org.slf4j.Logger;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.meta.schema.Importer;
import com.prayer.meta.schema.json.GenericImporter;
import com.test.AbstractTestCase;

/**
 * 
 * @author Lang
 * @see
 */
public abstract class AbstractSchemaTestCase extends AbstractTestCase {
	// ~ Static Fields =======================================
	/** **/
	protected static final String M_IMPORT_FILE = "importFile()";
	// ~ Instance Fields =====================================
	/** **/
	protected transient Importer importer;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractSchemaTestCase() {
		super(GenericImporter.class.getName());
	}

	// ~ Abstract Methods ====================================
	/** **/
	public abstract Logger getLogger();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @param inputFile
	 * @param errMsg
	 */
	protected void testImport(final String inputFile, final String errMsg)
			throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new GenericImporter(inputFile);
		try {
			importer.importFile();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug(errMsg, ex);
			}
		}
		failure(errMsg);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
