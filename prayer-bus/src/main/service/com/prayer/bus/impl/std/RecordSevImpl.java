package com.prayer.bus.impl.std;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.bus.AbstractSevImpl;
import com.prayer.dao.impl.std.record.RecordDaoImpl;
import com.prayer.facade.bus.RecordService;
import com.prayer.model.crucial.DataRecord;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordSevImpl extends AbstractSevImpl implements RecordService {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordSevImpl.class);
    // ~ Instance Fields =====================================

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public RecordSevImpl() {
        super(RecordDaoImpl.class, DataRecord.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @NotNull
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
