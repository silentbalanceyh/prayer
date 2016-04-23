package com.prayer.facade.engine.cv;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/** **/
@VertexPoint(Interface.CONSTANT)
public interface RmiKeys {
    /** 元数据运行参数RMI **/
    String META_SERVER_OPTS = "RUNNING/OPTS";

    /** 正在运行的Vertx 实例信息 **/
    // {0} Vertx Instance名称
    // {1} Vertx中的实例的数量
    String VERTX_OPTS = "RUNNING/{0}/INSTANCE";
    /** 正在运行的Verticles的信息 **/
    // {0} Vertx Instance名称
    // {1} WORKER/STANDARD
    // {2} Verticle实例的类名
    String VERTCILE_OPTS = "RUNNING/{0}/{1}/{2}/{3}";
    /** 是否停止该实例 **/
    String VERTX_STOP = "SHUTDOWN/{0}/OK";
}
