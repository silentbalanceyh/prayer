package com.prayer.record.data;

import com.prayer.facade.constant.DBConstants;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMsSqlDaoTool extends AbstractRecordDaoTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    protected String getCategory() {
        return DBConstants.CATEGORY_MSSQL;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
