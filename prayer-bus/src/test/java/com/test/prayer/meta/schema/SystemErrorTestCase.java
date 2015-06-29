package com.test.prayer.meta.schema;

import net.sf.oval.exception.ConstraintsViolatedException;

import org.junit.Test;

import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.JsonParserException;
import com.prayer.meta.schema.Importer;
import com.prayer.meta.schema.json.GenericImporter;
import com.test.AbstractTestCase;

/**
 * 
 * @author Lang
 * @see
 */
public class SystemErrorTestCase extends AbstractTestCase {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient Importer importer;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public SystemErrorTestCase() {
		super(GenericImporter.class.getName());
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testConstructor1() {
		setMethod("Constructor(null)");
		importer = new GenericImporter(null);
		failure(importer);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testConstructor2() {
		setMethod("Constructor(Empty)");
		importer = new GenericImporter("");
		failure(importer);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testConstructor3() {
		setMethod("Constructor(Blank)");
		importer = new GenericImporter("  ");
		failure(importer);
	}

	/** **/
	@Test(expected = JsonParserException.class)
	public void test20003() throws AbstractSystemException {
		setMethod("importFile()");
		importer = new GenericImporter("/schema/system/data/json/role20003.txt");
		importer.importFile();
		failure("There is no expected exception!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
