package com.prayer.facade.fun.entity;
/**
 * Java Bean专用方法void set(String)
 * @author Lang
 *
 */
@FunctionalInterface
public interface BeanSet<T> {
    /**
     * 方法的签名
     * @param data
     */
    void set(T data);
}
