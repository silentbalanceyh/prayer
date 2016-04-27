package com.prayer.facade.vtx.headers;

import java.util.List;

import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpServerRequest;

/**
 * 头部信息的接收动作
 * @author Lang
 *
 */
public interface Acceptor {
    /**
     * 针对特殊的Http Header执行接收/拒绝操作
     * @param request
     * @param expected
     * @return
     * @throws AbstractWebException
     */
    Envelop accept(HttpServerRequest request, List<String> expectes);
}
