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
