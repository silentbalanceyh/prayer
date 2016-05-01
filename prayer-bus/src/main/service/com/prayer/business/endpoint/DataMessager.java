package com.prayer.business.endpoint;

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
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
