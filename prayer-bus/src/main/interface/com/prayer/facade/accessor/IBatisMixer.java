package com.prayer.facade.accessor;

/** **/
@FunctionalInterface
public interface IBatisMixer {
    /**
     * 根据index和size计算Page过程的SQL最终参数
     * 
     * @param index
     * @param size
     * @return
     */
    int offset(int index, int size);
}
