package com.prayer.business;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.service.RecordBehavior;
import com.prayer.facade.business.service.RecordService;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractService extends AbstractBusiness{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String getFolder(){
        return "business/service/";
    }
    // ~ Methods =============================================
    /**
     * Service: Service - Behavior
     * 
     * @return
     */
    public RecordService getRecordSrv() {
        return singleton(RecordBehavior.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
