package com.prayer.dao.impl.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractConn;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordConnImpl extends AbstractConn {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordConnImpl.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public RecordConnImpl() {
        super(null);
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
