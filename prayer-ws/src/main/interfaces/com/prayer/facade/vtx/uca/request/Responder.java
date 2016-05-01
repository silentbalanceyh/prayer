package com.prayer.facade.vtx.uca.request;

import com.prayer.facade.vtx.uca.headers.Ransmitter;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 响应处理器，可配置到Uri中，用于生成UI对应数据，从响应Header接口继承，同时处理接口数据
 * @author Lang
 *
 */
public interface Responder<T> extends Ransmitter<T>{
    /**
     * 
     * @param data
     * @return
     * @throws AbstractException
     */
    String buildBody(T data);
}
