package com.prayer.builder;

import com.prayer.constant.DBConstants;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMsSqlBuilderTestCase extends AbstractBuilderTool {
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
