package com.prayer.constant;

/**
 * 系统使用的基础枚举类
 *
 * @author Lang
 * @see
 */
public final class SystemEnum { // NOPMD
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

    // ~ Constructors ========================================
    private SystemEnum() {
    }
}
