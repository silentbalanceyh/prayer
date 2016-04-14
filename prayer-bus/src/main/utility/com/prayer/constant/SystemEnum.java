package com.prayer.constant;

/**
 * 系统使用的基础枚举类
 *
 * @author Lang
 * @see
 */
public final class SystemEnum { // NOPMD
    /** 图遍历节点状态信息 **/
    public static enum NodeStatus {
        WHITE, GRAY, BLACK
    }

    /** Mapping模式 **/
    public static enum MappingMode {
        COLLECTION, // 验证集合信息
        MAPPING // 验证Mapping信息
    }

    /** Index的操作对象类型 **/
    public static enum TargetType {
        VIEW, // 操作对象是视图
        TABLE, // 操作对象是表
    }

    /** 触发器触发时的类型 **/
    public static enum TriggerOp {
        INSERT, // Insert触发器
        UPDATE, // Update触发器
        DELETE, // Delete触发器
    }

    /** 触发器模式 **/
    public static enum TriggerMode {
        BEFORE, // Before触发器
        AFTER, // After触发器
        INSTEAD_OF, // Instead Of触发器
    }

    /** Meta/View的状态 **/
    public static enum Status {
        SYSTEM, USER, DISABLED
    }

    /** Meta的S_CATEGORY属性 **/
    public static enum Category {
        ENTITY, RELATION
    }

    /** Meta的S_MAPPING属性 **/
    public static enum Mapping {
        DIRECT, COMBINATED, PARTIAL
    }

    /** Meta的S_POLICY属性 **/
    public static enum MetaPolicy {
        GUID, INCREMENT, ASSIGNED, COLLECTION
    }

    /** Key的S_CATEGORY属性 **/
    public static enum KeyCategory {
        PrimaryKey, ForeignKey, UniqueKey
    }

    /** Field的C_DATETIME属性 **/
    public static enum DateMode {
        STRING, TIMER
    }

    /** 从Business的相应标记 **/
    public static enum ResponseCode {
        SUCCESS, // 成功返回
        FAILURE, // 非系统运行异常失败返回
        ERROR // 系统运行异常失败返回
    }

    /** 标记状态列 **/
    public static enum StatusFlag {
        UPDATE, // 需要更新的列
        ADD, // 需要添加的列
        DELETE // 需要删除的列
    }

    /** Http请求参数类型 **/
    public static enum ParamType {
        QUERY, // /uri/:a/:b/, /uri?a=x&b=y
        FORM, // 表单上获取
        BODY, // 直接从Body中获取
        CUSTOM // 自定义
    }

    /** Rule的类型，目前仅包含验证器和转换器 **/
    public static enum ComponentType {
        VALIDATOR, // Ruler
        CONVERTOR, // Convertor
        DEPENDANT, // Dependant
    }

    /** Dependant 类型的基础Rule，存在于Json Config中的特殊类型 **/
    public static enum DependRule {
        VALIDATE, // Depend的基础Rule使用Validator
        CONVERT, // Depend的基础Rule使用Convertor
    }

    /** Dependant 的二义性结果 **/
    public static enum DependResult {
        REJECT, // Validate失败的时候被Reject请求
        CONTINUE // Validate成功以及Convert过后继续请求
    }

    /** 组件中访问的Database数据库的类型 **/
    public static enum UACSource {
        DATA, // 基本Database的数据库
        META // H2元数据库Database
    }

    /** 认证模式 **/
    public static enum SecurityMode {
        BASIC, // BASIC
        DIGEST, // DIGEST
        OAUTH2 // OAUTH2
    }

    /** 返回类型 **/
    public static enum ReturnType {
        OBJECT, // JsonObject
        ARRAY, // JsonArray
        ERROR, // JsonObject(Error)
    }

    /** 使用的模板引擎的信息 **/
    public static enum TemplateEngine {
        JADE, // Jade模板引擎
        HTML // 纯的HTML结构，Dynamic无用
    }

    /** 使用的Deployer中Acus组件种类 **/
    public static enum Acus {
        INIT, // 初始化执行器，如果是SQL模式则执行SQL文件
        SCHEMA, // Schema执行器：导入Meta，Field，Key，Index
        DATABASE, // 数据库组件执行器：导入View，Trigger，VColumn
        VERTX, // Vertx执行器：导入Verticle, Address, Route
        URI, // URI执行器：导入Uri，Rule
        SCRIPT // JavaScript执行器：导入Script
    }

    // ---------------------Annotation种类----------------------------
    /** 用于标记接口种类，这里不包括JDK 8.0中的FunctionalInterface **/
    public static enum Interface {
        /**
         * 只能使用WebRequest作为参数，WebResponse作为返回值，用于远程客户端调用，
         * 并且只有RESTFUL接口可以启用Script脚本引擎
         **/
        RESTFUL, // Web Service接口
        /** 参数随意，返回值必须是ServiceResult，用于Engine前端工具调用 **/
        SERVICE, // 服务层接口（内部调用）
        /** Java接口，参数和返回值都随意，用于Engine本身调用 **/
        DIRECT, // 直接访问的接口
        /** 之中仅仅包含了常量信息 **/
        CONSTANT, // 常量接口
        // --------------------上边三个接口是上层调用接口--------------
        /** Engine通用外部接口 **/
        ENG_PUBLIC, // 其他接口，非Service层接口，即Prayer内部调用接口
        /** Engine使用的内部接口，一般为包域 **/
        ENG_PRIVATE, // 私有接口，一般定义为包域
        /** 第三方库需要用才存在的接口 **/
        THIRD_PART // 第三方接口
    }

    /** API的种类 **/
    public static enum Api {
        /** 输入为参数信息，返回一般为实体信息，读取、统计、报表、查询都属于，包括Java Bean的GET **/
        READ, // 读取用的API
        /** 输入为实体信息，添加、更新、删除、清空都属于，包括Java Bean的Set **/
        WRITE, // 写入用的API
        /** 主要为工具辅助方法，即使有时候读取了数据库，但它的内容不会引起实体流程变动，验证、过滤、监听都属于功能接口 **/
        TOOL // 功能接口
    }

    /** 容器计算模型 **/
    public static enum RuleContainer {
        ONE_TO_ONE, ONE_TO_MANY, MANY_TO_ONE, MANY_TO_MANY
    }

    // ~ Constructors ========================================
    private SystemEnum() {
    }
}
