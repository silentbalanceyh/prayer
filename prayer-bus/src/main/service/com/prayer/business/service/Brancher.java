package com.prayer.business.service;

import static com.prayer.util.debug.Log.peError;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractException;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class Brancher {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Brancher.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 是否进入Update流程，主要用于Save方法
     * 
     * @param record
     * @return
     */
    public static boolean isUpdate(@NotNull @InstanceOf(Record.class) final Record record) {
        boolean isUpdate = true;
        try {
            final ConcurrentMap<String, Value<?>> idKV = record.idKV();
            for (final String id : idKV.keySet()) {
                final Value<?> value = idKV.get(id);
                if (null == value.getValue()) {
                    isUpdate = false;
                    break;
                }
            }
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
        return isUpdate;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Brancher() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
