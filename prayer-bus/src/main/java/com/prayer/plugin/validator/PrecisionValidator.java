package com.prayer.plugin.validator;

import java.math.BigDecimal;

import com.prayer.exception.database.ValidatorConflictException;
import com.prayer.facade.model.crucial.Validator;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.guard.Guarded;

/**
 * 浮点数精度验证
 * 
 * @author Lang
 *
 */
@Guarded
final class PrecisionValidator implements Validator { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull @InstanceOf(Value.class) final Value<?> value,
            @NotNull @Size(min = 2, max = 2) final Object... params) throws AbstractDatabaseException {
        // 类型冲突
        if (DataType.DECIMAL != value.getDataType()) {
            throw new ValidatorConflictException(getClass(), value.getDataType().toString(), "precision");
        }
        boolean ret = false;
        if (null != params[0] && null != params[1]) {
            final BigDecimal currentValue = (BigDecimal) value.getValue();
            if (currentValue.scale() == Integer.parseInt(params[0].toString())
                    && currentValue.precision() <= Integer.parseInt(params[1].toString())) {
                ret = true;
            } else {
                ret = false;
            }
        }
        return ret;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
