package com.prayer.facade.fun.schema;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Condition<L, R> {
    /**
     * 
     * @param left
     * @param right
     * @return
     */
    boolean condition(L left, R right);
}
