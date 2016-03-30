package com.prayer.record.fun;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Evaluator {
    /**
     * 
     * @param message
     * @param condition
     */
    void evalTrue(String message, boolean condition);
}
