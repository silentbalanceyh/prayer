package com.prayer.business.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.business.endpoint.MetaStubor;
import com.prayer.fantasm.business.endpoint.AbstractMessager;
import com.prayer.model.crucial.MetaRecord;

/**
 * 
 * @author Lang
 *
 */
public final class MetaMessager extends AbstractMessager implements MetaStubor {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaMessager.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** Data Record **/
    public MetaMessager() {
        super(MetaRecord.class);
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
