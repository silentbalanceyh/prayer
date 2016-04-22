package com.prayer.facade.engine.cv;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/** **/
@VertexPoint(Interface.CONSTANT)
public interface RmiKeys {
    /** 元数据运行参数RMI **/
    String META_SERVER_OPTS = "RUNNING/OPTS";
}
