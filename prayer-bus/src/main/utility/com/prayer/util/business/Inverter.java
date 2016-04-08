package com.prayer.util.business;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.util.reflection.Instance;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 结果转换器，针对Entity的
 * 
 * @author Lang
 *
 */
public class Inverter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param dataList
     * @param field
     * @return
     */
    public static <K, V> ConcurrentMap<K, V> invertOrb(@NotNull final List<V> dataList,
            @NotNull @NotBlank @NotEmpty final String field) {
        /** 1.构造结果 **/
        final ConcurrentMap<K, V> retMap = new ConcurrentHashMap<>();
        /** 2.遍历结果集 **/
        for (final V item : dataList) {
            final K key = Instance.field(item, field);
            if (null != key) {
                retMap.put(key, item);
            }
        }
        return retMap;
    }

    /**
     * 
     * @param dataList
     * @param field
     * @return
     */
    public static <K, V> ConcurrentMap<K, List<V>> invertList(@NotNull final List<V> dataList,
            @NotNull @NotBlank @NotEmpty final String field) {
        /** 1.构造结果 **/
        final ConcurrentMap<K, List<V>> retMap = new ConcurrentHashMap<>();
        /** 2.遍历结果集 **/
        for (final V item : dataList) {
            final K key = Instance.field(item, field);
            if (null != key) {

                List<V> list = retMap.get(key);
                if (null == list) {
                    list = new ArrayList<>();
                }
                list.add(item);
                retMap.put(key, list);
            }
        }
        return retMap;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
