package com.prayer.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.sf.oval.constraint.Min;
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
     * 交集运算，不改变传参的两个集合 <code>约束：返回值不可为null；</code>
     * 
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
     * 并集运算，不改变传参的两个集合 <code>约束：返回值不可为null；</code>
     * 
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
     * 集合减法，交集运算，不改变传参的两个集合，但有顺序 <code>约束：返回值不可为null；</code>
     * 
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

    /**
     * 集合的索引运算，注意这里传入的不是Collection<T>，因为Set<T>不支持索引操作
     * 
     * @param list
     * @param value
     * @return
     */
    @Min(-1)
    public static <T extends Object> int index(@NotNull final List<T> list, @NotNull final T value) {
        final int size = list.size();
        int retIdx = -1;
        for (int idx = 0; idx < size; idx++) {
            final T item = list.get(idx);
            if (null != item && item.equals(value)) {
                retIdx = idx;
                break;
            }
        }
        return retIdx;
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
