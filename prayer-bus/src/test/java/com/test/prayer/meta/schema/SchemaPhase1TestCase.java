package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.schema.AttrJsonTypeException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.exception.schema.UnsupportAttrException;
import com.prayer.meta.schema.Importer;
import com.prayer.meta.schema.json.GenericImporter;
import com.test.AbstractTestCase;

/**
 * 
 * @author Lang
 * @see
 */
public class SchemaPhase1TestCase extends AbstractTestCase {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SchemaPhase1TestCase.class);
	/** **/
	private static final String M_IMPORT_FILE = "importFile()";
	// ~ Instance Fields =====================================
	/** **/
	private transient Importer importer;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public SchemaPhase1TestCase() {
		super(GenericImporter.class.getName());
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** 
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testFields10001() throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new GenericImporter(
				"/schema/system/data/json/root-fields10001.json");
		try {
			importer.importFile();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E10001] Fields ==> Unexpected exception happened!", ex);
			}
		}
		failure("[E10001] Fields ==> (Failure) There is unexpected exception!");
	}
	/** 
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testKeys10001() throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new GenericImporter(
				"/schema/system/data/json/root-keys10001.json");
		try {
			importer.importFile();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E10001] Keys ==> Unexpected exception happened!", ex);
			}
		}
		failure("[E10001] Keys ==> (Failure) There is unexpected exception!");
	}
	/** 
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = RequiredAttrMissingException.class)
	public void testMeta10001() throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new GenericImporter(
				"/schema/system/data/json/root-meta10001.json");
		try {
			importer.importFile();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E10001] Meta ==> Unexpected exception happened!", ex);
			}
		}
		failure("[E10001] Meta ==> (Failure) There is unexpected exception!");
	}
	
	/** 
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = AttrJsonTypeException.class)
	public void testFields10002() throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new GenericImporter(
				"/schema/system/data/json/root-fields10002.json");
		try {
			importer.importFile();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E10002] Fields ==> Unexpected exception happened!", ex);
			}
		}
		failure("[E10002] Fields ==> (Failure) There is unexpected exception!");
	}
	/** 
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = AttrJsonTypeException.class)
	public void testKeys10002() throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new GenericImporter(
				"/schema/system/data/json/root-keys10002.json");
		try {
			importer.importFile();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E10002] Keys ==> Unexpected exception happened!", ex);
			}
		}
		failure("[E10002] Keys ==> (Failure) There is unexpected exception!");
	}
	/** 
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = AttrJsonTypeException.class)
	public void testMeta10002() throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new GenericImporter(
				"/schema/system/data/json/root-meta10002.json");
		try {
			importer.importFile();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E10002] Meta ==> Unexpected exception happened!", ex);
			}
		}
		failure("[E10002] Meta ==> (Failure) There is unexpected exception!");
	}
	/** 
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = UnsupportAttrException.class)
	public void testMeta10017() throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new GenericImporter(
				"/schema/system/data/json/root-fields10017.json");
		try {
			importer.importFile();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E10017] Root => Unexpected exception happened!", ex);
			}
		}
		failure("[E10017] Root ==> (Failure) There is unexpected exception!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
