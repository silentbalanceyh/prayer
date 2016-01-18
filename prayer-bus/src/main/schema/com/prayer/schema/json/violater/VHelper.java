package com.prayer.schema.json.violater;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.facade.fun.schema.Condition;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.guard.Guarded;

/**
 * Violater的计算规则帮助方法，统一计算各种Violater的规则信息 a
 * 
 * @author Lang
 *
 */
@Guarded
final class VHelper {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 单个元素和对应的expected中的运算，单个元素Object -> JsonArray
     * 
     * @param valueArr
     * @param expected
     * @param fun
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T calculate(@NotNull final JsonArray valueArr, @NotNull final JsonArray expected,
            @NotNull final Condition<T, JsonArray> fun) {
        T ret = null;
        for (final Object value : expected) {
            if (null != value) {
                final T checked = (T) value;
                if (fun.condition(checked, valueArr)) {
                    ret = checked;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 同一个Map中的两个不同的key之间的运算 Object <-> Object
     * 
     * @param valueMap
     * @param keys
     * @param fun
     * @return 返回第二个key
     */
    public static <V> String[] calculate(@NotNull final ConcurrentMap<String, V> valueMap,
            @NotNull @Size(min = 2, max = 2) final String[] keys, @NotNull final Condition<V, V> fun) {
        String[] retKey = null;
        if (Constants.TWO == keys.length && VCondition.neq(keys[Constants.IDX], keys[Constants.ONE])) {
            final V left = valueMap.get(keys[Constants.IDX]);
            final V right = valueMap.get(keys[Constants.ONE]);
            if (fun.condition(left, right)) {
                retKey = keys;
            } else {
                retKey = null;
            }
        }
        return retKey;
    }

    /**
     * 两个Map之间的计算，单个元素使用 Object <-> Object
     * 
     * @param valueMap
     * @param expectedMap
     * @param fun
     * @return
     */
    // 1.遍历expectedMap，提取key
    // 2.如果expectedMap中的key在valueMap中存在，则使用fun检查两个map中同一个key对应的值
    // 3.如果fun返回true则表示有错误返回key，否则返回null
    public static <V, E> String calculate(@NotNull final ConcurrentMap<String, V> valueMap,
            @NotNull final ConcurrentMap<String, E> expectedMap, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final String key : expectedMap.keySet()) {
            if (valueMap.containsKey(key)) {
                /** 检查reKey是否为null，不为null就跳出循环，已经捕捉到Exception **/
                if (null != (retKey = dispatcher(key, valueMap.get(key), expectedMap.get(key), fun))) {
                    break;
                }
            }
        }
        return retKey;
    }

    /**
     * 内置分流函数
     * 
     * @param key
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <V, E, EV, NE> String dispatcher(@NotNull @NotBlank @NotEmpty final String key, final V value,
            final E expected, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        /** 两个值不为null时检查 **/
        if (null != value && null != expected) {
            if (value instanceof List && expected instanceof List) {
                // V -> List<V>, E -> List<E>
                final Condition<EV, NE> function = (Condition<EV, NE>) fun;
                final List<EV> values = transfer(value);
                final List<NE> expectes = transfer(expected);
                retKey = calculate(key, values, expectes, function);
            } else if (!(value instanceof List) && expected instanceof List) {
                // E -> List<E>
                final Condition<V, NE> function = (Condition<V, NE>) fun;
                final List<NE> expectes = transfer(expected);
                retKey = calculate(key, value, expectes, function);
            } else if (value instanceof List && !(expected instanceof List)) {
                // V -> List<V>
                final Condition<EV, E> function = (Condition<EV, E>) fun;
                final List<EV> values = transfer(value);
                retKey = calculate(key, values, expected, function);
            } else {
                // No Transfer
                retKey = calculate(key, value, expected, fun);
            }
        }
        return retKey;
    }

    /**
     * 内置运算，T -> List<I>的过程
     * 
     * @param instance
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <T, I> List<I> transfer(final T instance) {
        final List<I> ret = new ArrayList<>();
        if (instance instanceof List) {
            for (final Object item : ((List) instance)) {
                if (null != item) {
                    final I target = (I) item;
                    ret.add(target);
                }
            }
        }
        return ret;
    }

    /**
     * 内置运算：List<V> -> List<E>
     * 
     * @param key
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    private static <V, E> String calculate(@NotNull @NotBlank @NotEmpty final String key, @NotNull final List<V> value,
            @NotNull final List<E> expected, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        outer: for (final V vItem : value) {
            if (null != vItem) {
                for (final E eItem : expected) {
                    if (null != eItem && fun.condition(vItem, eItem)) {
                        retKey = key;
                        break outer;
                    }
                }
            }
        }
        return retKey;
    }

    /**
     * 内置运算：List<V> -> E
     * 
     * @param key
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    private static <V, E> String calculate(@NotNull @NotBlank @NotEmpty final String key, @NotNull final List<V> value,
            @NotNull final E expected, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final V item : value) {
            if (null != item) {
                retKey = key;
                break;
            }
        }
        return retKey;
    }

    /**
     * 内置运算：V -> List<E>
     * 
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    private static <V, E> String calculate(@NotNull @NotBlank @NotEmpty final String key, @NotNull final V value,
            @NotNull final List<E> expected, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final E item : expected) {
            if (null != item && fun.condition(value, item)) {
                retKey = key;
                break;
            }
        }
        return retKey;
    }

    /**
     * 内置运算：V -> E
     * 
     * @param key
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    private static <V, E> String calculate(@NotNull @NotBlank @NotEmpty final String key, @NotNull final V value,
            @NotNull final E expected, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        if (fun.condition(value, expected)) {
            retKey = key;
        }
        return retKey;
    }

    /**
     * 
     * @param valueMap
     * @param expectedMap
     * @param fun
     * @return
     */
    public static <V, E> String calculateCollection(@NotNull final ConcurrentMap<String, List<V>> valueMap,
            @NotNull final ConcurrentMap<String, List<E>> expectedMap, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final String key : expectedMap.keySet()) {
            if (valueMap.containsKey(key)) {

            }
        }
        return retKey;
    }

    // ~ Check Function ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private VHelper() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
