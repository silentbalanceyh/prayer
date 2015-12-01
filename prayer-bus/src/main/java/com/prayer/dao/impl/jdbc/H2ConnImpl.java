package com.prayer.dao.impl.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractConn;
import com.prayer.util.PropertyKit;
import com.prayer.util.cv.Resources;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class H2ConnImpl extends AbstractConn{
    // ~ Static Fields =======================================
    /** **/
    private static final PropertyKit LOADER = new PropertyKit(H2ConnImpl.class,Resources.DB_CFG_FILE);
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(H2ConnImpl.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static String getH2Password(){
        String ret = null;
        if(null != LOADER){
            ret = LOADER.getString("H2.jdbc.password");
        }
        return ret;
    }
    /** **/
    public static String getH2User(){
        String ret = null;
        if(null != LOADER){
            ret = LOADER.getString("H2.jdbc.username");
        }
        return ret;
    }
    /** **/
    public static String getH2DatabaseName(){
        String ret = null;
        if(null != LOADER){
            ret = LOADER.getString("H2.jdbc.database.name");
        }
        return ret;
    }
    // ~ Constructors ========================================
    /** **/
    public H2ConnImpl(){
        super("H2");
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
