package com.prayer.record.data;

import com.prayer.constant.DBConstants;
import com.prayer.dao.impl.data.DataRecordDalor;

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

    /** **/
    @Override
    protected Class<?> getTarget() {
        return DataRecordDalor.class;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
