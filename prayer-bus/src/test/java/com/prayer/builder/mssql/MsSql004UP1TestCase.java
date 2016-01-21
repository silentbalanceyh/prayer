package com.prayer.builder.mssql;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.builder.AbstractMsSqlBDTestCase;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class MsSql004UP1TestCase extends AbstractMsSqlBDTestCase {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSql004UP1TestCase.class);

    // ~ Instance Fields =====================================
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
    @Test
    public void test001UP1Updated() throws AbstractException {
        boolean ret = this.executeUpdateContainer("MsSqlP004Update1FromUK1.json", "MsSqlP004Update1ToUK1.json");
        assertTrue(ret);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
