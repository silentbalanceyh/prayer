package com.prayer.util;

import java.util.Collection;
import java.util.HashSet;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Calculator {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 交集运算，不改变传参的两个集合
     * <code>约束：返回值不可为null；</code>
     * @param left
     * @param right
     * @return
     */
    @NotNull
    public static <T extends Object> Collection<T> intersect(@NotNull final Collection<T> left,
            @NotNull final Collection<T> right) {
        final Collection<T> ret = new HashSet<>();
        ret.addAll(left);
        ret.retainAll(right);
        return ret;
    }

    /**
     * 并集运算，不改变传参的两个集合
     * <code>约束：返回值不可为null；</code>
     * @param left
     * @param right
     * @return
     */
    @NotNull
    public static <T extends Object> Collection<T> union(@NotNull final Collection<T> left,
            @NotNull final Collection<T> right) {
        final Collection<T> ret = new HashSet<>();
        ret.addAll(left);
        ret.addAll(right);
        return ret;
    }

    /**
     * 集合减法，交集运算，不改变传参的两个集合，但有顺序
     * <code>约束：返回值不可为null；</code>
     * @param subtrahend
     * @param minuend
     * @return
     */
    @NotNull
    public static <T extends Object> Collection<T> diff(@NotNull final Collection<T> subtrahend,
            @NotNull final Collection<T> minuend) {
        final Collection<T> ret = new HashSet<>();
        ret.addAll(subtrahend);
        ret.removeAll(minuend);
        return ret;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Calculator() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
