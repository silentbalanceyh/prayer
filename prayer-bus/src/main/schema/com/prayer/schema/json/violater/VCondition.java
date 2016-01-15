package com.prayer.schema.json.violater;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.prayer.facade.fun.schema.Occurs;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class VCondition {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================

    // ~ Static Methods ======================================
    /**
     * 不相等算法
     * 
     * @param left
     * @param right
     * @return
     */
    public static boolean neq(@NotNull final Object left, @NotNull final Object right) {
        return !eq(left, right);
    }

    /**
     * 相等算法
     * 
     * @param left
     * @param right
     * @return
     */
    public static boolean eq(@NotNull final Object left, @NotNull final Object right) {
        return left.equals(right);
    }

    /**
     * 不匹配算法，只要有一个不匹配则表示不匹配
     * 
     * @param value
     * @param patterns
     * @return
     */
    public static boolean unmatch(@NotNull final Object value, @NotNull final JsonArray patterns) {
        return !match(value, patterns);
    }

    /**
     * 不匹配算法
     * 
     * @param value
     * @param pattern
     * @return
     */
    public static boolean unmatch(@NotNull final Object value, @NotNull final Pattern pattern) {
        return !match(value, pattern);
    }
    /**
     * 互斥运算
     * @param config
     * @param values
     * @return
     */
    public static boolean mostRepel(@NotNull final JsonObject config, @NotNull final JsonArray values) {
        final Occurs fun = (int occurs, int actualOccurs) -> {
            return actualOccurs < occurs;
        };
        return mediater(config, values, fun);
    }
    /**
     * 根据Filter获取次数限制，查找满足filter的values，最多多少次，实际次数不能大于occurs
     * 
     * @param filter
     * @param patterns
     * @return
     */
    public static boolean most(@NotNull final JsonObject config, @NotNull final JsonArray values) {
        final Occurs fun = (int occurs, int actualOccurs) -> {
            return actualOccurs <= occurs;
        };
        return mediater(config, values, fun);
    }
    /**
     * 根据Filter获取次数限制，查找满足filter的values，最少多少次，实际次数不能小于occurs
     * 
     * @param filter
     * @param patterns
     * @return
     */
    public static boolean leastRepel(@NotNull final JsonObject config, @NotNull final JsonArray values) {
        final Occurs fun = (int occurs, int actualOccurs) -> {
            return actualOccurs > occurs;
        };
        return mediater(config, values, fun);
    }

    /**
     * 根据Filter获取次数限制，查找满足filter的values，最少多少次，实际次数不能小于occurs
     * 
     * @param filter
     * @param patterns
     * @return
     */
    public static boolean least(@NotNull final JsonObject config, @NotNull final JsonArray values) {
        final Occurs fun = (int occurs, int actualOccurs) -> {
            return actualOccurs >= occurs;
        };
        return mediater(config, values, fun);
    }

    /**
     * 根据Filter获取次数限制，查找满足filter的values，最少多少次，实际次数必须是期望次数
     * 
     * @param filter
     * @param patterns
     * @return
     */
    public static boolean counter(@NotNull final JsonObject config, @NotNull final JsonArray values) {
        final Occurs fun = (int occurs, int actualOccurs) -> {
            return actualOccurs == occurs;
        };
        return mediater(config, values, fun);
    }

    /**
     * 不重复的判断
     * 
     * @param field
     * @param values
     * @return
     */
    public static boolean distict(@NotNull final String field, @NotNull final JsonArray values) {
        return !duplicated(field, values);
    }

    /**
     * 查找dataArr中是否有field重复的，默认一次，2次即重复
     * 
     * @param field
     * @param dataArr
     * @return
     */
    public static boolean duplicated(final String field, final JsonArray dataArr) {
        final int size = dataArr.size();
        boolean ret = false;
        final Set<Object> duplicated = new HashSet<>();
        for (int pos = 0; pos < size; pos++) {
            final Object value = dataArr.getValue(pos);
            if (null != value && JsonObject.class == value.getClass()) {
                final JsonObject item = dataArr.getJsonObject(pos);
                if (item.containsKey(field)) {
                    final Object dupValue = item.getValue(field);
                    if (duplicated.contains(dupValue)) {
                        ret = true;
                        break;
                    } else {
                        duplicated.add(dupValue);
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 匹配算法，必须全部匹配
     * 
     * @param value
     * @param patterns
     * @return
     */
    public static boolean match(@NotNull final Object value, @NotNull final JsonArray patterns) {
        boolean ret = true;
        for (final Object item : patterns) {
            if (null != item && String.class == item.getClass()) {
                final Pattern pattern = Pattern.compile(item.toString());
                if (unmatch(value, pattern)) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 匹配算法
     * 
     * @param value
     * @param pattern
     * @return
     */
    public static boolean match(@NotNull final Object value, @NotNull final Pattern pattern) {
        return pattern.matcher(value.toString()).matches();
    }

    /**
     * 不包含算法
     * 
     * @param value
     * @param expected
     * @return
     */
    public static boolean nin(final Object value, final JsonArray expected) {
        return !in(value, expected);
    }

    /**
     * 单元素包含算法
     * 
     * @param value
     * @param expected
     * @return
     */
    public static boolean in(final Object value, final JsonArray expected) {
        return expected.contains(value);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    // ~ Private Methods =====================================
    /**
     * 内置函数，用于提供不同的函数实现，二义性函数
     * 
     * @param config
     * @param values
     * @param fun
     * @return
     */
    private static boolean mediater(final JsonObject config, final JsonArray values, final Occurs fun) {
        final int occurs = config.getInteger("occurs");
        // 用于Least，Most，Counter的判断
        final JsonObject filters = config.getJsonObject("filter");
        final int actualOccurs = occurs(filters, values);
        return fun.occurs(occurs, actualOccurs);
    }

    private static int occurs(final JsonObject filter, final JsonArray dataArr) {
        final int size = dataArr.size();
        int occurs = 0;
        for (int pos = 0; pos < size; pos++) {
            final Object value = dataArr.getValue(pos);
            if (null != value && JsonObject.class == value.getClass()) {
                final JsonObject item = dataArr.getJsonObject(pos);
                /** 遍历Filter看当前的item是否满足条件 **/
                boolean isMatch = true;
                for (final String field : filter.fieldNames()) {
                    if (item.containsKey(field)) {
                        final Object fValue = filter.getValue(field);
                        final Object dValue = item.getValue(field);
                        if (null != fValue && null != dValue) {
                            if (!eq(fValue, dValue)) {
                                isMatch = false;
                                break;
                            }
                        }
                    } else {
                        isMatch = false;
                        continue;
                    }
                }
                /** 当前数据并没有因为不match跳出 **/
                if (isMatch) {
                    occurs++;
                }
            }
        }
        return occurs;
    }

    private VCondition() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
