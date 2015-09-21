package com.prayer.kernel.validator;

import static com.prayer.util.Error.info;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
final class PatternValidator implements Validator {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PatternValidator.class);
    /** 符合该验证器的属性 **/
    private static final DataType[] T_REQUIRED = new DataType[] { DataType.STRING, DataType.XML, DataType.JSON,
            DataType.SCRIPT };

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
        // 冲突
        if (!Arrays.asList(T_REQUIRED).contains(value.getDataType())) {
            throw new ValidatorConflictException(getClass(), value.getDataType().toString(), "pattern");
        }
        boolean ret = false;
        // 检查Pattern是否符合
        if (null == params[0]) {
            info(LOGGER, "[E] Param[0] is null and execution error !");
        } else {
            final Pattern pattern = Pattern.compile(params[0].toString());
            final Matcher matcher = pattern.matcher(value.literal());
            ret = matcher.matches();
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
