package com.prayer.database.pool.impl.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.DBConstants;
import com.prayer.constant.Resources;
import com.prayer.fantasm.pool.AbstractJdbcConnection;
import com.prayer.util.io.PropertyKit;

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
    private static final PropertyKit LOADER = new PropertyKit(H2ConnImpl.class, Resources.DB_CFG_FILE);
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
