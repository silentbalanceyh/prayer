package com.prayer.resource;

import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;

/**
 * 替换原来的Accessors
 * 
 * @author Lang
 *
 */
public class Injections {
    // ~ Static Fields =======================================
    /** Inceptor **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Injection.class);
    /** Cache **/
    public static final Class<?> CACHE = INCEPTOR.getClass(Point.Injection.CATCH);
    /** Data连接 **/
    public static final Class<?> POOL = INCEPTOR.getClass(Point.Injection.Data.POOL);

    /** Web **/
    public static final class Web {
        /** URI Hooker **/
        public static final Class<?> URI_HOOKER = INCEPTOR.getClass(Point.Injection.Web.URI_HOOKER);
    }

    /** Script Engine **/
    public static final class Service {
        /** Script **/
        public static final Class<?> SCRIPT_ENGINE = INCEPTOR.getClass(Point.Injection.Service.SCRIPT_ENGINE);
    }

    /** 元数据库访问 **/
    public static final class Meta {
        /** Meta Data 连接 **/
        public static final Class<?> CONNECTION = INCEPTOR.getClass(Point.Injection.Meta.CONNECTION);
        /** Accessor连接 **/
        public static final Class<?> ACCESSOR = INCEPTOR.getClass(Point.Injection.Meta.ACCESSOR);
        /** Launcher类 **/
        public static final Class<?> LAUNCHER = INCEPTOR.getClass(Point.Injection.Meta.LAUNCHER);
    }

    /** 数据库访问 **/
    public static final class Data {
        /** Validator **/
        public static final Class<?> VALIDATOR = INCEPTOR.getClass(Point.Injection.Data.VALIDATOR);
        /** Builder **/
        public static final Class<?> BUILDER = INCEPTOR.getClass(Point.Injection.Data.BUILDER);
        /** Meta Dalor **/
        public static final Class<?> META_DALOR = INCEPTOR.getClass(Point.Injection.Data.META_DALOR);
        /** Data Dalor **/
        public static final Class<?> DATA_DALOR = INCEPTOR.getClass(Point.Injection.Data.DATA_DALOR);
        /** Tranducer **/
        public static final Class<?> TRANSDUCER = INCEPTOR.getClass(Point.Injection.Data.TRANSDUCER);
    }
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
