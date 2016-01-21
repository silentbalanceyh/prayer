package com.prayer.builder.mssql;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractException;
import com.prayer.builder.AbstractMsSqlBDTestCase;
import com.prayer.facade.schema.Schema;

/**
 * 
 * @author Lang
 *
 */
public class _MsSql001UK1TestCase extends AbstractMsSqlBDTestCase {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_MsSql001UK1TestCase.class);

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
        boolean ret = this.executeContainer(this::test001UK1);
        assertTrue(ret);
    }

    public void test001UK1() throws AbstractException {
        /** 1.准备数据 **/
        final Schema schema = this.prepare("MsSqlP001TestUK1.json");
        /** 2.构建表数据 **/
        this.builder().synchronize(schema);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
