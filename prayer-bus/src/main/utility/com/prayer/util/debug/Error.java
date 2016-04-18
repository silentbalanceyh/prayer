package com.prayer.util.debug;

import java.text.MessageFormat;

import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;

import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public final class Error { // NOPMD
    // ~ Static Fields =======================================
    // instance(PropertyKit.class.getName(), Error.class,
    // Resources.ERR_PROP_FILE);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param errorCode
     * @param params
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    public static String error(@Max(-10000) final int errorCode, final Object... params) {
        return error(null, errorCode, params);
    }

    /**
     * 
     * @param clazz
     * @param errorCode
     * @param params
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    public static String error(@NotNull final Class<?> clazz, @Max(-10000) final int errorCode,
            final Object... params) {
        return format(clazz, errorKey('E', errorCode), params);
    }

    /**
     * 直接从属性文件中提取Patterns，然后根据参数执行格式化
     * 
     * @param errKey
     * @param params
     * @return
     */
    @NotNull
    public static String message(@NotNull final String errKey, final Object... params) {
        final String errMsg = InceptBus.build(Point.Error.class).getString(errKey);
        return MessageFormat.format(errMsg, params);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private static String errorKey(final char prefix, final int errorCode) {
        return prefix + String.valueOf(Math.abs(errorCode));
    }

    private static String format(final Class<?> clazz, final String errKey, final Object... params) {
        final StringBuilder errMsg = new StringBuilder(32);
        errMsg.append('[').append(errKey).append(']');
        if (null != clazz) {
            errMsg.append(" Class -> " + clazz.getName() + " |");
        }
        errMsg.append(' ').append(message(errKey, params));
        return errMsg.toString();
    }

    private Error() {
    }
    // ~ hashCode,equals,toString ============================

}
