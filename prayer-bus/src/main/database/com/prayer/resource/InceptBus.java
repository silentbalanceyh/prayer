package com.prayer.resource;

import static com.prayer.util.reflection.Instance.reservoir;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.inceptor.ConsoleInceptor;
import com.prayer.resource.inceptor.DatabaseInceptor;
import com.prayer.resource.inceptor.DeployInceptor;
import com.prayer.resource.inceptor.DynamicInceptor;
import com.prayer.resource.inceptor.ErrorInceptor;
import com.prayer.resource.inceptor.InjectionInceptor;
import com.prayer.resource.inceptor.MetaServerInceptor;
import com.prayer.resource.inceptor.PatternInceptor;
import com.prayer.resource.inceptor.ResolverInceptor;
import com.prayer.resource.inceptor.RmiInceptor;
import com.prayer.resource.inceptor.SchemaInceptor;
import com.prayer.resource.inceptor.SecurityInceptor;
import com.prayer.resource.inceptor.ServerInceptor;
import com.prayer.resource.inceptor.SystemInceptor;
import com.prayer.resource.inceptor.TpInceptor;
import com.prayer.resource.inceptor.UriInceptor;
import com.prayer.resource.inceptor.VertxInceptor;
import com.prayer.resource.inceptor.WebInceptor;
import com.prayer.util.reflection.Instance;
import com.prayer.util.string.StringKit;

/**
 * 用于初始化Inceptor用
 * 
 * @author Lang
 *
 */
public final class InceptBus {
    // ~ Static Fields =======================================
    /** **/
    private static ConcurrentMap<Class<?>, Inceptor> INCEPTORS = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    static {
        INCEPTORS.put(Point.System.class, Instance.instance(SystemInceptor.class));
        INCEPTORS.put(Point.Error.class, Instance.instance(ErrorInceptor.class));
        INCEPTORS.put(Point.Database.class, Instance.instance(DatabaseInceptor.class));
        INCEPTORS.put(Point.Injection.class, Instance.instance(InjectionInceptor.class));
        INCEPTORS.put(Point.Schema.class, Instance.instance(SchemaInceptor.class));
        INCEPTORS.put(Point.Deploy.class, Instance.instance(DeployInceptor.class));
        INCEPTORS.put(Point.TP.class, Instance.instance(TpInceptor.class));
        INCEPTORS.put(Point.MetaServer.class, Instance.instance(MetaServerInceptor.class));
        INCEPTORS.put(Point.RMI.class, Instance.instance(RmiInceptor.class));
        INCEPTORS.put(Point.Console.class, Instance.instance(ConsoleInceptor.class));
        INCEPTORS.put(Point.Server.class, Instance.instance(ServerInceptor.class));
        INCEPTORS.put(Point.Vertx.class, Instance.instance(VertxInceptor.class));
        INCEPTORS.put(Point.Web.class, Instance.instance(WebInceptor.class));
        INCEPTORS.put(Point.Security.class, Instance.instance(SecurityInceptor.class));
        INCEPTORS.put(Point.Uri.class, Instance.instance(UriInceptor.class));
        INCEPTORS.put(Point.Resolver.class, Instance.instance(ResolverInceptor.class));
        INCEPTORS.put(Point.Pattern.class, Instance.instance(PatternInceptor.class));
        /** 特殊Inceptors **/
        INCEPTORS.put(Point.Jdbc.class, Instance.instance(DatabaseInceptor.class));
    }

    // ~ Static Methods ======================================
    /**
     * 
     * @param clazz
     * @return
     */
    public static Inceptor build(final Class<?> clazz) {
        return INCEPTORS.get(clazz);
    }

    /**
     * 
     * @param clazz
     * @param key
     * @return
     */
    public static Inceptor build(final Class<?> clazz, final String key) {
        /** 1.读取Inceptor **/
        final Inceptor inceptor = INCEPTORS.get(clazz);
        /** 2.读取key中对应的配置路径 **/
        final String file = inceptor.getString(key);
        /** 3.参数操作 **/
        String param = key;
        if (StringKit.isNonNil(file)) {
            param = file;
        }
        return reservoir(param, DynamicInceptor.class, param);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private InceptBus() {
    };
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
