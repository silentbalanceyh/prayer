package com.prayer.util.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.vertx.UriModel;
import com.prayer.util.reflection.Instance;
import com.prayer.util.string.StringKit;

import io.vertx.core.http.HttpMethod;
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
public final class ResultExtractor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** Object extracting **/
    @NotNull
    public static <T> ConcurrentMap<String, T> extractEntity(@NotNull final List<T> dataList,
            @NotNull @NotBlank @NotEmpty final String field) {
        // 1.构造结果
        final ConcurrentMap<String, T> retMap = new ConcurrentHashMap<>();
        // 2.遍历结果集
        for (final T item : dataList) {
            if (StringKit.isNonNil(Instance.field(item, field).toString())) {
                // 3.将DataList转换成一个Map
                retMap.put(Instance.field(item, field).toString(), item);
            }
        }
        return retMap;
    }

    /** List<Object> extracting **/
    @NotNull
    public static <T> ConcurrentMap<String, List<T>> extractList(@NotNull final List<T> dataList,
            @NotNull @NotBlank @NotEmpty final String field) {
        // 1.构造结果
        final ConcurrentMap<String, List<T>> retMap = new ConcurrentHashMap<>();
        // 2.遍历结果集
        for (final T item : dataList) {
            if (StringKit.isNonNil(Instance.field(item, field).toString())) {
                // 3.1.获取Key
                final String key = Instance.field(item, field).toString();
                // 3.2.从Map中读取
                List<T> list = retMap.get(key);
                // 3.3.判断获取结果
                if (null == list) {
                    list = new ArrayList<>(); // NOPMD
                }
                // 3.4.添加对象到List中
                list.add(item);
                // 3.5.填充Map
                retMap.put(key, list);
            }
        }
        return retMap;
    }

    /** **/
    @NotNull
    public static ConcurrentMap<HttpMethod, UriModel> extractUris(@NotNull final List<UriModel> dataList) {
        // 1.构造结果
        final ConcurrentMap<HttpMethod, UriModel> retMap = new ConcurrentHashMap<>();
        // 2.遍历结果集
        for (final UriModel item : dataList) {
            // 3.5.填充Map
            retMap.put(item.getMethod(), item);
        }
        return retMap;
    }

    /*    *//**
             * 
             * @param dataList
             * @return
             *//*
               * @NotNull public static ConcurrentMap<String,
               * List<VerticleModel>> extractVerticles(@NotNull final
               * List<VerticleModel> dataList) { // 1.构造结果 final
               * ConcurrentMap<String, List<VerticleModel>> retMap = new
               * ConcurrentHashMap<>(); // 2.遍历结果集 for (final VerticleModel item
               * : dataList) { if (StringKit.isNonNil(item.getGroup())) { //
               * 3.1.获取某个Group的VerticleChain VerticleChain chain =
               * retMap.get(item.getGroup()); // 3.2.判断是否获取到，没获取到就重新获取 if (null
               * == chain) { chain = new VerticleChain(item.getGroup()); //
               * NOPMD } // 3.3.修改chain引用 chain.addVerticle(item); //
               * 3.4.完成过后添加到Map中 retMap.put(item.getGroup(), chain); } } //
               * 4.返回最终过滤结果 return retMap; }
               */

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private ResultExtractor() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
