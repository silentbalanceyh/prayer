package com.prayer.business.service;

import static com.prayer.util.debug.Log.peError;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.web.PrimaryKeyMissingException;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class Interruptor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Interruptor.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /**
     * 检查Record中是否丢失了主键值
     * 
     * @param record
     * @return
     */
    public static AbstractException jerquePK(@NotNull @InstanceOf(Record.class) final Record record) {
        AbstractException error = null;
        try {
            final ConcurrentMap<String, Value<?>> idKV = record.idKV();
            for (final String id : idKV.keySet()) {
                final Value<?> value = idKV.get(id);
                if (StringKit.isNil(value.literal())) {
                    error = new PrimaryKeyMissingException(Interruptor.class, id);
                }
            }
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
            error = ex;
        }
        return error;
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Interruptor() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
