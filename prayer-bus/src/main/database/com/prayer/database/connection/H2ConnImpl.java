package com.prayer.database.connection;

import com.prayer.facade.constant.DBConstants;
import com.prayer.fantasm.database.pool.AbstractJdbcConnection;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class H2ConnImpl extends AbstractJdbcConnection {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public H2ConnImpl() {
        super(DBConstants.CATEGORY_H2);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
