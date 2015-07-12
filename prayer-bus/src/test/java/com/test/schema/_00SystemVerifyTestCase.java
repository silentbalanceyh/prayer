package com.test.schema;

import net.sf.oval.exception.ConstraintsViolatedException;

import org.junit.Test;

import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;
import com.prayer.schema.Importer;
import com.prayer.schema.json.internal.CommunionImporter;
import com.test.AbstractTestCase;

/**
 * 
 * @author Lang
 * @see
 */
public class _00SystemVerifyTestCase extends AbstractTestCase {
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
	public _00SystemVerifyTestCase() {
		super(CommunionImporter.class.getName());
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testConstructor1() {
		setMethod("Constructor(null)");
		importer = new CommunionImporter(null);
		failure(importer);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testConstructor2() {
		setMethod("Constructor(Empty)");
		importer = new CommunionImporter("");
		failure(importer);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testConstructor3() {
		setMethod("Constructor(Blank)");
		importer = new CommunionImporter("  ");
		failure(importer);
	}

	/** **/
	@Test(expected = JsonParserException.class)
	public void testP000System20003() throws AbstractSystemException {
		setMethod("importFile()");
		importer = new CommunionImporter("/schema/system/data/json/P000json20003.txt");
		importer.importFile();
		failure("[E20003] Json Parser ==> (Failure) There is unexpected exception!");
	}

	/** **/
	@Test(expected = ResourceIOException.class)
	public void testP000System20002() throws AbstractSystemException {
		setMethod("importFile()");
		importer = new CommunionImporter("/schema/system/data/json/P000json20003.json");
		importer.importFile();
		failure("[E20002] Resource IO ==> (Failure) There is no expected exception!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}