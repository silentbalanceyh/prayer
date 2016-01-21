package com.prayer.builder.mssql;

import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractException;
import com.prayer.builder.AbstractMsSqlBDTestCase;
import com.prayer.constant.Accessors;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.pool.impl.jdbc.RecordConnImpl;

/**
 * 
 * @author Lang
 *
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class MsSql003FK1TestCase extends AbstractMsSqlBDTestCase {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSql003FK1TestCase.class);

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
    /** 全部执行完 **/
    @BeforeClass
    public static void setUp() throws AbstractException {
        final DataValidator verifier = singleton(Accessors.validator());
        final JdbcConnection connection = singleton(RecordConnImpl.class);
        if (null != verifier.verifyTable("TST_FKP003")) {
            connection.executeBatch("CREATE TABLE TST_FKP003( T_ID BIGINT PRIMARY KEY );");
        }
    }

    /** 全部执行完 **/
    @AfterClass
    public static void setDown() throws AbstractException {
        final DataValidator verifier = singleton(Accessors.validator());
        final JdbcConnection connection = singleton(RecordConnImpl.class);
        if (null == verifier.verifyTable("TST_FKP003")) {
            connection.executeBatch("DROP TABLE TST_FKP003;");
        }
    }

    /** **/
    @Test
    public void test001UK1Sync() throws AbstractException {
        boolean ret = this.executeSyncContainer("MsSqlP003TestFK1.json");
        assertTrue(ret);
    }

    /** **/
    @Test
    public void test002UK1Purge() throws AbstractException {
        boolean ret = this.executePurgeContainer("MsSqlP003TestFK1.json");
        assertTrue(ret);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
