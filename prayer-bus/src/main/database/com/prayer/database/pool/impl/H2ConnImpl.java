package com.prayer.database.pool.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(H2ConnImpl.class);

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
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
