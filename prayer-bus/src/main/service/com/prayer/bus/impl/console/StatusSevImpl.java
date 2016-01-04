package com.prayer.bus.impl.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.bus.AbstractMSevImpl;
import com.prayer.facade.bus.console.StatusService;

/**
 * 
 * @author Lang
 *
 */
public class StatusSevImpl extends AbstractMSevImpl implements StatusService {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusSevImpl.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
