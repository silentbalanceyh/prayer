package com.prayer.builder.mssql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.builder.AbstractMsSqlBuilderTool;

/**
 * 外键语句有问题
 * @author Lang
 *
 */
public class DebugBuilderTestCase extends AbstractMsSqlBuilderTool{
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DebugBuilderTestCase.class);
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
//    /** **/
//    @Test
//    public void testREL_A_G() throws AbstractException {
//        final Schema schema = this.prepare("rel.account.group.json");
//        this.builder().synchronize(schema);
//        System.out.println(schema.getForeignField().size());
//    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
