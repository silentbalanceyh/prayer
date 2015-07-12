package com.test.schema;

import org.slf4j.Logger;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.schema.Importer;
import com.prayer.schema.Serializer;
import com.prayer.schema.json.CommunionSerializer;
import com.prayer.schema.json.internal.CommunionImporter;
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
	/** **/
	protected transient Serializer serializer;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractSchemaTestCase() {
		super(CommunionImporter.class.getName());
		this.serializer = new CommunionSerializer();
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
		importer = new CommunionImporter("/schema/system/data/json/" + inputFile);
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
