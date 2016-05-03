package com.prayer.facade.engine.cv;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 安全相关常量
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface SecKeys {

    /** Realm **/
    String REALM = "realm";
    /** 用户Schema Mode 的全局ID **/
    String IDENTIFIER = "identifier";
    /** 访问的URI **/
    String URI = "request-uri";
    /** 访问的Resource ID **/
    String RES_ID = "resource-id";
    /** 访问的具体认证信息，从Token中提取 **/
    String CREDENTIAL = "credential";
    
    /** 配置常量 **/
    @VertexPoint(Interface.CONSTANT)
    interface Shared {
        /** 用户名字段名 **/
        String USERNAME = "username";
        /** 用户Mobile **/
        String MOBILE = "mobile";
        /** 用户Email **/
        String EMAIL = "email";
        /** 使用了哪种登录方式 **/
        String TYPE = "credential";
    }
    /** Tp Options **/
    @VertexPoint(Interface.CONSTANT)
    interface Tp{
        /** QQ **/
        String QQ = "qq";
        /** Alipay **/
        String ALIPAY = "alipay";
        /** TaoBao **/
        String TAOBAO = "taobao";
        /** WeiBo **/
        String WEIBO = "weibo";
        /** WebChat **/
        String WEBCHAT = "webchat";
    }
    /** Basic认证 **/
    @VertexPoint(Interface.CONSTANT)
    interface User {
        /** 用户常量 **/
        @VertexPoint(Interface.CONSTANT)
        interface Basic {
            /** **/
            String ID = "id";
            /** **/
            String TOKEN = "token";
            /** **/
            String PASSWORD = "password";
        }
    }
}
