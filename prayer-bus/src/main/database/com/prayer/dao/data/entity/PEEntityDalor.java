package com.prayer.dao.data.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.fantasm.database.AbstractMetaDalor;

/**
 * 
 * @author Lang
 *
 */
public class PEEntityDalor extends AbstractMetaDalor<String> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEEntityDalor.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 日志器 **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
