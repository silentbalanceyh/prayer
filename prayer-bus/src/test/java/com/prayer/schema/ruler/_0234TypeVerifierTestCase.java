package com.prayer.schema.ruler;

import org.junit.After;
import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.BTColumnTypeInvalidException;

/** **/
public class _0234TypeVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    @After
    public void setDown() {
        // 如果存在该表就删除
        this.purgeTable("TST_SUB_ROLE2");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTColumnTypeInvalidException.class)
    public void testP23FKey1Target10030() throws AbstractException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != validator().verifyTable("TST_SUB_ROLE2")) {
            ret = this.connection().executeBatch("CREATE TABLE TST_SUB_ROLE2( R_ID BIGINT PRIMARY KEY );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("rels/P0213field-FkCType10030-1.json");
            failure("[E10030] Target column data type is invalid please verify the result!");
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
