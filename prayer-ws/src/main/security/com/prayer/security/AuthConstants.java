package com.prayer.security;
/**
 * 
 * @author Lang
 *
 */
public interface AuthConstants {    // NOPMD
    /** **/
    interface BASIC{    // NOPMD
        /** 默认Realm属性 **/
        String REALM = "realm";
        /** 默认用户Schema的ID **/
        String SCHEMA_ID = "user.schema.id";
        /** 业务逻辑层脚本名称 **/
        String SCRIPT_NAME = "script.name";
        
        /** 账号字段信息 **/
        String ACCOUNT_ID = "user.account";
        /** 账号Email信息 **/
        String EMAIL = "user.email";
        /** 账号手机信息 **/
        String MOBILE = "user.mobile";
        /** 账号的密码字段信息 **/
        String PWD = "user.password";
        
        /** 登录入口 **/
        String LOGIN_URL = "login.url";
        
        /** 用户表中存储角色Code的信息 **/
        String ROLE_USER_CODE="user.roles";
    }
}
