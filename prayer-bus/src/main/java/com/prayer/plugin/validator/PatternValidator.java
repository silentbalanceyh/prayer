package com.prayer.plugin.validator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.exception.database.ValidatorConflictException;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;
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
final class PatternValidator implements Validator { // NOPMD
    // ~ Static Fields =======================================
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
    public boolean validate(@NotNull @InstanceOf(Value.class) final Value<?> value,
            @NotNull @Size(min = 1, max = 1) final Object... params) throws AbstractDatabaseException {
        // 冲突
        if (!Arrays.asList(T_REQUIRED).contains(value.getDataType())) {
            throw new ValidatorConflictException(getClass(), value.getDataType().toString(), "pattern");
        }
        boolean ret = false;
        // 检查Pattern是否符合
        if (null != params[0]) {
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
