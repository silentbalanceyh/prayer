package com.prayer.vertx.uca.consumer;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.vtx.Channel;

import io.vertx.core.http.HttpMethod;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class Director {
    // ~ Static Fields =======================================
    /** **/
    private static final ConcurrentMap<HttpMethod, Channel> DATA_CNL = new ConcurrentHashMap<>();
    /** **/
    private static final ConcurrentMap<HttpMethod, Channel> META_CNL = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    static {
        /** 数据访问 **/
        DATA_CNL.put(HttpMethod.GET, singleton(com.prayer.vertx.channel.data.GetChannel.class));
        DATA_CNL.put(HttpMethod.POST, singleton(com.prayer.vertx.channel.data.PostChannel.class));
        DATA_CNL.put(HttpMethod.PUT, singleton(com.prayer.vertx.channel.data.PutChannel.class));
        DATA_CNL.put(HttpMethod.DELETE, singleton(com.prayer.vertx.channel.data.DeleteChannel.class));
        /** 元数据访问 **/
        META_CNL.put(HttpMethod.GET, singleton(com.prayer.vertx.channel.meta.GetChannel.class));
        META_CNL.put(HttpMethod.POST, singleton(com.prayer.vertx.channel.meta.PostChannel.class));
        META_CNL.put(HttpMethod.PUT, singleton(com.prayer.vertx.channel.meta.PutChannel.class));
        META_CNL.put(HttpMethod.DELETE, singleton(com.prayer.vertx.channel.meta.DeleteChannel.class));
    }

    // ~ Static Methods ======================================
    /** Data **/
    public static final class Data {
        /** 接口选择器 **/
        public static Channel select(final HttpMethod method) {
            return DATA_CNL.get(method);
        }
    }

    /** Meta **/
    public static final class Meta {
        /** 接口选择器 **/
        public static Channel select(final HttpMethod method) {
            return META_CNL.get(method);
        }
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
