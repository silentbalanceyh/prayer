package com.prayer.business.instantor.util;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;

import com.prayer.facade.business.instantor.uca.UtilInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractException;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class UtilBllor implements UtilInstantor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final CommonQuerier querier = singleton(CommonQuerier.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Override
    public boolean checkUnique(@NotNull @NotBlank @NotEmpty final String identifier,
            @NotNull @NotBlank @NotEmpty final String field, @NotNull @NotBlank @NotEmpty final String value)
            throws AbstractException {
        final List<Record> records = this.querier.queryMuster(identifier, field, value);
        boolean ret = false;
        if (Constants.ZERO < records.size()) {
            ret = true;
        }
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
