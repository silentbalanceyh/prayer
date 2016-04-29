package com.prayer.resource;

import static com.prayer.util.Converter.fromStr;

import com.prayer.constant.SystemEnum.SecureMode;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;

/**
 * 数据库访问中特殊的高频使用常量
 **/
public class Resources {
    // ~ Static Fields =======================================
    /** 数据访问资源 **/
    public static final class Data {
        /** 数据库模式 **/
        public static final String MODE;
        /** 数据库种类 **/
        public static final String CATEGORY;
        /** **/
        static {
            CATEGORY = InceptBus.build(Point.Database.class).getString(Point.Database.Data.CATEGORY);
            MODE = InceptBus.build(Point.Database.class).getString(Point.Database.Data.MODE);
        }
    }

    /** 元数据数据访问资源 **/
    public static final class Meta {
        /** 元数据库模式 **/
        public static final String MODE;
        /** 元数据库种类 **/
        public static final String CATEGORY;
        /** **/
        static {
            CATEGORY = InceptBus.build(Point.Database.class).getString(Point.Database.Meta.CATEGORY);
            MODE = InceptBus.build(Point.Database.class).getString(Point.Database.Meta.MODE);
        }
    }

    /** **/
    public static final Inceptor JDBC = InceptBus.build(Point.Database.class, Point.Database.Jdbc.JDBC);
    /** 数据库名 **/
    public static final String DATABASE = JDBC.getString(Data.CATEGORY + ".jdbc.database.name");

    /** 安全Key **/
    public static final class Security {
        /** 读取安全模式 **/
        public static final SecureMode MODE = fromStr(SecureMode.class,
                InceptBus.build(Point.Security.class).getString(Point.Security.Shared.MODE));
        /** 读取Realm信息 **/
        public static final String REALM = InceptBus.build(Point.Security.class).getString(Point.Security.Shared.REALM);
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
