package com.prayer.database.pool.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.DBConstants;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.database.pool.AbstractJdbcConnection;
import com.prayer.resource.Resources;

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
    private static final Inceptor LOADER = Resources.JDBC;
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(H2ConnImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static String getH2Password() {
        String ret = null;
        if (null != LOADER) {
            ret = LOADER.getString(DBConstants.CATEGORY_H2 + ".jdbc.password");
        }
        return ret;
    }

    /** **/
    public static String getH2User() {
        String ret = null;
        if (null != LOADER) {
            ret = LOADER.getString(DBConstants.CATEGORY_H2 + ".jdbc.username");
        }
        return ret;
    }

    /** **/
    public static String getH2DatabaseName() {
        String ret = null;
        if (null != LOADER) {
            ret = LOADER.getString(DBConstants.CATEGORY_H2 + ".jdbc.database.name");
        }
        return ret;
    }

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
