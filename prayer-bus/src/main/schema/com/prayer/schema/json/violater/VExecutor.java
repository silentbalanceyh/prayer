package com.prayer.schema.json.violater;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.constant.Constants;
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
final class VExecutor {
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
    public static <T> T execute(@NotNull final JsonArray valueArr, @NotNull final JsonArray expected,
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
    public static <V> String[] execute(@NotNull final ConcurrentMap<String, V> valueMap,
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
     * Many -> Many
     * 
     * @param key
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    public static <V, E> String execute(@NotNull @NotBlank @NotEmpty final String key, @NotNull final List<V> value,
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
     * Many -> One
     * 
     * @param key
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    public static <V, E> String execute(@NotNull @NotBlank @NotEmpty final String key, @NotNull final List<V> value,
            @NotNull final E expected, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final V vItem : value) {
            if (null != vItem && fun.condition(vItem, expected)) {
                retKey = key;
                break;
            }
        }
        return retKey;
    }

    /**
     * One -> One
     * 
     * @param key
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    public static <V, E> String execute(@NotNull @NotBlank @NotEmpty final String key, @NotNull final V value,
            @NotNull final E expected, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        if (fun.condition(value, expected)) {
            retKey = key;
        }
        return retKey;
    }

    /**
     * One -> Many
     * 
     * @param key
     * @param value
     * @param expected
     * @param fun
     * @return
     */
    public static <V, E> String execute(@NotNull @NotBlank @NotEmpty final String key, @NotNull final V value,
            @NotNull final List<E> expected, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final E eItem : expected) {
            if (null != eItem && fun.condition(value, eItem)) {
                retKey = key;
                break;
            }
        }
        return retKey;
    }

    // ~ 四个规范方法 ======================================
    /**
     * 两个Map之间的计算，单个元素使用 Object <-> Object ( One To One )
     * 
     * @param valueMap
     * @param expectedMap
     * @param fun
     * @return
     */
    // 1.遍历expectedMap，提取key
    // 2.如果expectedMap中的key在valueMap中存在，则使用fun检查两个map中同一个key对应的值
    // 3.如果fun返回true则表示有错误返回key，否则返回null
    public static <V, E> String map(@NotNull final ConcurrentMap<String, V> valueMap,
            @NotNull final ConcurrentMap<String, E> expectedMap, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final String key : expectedMap.keySet()) {
            if (valueMap.containsKey(key)) {
                /** 检查reKey是否为null，不为null就跳出循环，已经捕捉到Exception **/
                if (null != (retKey = execute(key, valueMap.get(key), expectedMap.get(key), fun))) {
                    break;
                }
            }
        }
        return retKey;
    }

    /**
     * Many -> Many
     * 
     * @param valueMap
     * @param expectedMap
     * @param fun
     * @return
     */
    public static <V, E> String matrix(@NotNull final ConcurrentMap<String, List<V>> valueMap,
            @NotNull final ConcurrentMap<String, List<E>> expectedMap, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final String key : expectedMap.keySet()) {
            if (valueMap.containsKey(key)) {
                if (null != (retKey = execute(key, valueMap.get(key), expectedMap.get(key), fun))) {
                    break;
                }
            }
        }
        return retKey;
    }

    /**
     * One -> Many
     * 
     * @param valueMap
     * @param expectedMap
     * @param fun
     * @return
     */
    public static <V, E> String radiate(@NotNull final ConcurrentMap<String, V> valueMap,
            @NotNull final ConcurrentMap<String, List<E>> expectedMap, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final String key : expectedMap.keySet()) {
            if (valueMap.containsKey(key)) {
                if (null != (retKey = execute(key, valueMap.get(key), expectedMap.get(key), fun))) {
                    break;
                }
            }
        }
        return retKey;
    }

    /**
     * Many -> One
     * 
     * @param valueMap
     * @param expectedMap
     * @param fun
     * @return
     */
    public static <V, E> String focalize(@NotNull final ConcurrentMap<String, List<V>> valueMap,
            @NotNull final ConcurrentMap<String, E> expectedMap, @NotNull final Condition<V, E> fun) {
        String retKey = null;
        for (final String key : expectedMap.keySet()) {
            if (valueMap.containsKey(key)) {
                if (null != (retKey = execute(key, valueMap.get(key), expectedMap.get(key), fun))) {
                    break;
                }
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

    private VExecutor() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
