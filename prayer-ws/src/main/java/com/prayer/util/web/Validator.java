package com.prayer.util.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.facade.constant.Constants;

import net.sf.oval.constraint.Min;
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
public final class Validator { // NOPMD
    // ~ Static Fields =======================================

    /**
     * 比较数值的长度范围
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static boolean verifyRange(@NotNull final Number value, @NotNull final Number min, // NOPMD
            @NotNull final Number max) {
        boolean flag = false;
        final double range = Constants.RANGE;
        final double minV = min.doubleValue();
        final double maxV = max.doubleValue();
        if (range == minV && range == maxV) {
            // 两个参数都为边界值，则表示当前调用不检查，直接返回TRUE
            flag = true;
        } else if (range == minV && range != maxV) {
            // 最小值为边界值，检查max
            if (value.doubleValue() <= maxV) {
                flag = true;
            }
        } else if (range != minV && range == maxV) {
            // 最大值为边界值，检查min
            if (minV <= value.doubleValue()) {
                flag = true;
            }
        } else if (range != minV && range != maxV && minV <= value.doubleValue() && value.doubleValue() <= maxV) {
            flag = true;
        }
        return flag;
    }

    /**
     * 比较字符串的长度范围
     * @param value
     * @param minLength
     * @param maxLength
     * @return
     */
    public static boolean verifyLength(@NotNull @NotBlank @NotEmpty final String value, @Min(-1) final int minLength, // NOPMD
            @Min(-1) final int maxLength) {
        boolean flag = false;
        final int length = value.length();
        if (Constants.RANGE == minLength && Constants.RANGE == maxLength) {
            // 两个参数都为边界值，则表示当前调用不检查，直接返回TRUE
            flag = true;
        } else if (Constants.RANGE == minLength && Constants.RANGE != maxLength) {
            // 最小值为边界值，则只检查MaxLength
            if (length <= maxLength) {
                flag = true;
            }
        } else if (Constants.RANGE != minLength && Constants.RANGE == maxLength) {
            // 最大值为边界值，则只检查MinLength
            if (minLength <= length) {
                flag = true;
            }
        } else if (Constants.RANGE != minLength && Constants.RANGE != maxLength && minLength <= length
                && length <= maxLength) {
            flag = true;
        }
        return flag;
    }

    /**
     * 
     * @param value
     * @param regex
     * @return
     */
    public static boolean verifyPattern(@NotNull @NotBlank @NotEmpty final String value,
            @NotNull @NotBlank @NotEmpty final String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    
    private Validator() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
