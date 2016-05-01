package com.prayer.facade.vtx.uca.headers;

import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.http.HttpServerResponse;

/**
 * 头部响应器
 * @author Lang
 *
 */
public interface Ransmitter<T> {
    /**
     * 根据响应数据的头信息，构造响应头
     * @param data
     * @return
     * @throws AbstractException
     */
    void reckonHeaders(HttpServerResponse response, T data);
}
