package com.prayer.kernel.validator;

import static com.prayer.util.Error.info;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractMetadataException;
import com.prayer.exception.metadata.ValidatorConflictException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;

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
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MaxValidator.class);
    /** 符合该验证器的属性 **/
    // TODO：等待加入Date验证
    private static final DataType[] T_REQUIRED = new DataType[] { DataType.INT, DataType.LONG };

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull final Value<?> value, @NotNull @Size(min = 1, max = 1) final Object... params)
            throws AbstractMetadataException {
        // 类型冲突
        if (!Arrays.asList(T_REQUIRED).contains(value.getDataType())) {
            throw new ValidatorConflictException(getClass(), value.getDataType().toString(), "min");
        }
        boolean ret = false;
        if (null == params[0]) {
            info(LOGGER, "[E] Param[0] is null and execution error!");
        } else {
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
