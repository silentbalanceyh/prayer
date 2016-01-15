package com.prayer.schema.json.violater;

import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.facade.fun.schema.Condition;

import io.vertx.core.json.JsonArray;
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
                final V value = valueMap.get(key);
                final E expected = expectedMap.get(key);
                if (fun.condition(value, expected)) {
                    retKey = key;
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

    private VHelper() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
