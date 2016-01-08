package com.prayer.util.fun;
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
