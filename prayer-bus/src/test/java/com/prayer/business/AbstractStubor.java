package com.prayer.business;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.endpoint.DataMessager;
import com.prayer.business.endpoint.MetaMessager;
import com.prayer.facade.business.endpoint.DataStubor;
import com.prayer.facade.business.endpoint.MetaStubor;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractStubor extends AbstractBusiness {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String getFolder() {
        return "business/stubor/";
    }

    // ~ Methods =============================================
    /**
     * Restful：Stubor - Messager
     * 
     * @return
     */
    protected DataStubor getDataStubor() {
        return singleton(DataMessager.class);
    }

    /**
     * Restful：Stubor - Messager
     * 
     * @return
     */
    protected MetaStubor getMetaStubor() {
        return singleton(MetaMessager.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
