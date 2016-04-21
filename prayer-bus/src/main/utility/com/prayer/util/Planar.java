package com.prayer.util;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.fun.util.FlatTo;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 代码平整器，用于处理平整代码，将If-Else的代码改动掉，形成更加平整的代码流程
 * 
 * @author Lang
 *
 */
@Guarded
public final class Planar {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================

    /**
     * 如果value为null则返回dftValue，否则返回value
     * 
     * @param value
     * @param dftVal
     * @return
     */
    public static <T> T flat(final T value, @NotNull final T dftVal) {
        return null == value ? dftVal : value;
    }

    /**
     * 如果value为null则返回Empty，否则直接返回当前value
     * 
     * @param value
     * @return
     */
    public static String flat(@NotNull @NotEmpty @NotBlank final String value) {
        return null == value ? Constants.EMPTY_STR : value;
    }

    /**
     * 设置
     * 
     * @param value
     * @param dftVal
     * @return
     */
    public static int flat(@NotNull final int value, @NotNull final int dftVal) {
        return Constants.RANGE == value ? dftVal : value;
    }

    /**
     * 设置
     * 
     * @param value
     * @param dftVal
     * @return
     */
    public static long flat(@NotNull final long value, @NotNull final long dftVal) {
        return Constants.RANGE == value ? dftVal : value;
    }

    /**
     * 如果value为null则返回dftVal，否则返回fun的转换函数的执行结果
     * 
     * @param value
     * @param dftVal
     * @param fun
     * @return
     */
    public static String flat(final String value, @NotNull @NotEmpty @NotBlank final String dftVal,
            @NotNull final FlatTo fun) {
        return null == value ? dftVal : fun.to(value);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Planar() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
