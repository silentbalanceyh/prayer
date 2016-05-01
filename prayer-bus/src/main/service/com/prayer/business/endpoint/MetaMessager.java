package com.prayer.business.endpoint;

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
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
