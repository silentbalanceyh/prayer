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
    
    /** 配置常量 **/
    @VertexPoint(Interface.CONSTANT)
    interface Shared {
        /** 用户名字段名 **/
        String USERNAME = "username";
        /** 用户Mobile **/
        String MOBILE = "mobile";
        /** 用户Email **/
        String EMAIL = "email";
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
    interface Basic {
        /** 用户常量 **/
        @VertexPoint(Interface.CONSTANT)
        interface User {

            String ID = "id";
        }
    }
}
