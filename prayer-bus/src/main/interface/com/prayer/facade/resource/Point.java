package com.prayer.facade.resource;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 常量接口，保存了所有需要使用的配置Key
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface Point {
    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Injection {
        /** 使用的Cache **/
        String CATCH = "cache.manager";

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Data {
            /** 数据库连接池 **/
            String POOL = "database.data.pool";
            /** 数据库中验证器 **/
            String VALIDATOR = "database.data.validator";
            /** Meta 数据库构造器 **/
            String BUILDER = "database.meta.builder";
            /** Meta：数据库中的元数据访问 **/
            String META_DALOR = "database.meta.dalor";
            /** Data **/
            String DATA_DALOR = "database.data.dalor";
            /** Transducer **/
            String TRANSDUCER = "database.data.transducer";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Meta {
            /** 元数据使用JDBC时的连接类 **/
            String CONNECTION = "meta.connection";
            /** Meta：数据库中的Accessor **/
            String ACCESSOR = "database.meta.accessor";
        }
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface System {
        /** 编码方式 **/
        String ENCODING = "system.encoding";
        /** 日志目录 **/
        String LOGS_DEBUG = "system.logs.debug";
        /** 错误日志目录 **/
        String LOGS_ERROR = "system.logs.error";
        /** 消息日志目录 **/
        String LOGS_INFO = "system.logs.info";
        /** 验证Folder **/
        String VALIDATION_RULE = "system.validation.rules";
    }
    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Deploy{
    	/** 默认发布的数据目录 **/
    	String DATA_FOLDER = "data.folder";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Error {
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Schema {
    }

    @VertexPoint(Interface.CONSTANT)
    interface Jdbc {
        // Fix引用必须，Database和Jdbc是相互独立的，并不在同一个文件，不可使用Database作为Key值
    }
    /** 第三方 **/
    @VertexPoint(Interface.CONSTANT)
    interface TP{
    	/** MyBatis **/
        @VertexPoint(Interface.CONSTANT)
    	interface MyBatis{
    		/** 配置文件路径 **/
        	String CFG_FILE = "mybatis.config.file";
        	/** 环境名 **/
        	String ENV = "mybatis.environment";
    	}
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Database {
        /** 开启底层AOP验证 **/
        String VALIDATION = "aop.validation.enabled";
        /** Dao的mapping文件 **/
        String DALOR = "dalor.mapping";

        /** Jdbc特有配置 **/
        @VertexPoint(Interface.CONSTANT)
        interface Jdbc {
            /** JDBC配置文件 **/
            String JDBC = "jdbc.config.file";
            /** Mapping文件位置 **/
            String MAPPING = "jdbc.type.mapping";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Data {
            /** 数据库模式 **/
            String MODE = "database.mode";
            /** 数据库种类 **/
            String CATEGORY = "database.category";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Meta {
            /** 元数据库模式 **/
            String MODE = "metadata.mode";
            /** 元数据库种类 **/
            String CATEGORY = "metadata.category";
        }

    }
}
