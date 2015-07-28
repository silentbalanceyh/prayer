package com.prayer.kernel.model;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractDaoTestTool;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.type.StringType;

/**
 * 
 * @author Lang
 *
 */
public class GenericRecord02TestCase extends AbstractDaoTestTool { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericRecord02TestCase.class);
	/** **/
	private static final String DB_CATEGORY = "MSSQL";
	/** **/
	private static final String IDENTIFIER = "tst.mod.dao8";
	/** **/
	private static final Value<?> STR_VAL = new StringType("");

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
		return GenericRecord.class;
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
		final ServiceResult<GenericSchema> ret = this.syncMetadata("MsSqlP002OpTestDAO8.json", IDENTIFIER);
		if (ResponseCode.FAILURE == ret.getResponseCode()) {
			failure(TST_PREP, ret.getErrorMessage());
		}
	}

	// ~ Methods =============================================
	/** **/
	@Test
	public void testT05059Mset() throws AbstractMetadataException{
		final Record record = this.getRecord(IDENTIFIER);
		record.set("uk1", "Validator");
	}
	// ~ Private Methods =====================================

	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
