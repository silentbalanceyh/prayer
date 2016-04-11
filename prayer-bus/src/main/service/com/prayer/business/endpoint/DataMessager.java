package com.prayer.business.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.business.endpoint.DataStubor;
import com.prayer.fantasm.business.endpoint.AbstractMessager;
import com.prayer.model.crucial.DataRecord;

/**
 * 
 * @author Lang
 *
 */
public final class DataMessager extends AbstractMessager implements DataStubor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DataMessager.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** Data Record **/
    public DataMessager(){
        super(DataRecord.class);
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
