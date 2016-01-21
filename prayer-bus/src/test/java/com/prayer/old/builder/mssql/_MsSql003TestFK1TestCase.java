package com.prayer.old.builder.mssql;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractException;
import com.prayer.dao.impl.old.builder.MsSqlBuilder;
import com.prayer.facade.schema.Schema;
import com.prayer.old.builder.AbstractBCPTestCase;

/**
 * 
 * @author Lang
 *
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class _MsSql003TestFK1TestCase extends AbstractBCPTestCase {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final String DB_CATEGORY = "MSSQL";
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_MsSql003TestFK1TestCase.class);

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
    public void setUp() throws AbstractException{
        if(null != this.getVerifier().verifyTable("TST_FKP003")){
            this.getContext().executeBatch("CREATE TABLE TST_FKP003( T_ID BIGINT PRIMARY KEY );");
        }
        this.beforeExecute("MsSqlP003TestFK1.json", "tst.mod.fk1");
        final Schema prepSchema = this.getService().get("tst.mod.fk1");
        this.oldBuilder.syncTable(prepSchema);
    }

    /** **/
    @Test
    public void test001UKCreate() {
        final boolean ret = this.createTable();
        assertTrue("[T] Created Table Successfully ! Result = " + ret, ret);
        // Post
        if (ret) {
            this.oldBuilder.purgeTable();
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
    public void setDown() {
        this.afterExecute();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
