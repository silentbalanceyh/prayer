package com.prayer.builder.mssql;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.builder.AbstractMsSqlBuilderTool;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class MsSql001UK2TestCase extends AbstractMsSqlBuilderTool {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSql001UK2TestCase.class);

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
    public void test001UK1Sync() throws AbstractException {
        boolean ret = this.executeSyncContainer("MsSqlP001TestUK2.json");
        assertTrue(ret);
    }

    /** **/
    @Test
    public void test002UK1Purge() throws AbstractException {
        boolean ret = this.executePurgeContainer("MsSqlP001TestUK2.json");
        assertTrue(ret);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
