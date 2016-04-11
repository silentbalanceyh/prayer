package com.prayer.facade.configuration;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.model.meta.vertx.PEScript;

/**
 * 返回值是单独对象的接口
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface ConfigObjecter {
    /**
     * 根据Script名字读取脚本记录
     * @param name
     * @return
     */
    @VertexApi(Api.READ)
    PEScript script(String name) throws AbstractException;
    /**
     * 根据类信息读取该类对应的消息地址
     * @param workClass
     * @return
     */
    @VertexApi(Api.READ)
    PEAddress address(Class<?> workClass) throws AbstractException;
}
