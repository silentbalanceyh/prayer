package com.prayer.util.fun;
/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface EnumSet<T extends Enum<T>> {
    /**
     * 
     * @param data
     */
    void set(T data);
}
