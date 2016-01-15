package com.prayer.facade.fun.schema;
/**
 * 发生次数的对比
 * @author Lang
 *
 */
@FunctionalInterface
public interface Occurs {
    /**
     * 
     * @param occurs
     * @param actualOccurs
     * @return
     */
    boolean occurs(int occurs, int actualOccurs);
}
