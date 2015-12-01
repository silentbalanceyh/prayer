package com.prayer.bus.impl.std;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.bus.AbstractSevImpl;
import com.prayer.dao.impl.std.meta.MetaDaoImpl;
import com.prayer.facade.bus.MetaService;
import com.prayer.model.kernel.MetaRecord;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class MetaSevImpl extends AbstractSevImpl implements MetaService {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaSevImpl.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public MetaSevImpl(){
        super(MetaDaoImpl.class,MetaRecord.class);
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
