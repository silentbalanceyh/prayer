package com.prayer.model.kernel;

import static com.prayer.util.Instance.instance;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractRDaoTestTool;
import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.exception.validator.CustomValidatorException;
import com.prayer.exception.validator.LengthFailureException;
import com.prayer.exception.validator.NotNullFailureException;
import com.prayer.exception.validator.PatternFailureException;
import com.prayer.exception.validator.PrecisionFailureException;
import com.prayer.exception.validator.RangeFailureException;
import com.prayer.facade.kernel.Record;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.kernel.GenericRecord;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.util.cv.SystemEnum.ResponseCode;

/**
 * 
 * @author Lang
 *
 */
public class GenericRecord02TestCase extends AbstractRDaoTestTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericRecord02TestCase.class);
    /** **/
    private static final String DB_CATEGORY = "MSSQL";
    /** **/
    private static final String IDENTIFIER = "tst.mod.dao8";

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
    @Test(expected = PatternFailureException.class)
    public void testT05059Mset() throws AbstractDatabaseException {
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("uk1", "Validator");
    }
    /** **/
    @Test(expected = NotNullFailureException.class)
    public void testT05060Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("uk1", "");
    }
    /** **/
    @Test(expected = LengthFailureException.class)
    public void testT05061Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("muk1", "tst");
    }
    /** **/
    @Test(expected = LengthFailureException.class)
    public void testT05062Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("muk1", "lang.yu@hp.com");
    }
    /** **/
    @Test(expected = RangeFailureException.class)
    public void testT05063Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("tint","2");
    }
    /** **/
    @Test(expected = RangeFailureException.class)
    public void testT05064Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("tint","20000");
    }
    /** **/
    @Test(expected = RangeFailureException.class)
    public void testT05065Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("tlong","2");
    }
    /** **/
    @Test(expected = RangeFailureException.class)
    public void testT05066Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("tlong","223");
    }
    /** **/
    @Test(expected = PrecisionFailureException.class)
    public void testT05067Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("tdecimal","781111211.34");
    }
    /** **/
    @Test(expected = PrecisionFailureException.class)
    public void testT05068Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("tdecimal","73.3");
    }
    
    /** **/
    @Test(expected = CustomValidatorException.class)
    public void testT05069Mset() throws AbstractDatabaseException{
        final Record record = instance(GenericRecord.class.getName(), IDENTIFIER);
        record.set("tstring","15922611448");
    }
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
