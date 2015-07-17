package com.test.schema; // NOPMD

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.meta.GenericSchema;
import com.prayer.schema.dao.SchemaDao;
import com.prayer.schema.dao.impl.SchemaDaoImpl;
import com.prayer.schema.json.internal.CommunionImporter;

/**
 * 
 * @author Lang
 *
 */
public class _18SchemaUpdate1TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_18SchemaUpdate1TestCase.class);
	/** **/
	private static final String IDENTIFIER = "tst.mod.schema1";
	// ~ Instance Fields =====================================
	/** **/
	private transient SchemaDao service; // NOPMD

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
	@Before
	public void setUp() {
		service = singleton(SchemaDaoImpl.class);
		importer = new CommunionImporter("/schema/data/json/validation/P012meta-schema1-from.json");
		this.executeSync(IDENTIFIER);
	}

	/**
	 * 
	 */
	@Test
	public void testSchemaUpdate() {
		final GenericSchema oldSchema = this.service.getById(IDENTIFIER);
		info(LOGGER, "[T] Old Schema Meta : => " + oldSchema.getMeta());
		this.importer.refreshSchema("/schema/data/json/validation/P012meta-schema1-to.json");
		this.executeSync(IDENTIFIER);
		final GenericSchema newSchema = this.importer.getSchema();
		info(LOGGER, "[T] New Schema Meta : => " + newSchema.getMeta());
		final int oldKeys = oldSchema.getKeys().size();
		final int newKeys = newSchema.getKeys().size();
		final boolean ret = oldKeys == 3 && newKeys == 2;
		assertTrue("[T-ERROR] Schema Update Failure, please verify the details.", ret);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
