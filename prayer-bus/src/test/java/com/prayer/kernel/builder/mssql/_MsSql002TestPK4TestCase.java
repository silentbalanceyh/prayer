package com.prayer.kernel.builder.mssql;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.dao.impl.builder.MsSqlBuilder;
import com.prayer.kernel.builder.AbstractBCPTestCase;

/**
 * 
 * @author Lang
 *
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class _MsSql002TestPK4TestCase extends AbstractBCPTestCase {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final String DB_CATEGORY = "MSSQL";
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_MsSql002TestPK4TestCase.class);

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

    /** **/
    @Override
    protected Class<?> getBuilder() {
        return MsSqlBuilder.class;
    }

    // ~ Methods =============================================
    /** **/
    @Before
    public void setUp() {
        this.beforeExecute("MsSqlP002TestPK4.json", "tst.mod.pk4");
    }

    /** **/
    @Test
    public void test001UKCreate() {
        final boolean ret = this.createTable();
        assertTrue("[T] Created Table Successfully ! Result = " + ret, ret);
        // Post
        if (ret) {
            this.builder.purgeTable();
        }
    }

    /** **/
    @Test
    public void test001UKPurge() {
        final boolean ret = this.purgeTable();
        assertTrue("[T] Purge Table Successfully ! Result = " + ret, ret);
    }

    /** **/
    @After
    public void setDown() throws AbstractDatabaseException{
        this.afterExecute();
        this.pushData("tst.mod.pk4");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
