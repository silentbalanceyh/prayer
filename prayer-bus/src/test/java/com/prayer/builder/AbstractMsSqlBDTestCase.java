package com.prayer.builder;

import com.prayer.constant.DBConstants;
import com.prayer.constant.Resources;
import com.prayer.util.string.StringKit;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMsSqlBDTestCase extends AbstractBDTestCase {
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

    /** **/
    protected boolean validDB() {
        return StringKit.equals(getCategory(), Resources.DB_CATEGORY);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
