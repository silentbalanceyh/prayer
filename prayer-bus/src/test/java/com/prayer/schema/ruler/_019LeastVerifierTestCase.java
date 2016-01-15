package com.prayer.schema.ruler;

import org.junit.After;
import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.SubtableWrongException;

public class _019LeastVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @After
    public void setDown() {
        // For testP019Subtable1Rel10013();
        // 如果存在该表就删除
        this.purgeTable("TST_SUBROLE");
        this.purgeTable("TST_SUBROLE1");
    }
    // ~ Methods =============================================

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = SubtableWrongException.class)
    public void testP019Subtable1Rel10013() throws AbstractException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != validator().verifyTable("TST_SUBROLE")) {
            ret = this.connection().executeBatch("CREATE TABLE TST_SUBROLE( R_ID VARCHAR(256) PRIMARY KEY );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("rels/P019field-Subtable10013-1.json");
            failure("[E10013] Fields -> subtalbe ==> (Failure) Attribute 'subtalbe' must exist because mapping = COMBINATED! ");
        }
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = SubtableWrongException.class)
    public void testP019Subtable2Rel10013() throws AbstractException {
        int ret = Constants.RC_FAILURE;
        // 创建子表，防止10027
        if (null != validator().verifyTable("TST_SUBROLE1")) {
            ret = this.connection().executeBatch("CREATE TABLE TST_SUBROLE1( R_ID VARCHAR(256) UNIQUE );");
        }
        if (Constants.RC_SUCCESS == ret) {
            testImport("rels/P019field-Subtable10013-2.json");
            failure("[E10013] Fields -> subtalbe ==> (Failure) Attribute 'subtalbe' must exist because mapping = COMBINATED! ");
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
