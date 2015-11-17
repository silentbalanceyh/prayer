package com.prayer.kernel.validator;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class NotNullValidator implements Validator {    // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull final Value<?> value, @NotNull @MinSize(0) final Object... params)
            throws AbstractDatabaseException {
        boolean ret = false;
        if (null != value.getValue() && StringKit.isNonNil(value.getValue().toString())) {
            ret = true;
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
