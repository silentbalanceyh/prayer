package com.prayer.schema.ruler;

import org.junit.After;
import org.junit.Test;

import com.prayer.exception.schema.BTColumnNotExistingException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.DBConstants;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.resource.Resources;

/**
 * 
 * @author Lang
 *
 */
public class _0222ColumnVerifierTestCase extends AbstractVerifierTool {
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
        this.purgeTable("TST_SUB_ROLE");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTColumnNotExistingException.class)
    public void testP22FKey1Target10028() throws AbstractException {
        // TODO: 目前只有SQL模式才检查
        if (Resources.Data.MODE.equals(DBConstants.MODE_SQL)) {
            int ret = Constants.RC_FAILURE;
            // 创建子表，防止10027
            if (null != validator().verifyTable("TST_SUB_ROLE")) {
                ret = this.connection().executeBatch("CREATE TABLE TST_SUB_ROLE( K_ID VARCHAR(236) );");
            }
            if (Constants.RC_SUCCESS == ret) {
                testImport("rels/P0213field-FkCType10028-1.json");
                failure("[E10028] Target column does not exist, please verify the result!");
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
