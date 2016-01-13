package com.prayer.schema.old.workflow;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSystemException;
import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;
import com.prayer.facade.schema.OldImporter;
import com.prayer.schema.old.json.CommunionImporter;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 * @see
 */
@Ignore
public class _00SystemVerifyTestCase extends AbstractSchemaTestCase {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    protected static final String M_IMPORT_FILE = "importFile()";
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_00SystemVerifyTestCase.class);

    // ~ Instance Fields =====================================
    /** **/
    protected transient OldImporter oldImporter;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testConstructor1() {
        setMethod("Constructor(null)");
        oldImporter = new CommunionImporter(null);
        failure(oldImporter);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testConstructor2() {
        setMethod("Constructor(Empty)");
        oldImporter = new CommunionImporter("");
        failure(oldImporter);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testConstructor3() {
        setMethod("Constructor(Blank)");
        oldImporter = new CommunionImporter("  ");
        failure(oldImporter);
    }

    /** **/
    @Test(expected = JsonParserException.class)
    public void testP000System20003() throws AbstractSystemException {
        setMethod("importFile()");
        oldImporter = new CommunionImporter(SCHEMA_ROOT + "P000json20003.txt");
        oldImporter.readSchema();
        failure("[E20003] Json Parser ==> (Failure) There is unexpected exception!");
    }

    /** **/
    @Test(expected = ResourceIOException.class)
    public void testP000System20002() throws AbstractSystemException {
        setMethod("importFile()");
        oldImporter = new CommunionImporter(SCHEMA_ROOT + "P000json20003.json");
        oldImporter.readSchema();
        failure("[E20002] Resource IO ==> (Failure) There is no expected exception!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
