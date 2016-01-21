package com.prayer.plugin.validator;

import java.util.Arrays;

import com.prayer.exception.database.ValidatorConflictException;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class MaxValidator implements Validator { // NOPMD
    // ~ Static Fields =======================================
    /** 符合该验证器的属性，关于Data的验证使用新的Data验证器 **/
    private static final DataType[] T_REQUIRED = new DataType[] { DataType.INT, DataType.LONG };

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull @InstanceOf(Value.class) final Value<?> value,
            @NotNull @Size(min = 1, max = 1) final Object... params) throws AbstractDatabaseException {
        // 类型冲突
        if (!Arrays.asList(T_REQUIRED).contains(value.getDataType())) {
            throw new ValidatorConflictException(getClass(), value.getDataType().toString(), "min");
        }
        boolean ret = false;
        if (null != params[0]) {
            final Long maxValue = Long.parseLong(params[0].toString());
            final Long range = Long.parseLong(value.getValue().toString());
            if (maxValue < range) {
                ret = false;
            } else {
                ret = true;
            }
        }
        return ret;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
