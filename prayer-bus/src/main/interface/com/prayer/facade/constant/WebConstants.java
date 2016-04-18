package com.prayer.facade.constant;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface WebConstants {
    /** 元数据配置项 **/
    @VertexPoint(Interface.CONSTANT)
    interface HUB {
        /** 端口号 **/
        String PORT = "port";
        /** Host地址 **/
        String HOST = "host";
        /** 用户名 **/
        String USERNAME = "username";
        /** 密码 **/
        String PASSWORD = "password";
        /** 模式 **/
        String MODE = "mode";
    }
}
