package com.prayer.facade.entity;

import com.prayer.fantasm.exception.AbstractException;

/**
 * Service请求验证器
 * 
 * @author Lang
 *
 */
public interface Ensurer<T, R> {
    /**
     * 1.针对必须参数的验证
     * 
     * @param value
     * @throws AbstractException
     */
    R ensureRequired(T value, String attr) throws AbstractException;
    /**
     * 1.针对必须参数的验证
     * 
     * @param value
     * @throws AbstractException
     */
    R ensureOptional(T value, String attr) throws AbstractException;
}
