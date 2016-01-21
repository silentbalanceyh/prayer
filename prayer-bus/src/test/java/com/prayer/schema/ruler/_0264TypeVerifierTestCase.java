package com.prayer.schema.ruler;

import org.junit.After;
import org.junit.Test;

import com.prayer.constant.Constants;
import com.prayer.constant.DBConstants;
import com.prayer.constant.Resources;
import com.prayer.exception.schema.BTColumnTypeInvalidException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;

/** **/
public class _0264TypeVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @After
    public void setDown() {
        // 如果存在该表就删除
        this.purgeTable("TST_SUB_TST1");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTColumnTypeInvalidException.class)
    public void testP35Cross5Attr10030() throws AbstractException {
        // TODO: 目前只有SQL模式才检查
        if (Resources.DB_MODE.equals(DBConstants.MODE_SQL)) {
            int ret = Constants.RC_FAILURE;
            // 创建子表，防止10027
            if (null != validator().verifyTable("TST_SUB_TST1")) {
                ret = this.connection().executeBatch("CREATE TABLE TST_SUB_TST1( K_ID BIGINT PRIMARY KEY );");
            }
            if (Constants.RC_SUCCESS == ret) {
                testImport("zkeys/P035cross-10024-5.json");
                failure("[E10030] Target column data type is invalid please verify the result!");
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
