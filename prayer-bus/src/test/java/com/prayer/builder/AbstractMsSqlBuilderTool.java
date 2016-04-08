package com.prayer.builder;

import com.prayer.facade.constant.DBConstants;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMsSqlBuilderTool extends AbstractBuilderTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected String getCategory() {
        return DBConstants.CATEGORY_MSSQL;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
