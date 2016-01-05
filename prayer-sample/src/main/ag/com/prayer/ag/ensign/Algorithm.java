package com.prayer.ag.ensign;

/**
 * 
 * @author Lang
 *
 */
final class Algorithm {

    private transient int moves = 0;

    /**
     * 【解法一】1.快速排序算法，建立3个索引，begin前索引（从0开始），current中索引，end后索引（从length - 1开始）
     */
    // 1）current所指的元素为0，则和begin索引的元素交换，之后current++，begin++
    // 2）current所指的元素为1，则不做任何变换，之后current++
    // 3）current所指的元素为2，和end索引的涌入交换，之后current不变，end--
    public int quickProcess(final int[] flags) {
        /**
         * 1.初始化
         */
        this.moves = 0;
        /**
         * 2.定义begin和end索引
         */
        int current = 0, begin = 0;
        int end = flags.length - 1;
        /**
         * 3.用数组遍历索引代替current
         */
        do {
            final int value = flags[current];
            if (0 == value) {
                /**
                 * 4.1.如果为0的时候
                 */
                swapFlag(flags, current, begin);
                current++;
                begin++;
            } else if (1 == value) {
                /**
                 * 4.2.如果为1的时候
                 */
                current++;
            } else if (2 == value) {
                /**
                 * 4.3.如果为2的时候
                 */
                swapFlag(flags, current, end);
                end--;
            }
        } while (current <= end);
        /**
         * 最终返回Move了多少次
         */
        return this.moves;
    }

    /**
     * 提供数组中两个需要交换元素的索引，将两个元素进行值交换，并且统计交换次数
     * 
     * @param ldx
     * @param rdx
     */
    private void swapFlag(final int[] flags, final int ldx, final int rdx) {
        flags[ldx] = flags[ldx] + flags[rdx];
        flags[rdx] = flags[ldx] - flags[rdx];
        flags[ldx] = flags[ldx] - flags[rdx];
        // 这样算作交换一次
        moves++;
    }
}
