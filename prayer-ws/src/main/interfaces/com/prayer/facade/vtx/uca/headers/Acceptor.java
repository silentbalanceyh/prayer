package com.prayer.facade.vtx.uca.headers;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpServerRequest;

/**
 * 头部信息的接收动作
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Acceptor {
    /**
     * 针对特殊的Http Header执行接收/拒绝操作
     * @param request
     * @param expected
     * @return
     * @throws AbstractWebException
     */
    @VertexApi(Api.TOOL)
    Envelop accept(HttpServerRequest request, String... expectes);
}
