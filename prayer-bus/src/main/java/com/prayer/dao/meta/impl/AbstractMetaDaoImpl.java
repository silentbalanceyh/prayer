package com.prayer.dao.meta.impl;

import java.io.Serializable;

import org.slf4j.Logger;

import com.prayer.dao.record.RecordDao;
import com.prayer.schema.dao.TemplateDao;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractMetaDaoImpl<T, ID extends Serializable> implements RecordDao {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** Overwrite by sub class **/
    public abstract TemplateDao<T, ID> getDao();

    /** Logger Reference **/
    public abstract Logger getLogger();
    
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
