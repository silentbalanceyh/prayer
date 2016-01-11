package com.prayer.facade.entity;
/**
 * Java Bean专用方法
 * @author Lang
 *
 */
@FunctionalInterface
public interface BeanGet<T> {
    /**
     * 方法签名
     * @return
     */
    T get();
}
