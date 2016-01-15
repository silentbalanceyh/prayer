package com.prayer.schema.ruler;

import org.junit.After;
import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.constant.DBConstants;
import com.prayer.constant.Resources;
import com.prayer.exception.schema.BKeyConstraintInvalidException;

/**
 * 
 * @author Lang
 *
 */
public class _00746ConstraintVerifierTestCase extends AbstractVerifierTestCase {
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
        // For testP019Subtable1Rel10013();
        // 如果存在该表就删除
        this.purgeTable("TST_SUB_ROLE1");
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BKeyConstraintInvalidException.class)
    public void testP00746Meta10029ECombinatedConstraints() throws AbstractException {
        // TODO: 目前只有SQL模式才检查
        if (Resources.DB_MODE.equals(DBConstants.MODE_SQL)) {
            int ret = Constants.RC_FAILURE;
            // 创建临时子表
            if (null != validator().verifyTable("TST_SUB_ROLE1")) {
                ret = this.connection().executeBatch("CREATE TABLE TST_SUB_ROLE1( K_ID VARCHAR(236) );");
            }
            if (Constants.RC_SUCCESS == ret) {
                testImport("P00746meta-mappingE-COMBINATED10029-1.json");
                failure("[E10029] Database Constraints Error for subtable, subkey ! ");
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
