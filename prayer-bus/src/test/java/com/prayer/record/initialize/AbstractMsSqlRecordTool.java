package com.prayer.record.initialize;

import com.prayer.facade.constant.DBConstants;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMsSqlRecordTool extends AbstractRecordTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    protected String getCategory() {
        return DBConstants.CATEGORY_MSSQL;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
