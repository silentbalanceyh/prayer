package com.prayer.facade.model.entity;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;

/**
 * Service请求验证器
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Ensurer<T, R> {
    /**
     * 1.针对必须参数的验证
     * 
     * @param value
     * @throws AbstractException
     */
    @VertexApi(Api.TOOL)
    R ensureRequired(T value, String attr) throws AbstractException;
    /**
     * 1.针对必须参数的验证
     * 
     * @param value
     * @throws AbstractException
     */
    @VertexApi(Api.TOOL)
    R ensureOptional(T value, String attr) throws AbstractException;
}
