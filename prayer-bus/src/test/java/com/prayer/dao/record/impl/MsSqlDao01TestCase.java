package com.prayer.dao.record.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractDaoTestTool;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Record;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public class MsSqlDao01TestCase extends AbstractDaoTestTool { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlDao01TestCase.class);
	/** **/
	private static final String DB_CATEGORY = "MSSQL";
	/** **/
	private static final String IDENTIFIER = "tst.mod.dao4";

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
		final ServiceResult<GenericSchema> ret = this.syncMetadata("MsSqlP002OpTestDAO4.json", IDENTIFIER);
		if (ResponseCode.FAILURE == ret.getResponseCode()) {
			failure(TST_PREP, ret.getErrorMessage());
		}
	}
	/** **/
	@Test
	public void testT05040Minsert() throws AbstractDatabaseException {
		if(this.isValidDB()){
			final Record before = this.getRecord(IDENTIFIER);
			final Record after = this.getRecordDao().insert(before);
			final boolean ret = before == after;	// NOPMD
			assertTrue(message(TST_TF,Boolean.TRUE),ret);
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
