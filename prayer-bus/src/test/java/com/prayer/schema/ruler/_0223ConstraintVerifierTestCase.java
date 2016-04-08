package com.prayer.schema.ruler;

import org.junit.After;
import org.junit.Test;

import com.prayer.constant.Resources;
import com.prayer.exception.schema.BKeyConstraintInvalidException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.DBConstants;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;

public class _0223ConstraintVerifierTestCase extends AbstractVerifierTool {
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
        this.purgeTable("TST_SUB_ROLE1");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BKeyConstraintInvalidException.class)
    public void testP22FKey1Target10029() throws AbstractException {
        // TODO: 目前只有SQL模式才检查
        if (Resources.DB_MODE.equals(DBConstants.MODE_SQL)) {
            int ret = Constants.RC_FAILURE;
            // 创建子表，防止10027
            if (null != validator().verifyTable("TST_SUB_ROLE1")) {
                ret = this.connection().executeBatch("CREATE TABLE TST_SUB_ROLE1( R_ID VARCHAR(236) );");
            }
            if (Constants.RC_SUCCESS == ret) {
                testImport("rels/P0213field-FkCType10029-1.json");
                failure("[E10029] Target column constraint is invalid please verify the result!");
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
