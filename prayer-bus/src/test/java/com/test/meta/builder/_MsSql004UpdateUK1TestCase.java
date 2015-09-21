package com.test.meta.builder;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class _MsSql004UpdateUK1TestCase extends AbstractBUPTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final String DB_CATEGORY = "MSSQL";
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_MsSql004UpdateUK1TestCase.class);

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
    protected String getDbCategory() {
        return DB_CATEGORY;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testDatabaseUpdate1() {
        final ServiceResult<GenericSchema> ret = this.testUpdating("MsSqlP004Update1FromUK1.json",
                "MsSqlP004Update1ToUK1.json", "[T] Update schema met errors !");
        assertEquals(ret.getErrorMessage(), ResponseCode.SUCCESS, ret.getResponseCode());
    }
    
    /** **/
    @Test
    public void testDatabaseUpdate2() {
        final ServiceResult<GenericSchema> ret = this.testUpdating("MsSqlP004Update2FromUK2.json",
                "MsSqlP004Update2ToUK2.json", "[T] Update schema met errors !");
        assertEquals(ret.getErrorMessage(), ResponseCode.SUCCESS, ret.getResponseCode());
    }

    /** **/
    @After
    public void setDown() {
        // this.afterExecute();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
