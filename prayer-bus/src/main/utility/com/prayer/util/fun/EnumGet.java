package com.prayer.util.fun;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface EnumGet<T extends Enum<T>> {
    /**
     * 方法签名
     */
    T get();
}
