package com.prayer.fantasm.business.deployment.acus;

import com.prayer.database.accessor.impl.MetaAccessorImpl;
import com.prayer.facade.database.accessor.MetaAccessor;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractEntityAcus {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    
    /** 获取对应实体的Accessor **/
    protected MetaAccessor accessor(final Class<?> genericT) {
        return new MetaAccessorImpl(genericT);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
