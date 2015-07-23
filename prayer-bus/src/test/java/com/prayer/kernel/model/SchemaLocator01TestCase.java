package com.prayer.kernel.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractDaoTestTool;
import com.prayer.Assistant;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.model.bus.ServiceResult;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class SchemaLocator01TestCase extends AbstractDaoTestTool {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaLocator01TestCase.class);
	/** **/
	private static final String DB_CATEGORY = "MSSQL";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	/** **/
	@Override
	protected Class<?> getTarget() {
		return SchemaLocator.class;
	}

	/** **/
	@Override
	protected String getDbCategory() {
		return DB_CATEGORY;
	}

	// ~ Set Up Method =======================================
	/** **/
	@Before
	public void setUp() {
		final ServiceResult<GenericSchema> ret = this.syncMetadata("MsSqlP001TestDAO1.json","tst.mod.dao1");
		if (ResponseCode.FAILURE == ret.getResponseCode()) {
			failure(TST_PREP, ret.getErrorMessage());
		}
	}

	// ~ Methods =============================================
	/** **/
	@Test
	public void testC05002Constructor() {
		assertNotNull(message(TST_CONS, getTarget().getName()), Assistant.instance(getTarget()));
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05060MgetSchema() throws SchemaNotFoundException {
		SchemaLocator.getSchema(null);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05061MgetSchema() throws SchemaNotFoundException {
		SchemaLocator.getSchema("");
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05062MgetSchema() throws SchemaNotFoundException {
		SchemaLocator.getSchema("   ");
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = SchemaNotFoundException.class)
	public void testE05063MgetSchema() throws SchemaNotFoundException {
		SchemaLocator.getSchema("tst.mod.daoxx");
		failure(TST_PR);
	}

	/** **/
	@Test
	public void testT05028MgetSchema() throws SchemaNotFoundException {
		final GenericSchema schema = SchemaLocator.getSchema("tst.mod.dao1");
		assertNotNull(message(TST_NULL), schema);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
