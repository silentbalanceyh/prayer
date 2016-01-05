package com.prayer.ag.multi;

final class Algorithm {
    /**
     * 大数据乘法
     * 
     * @param firstArr
     * @param secondArr
     * @return
     */
    public int[] multi(final int[] firstArr, final int[] secondArr) {
        /**
         * 1.将两个数组的长度计算，合计乘法最终结果的总长度，构造新的结果数组
         */
        final int size = firstArr.length + secondArr.length;
        final int[] retArr = new int[size];
        /**
         * 2.第一级运算，计算每一个单元格的乘积 <code>1,2,3</code> <code>4,5,6</code>
         */
        for (int i = secondArr.length - 1; i >= 0; i--) {
            /**
             * 
             */
            int k = i + firstArr.length;
            for (int j = firstArr.length - 1; j >= 0; j--) {
                retArr[k--] += secondArr[i] * firstArr[j];
            }
        }
        /**
         * 3.每个单元格中都有数据，但是上边计算的数据有可能超过10，这种情况需要进行10除转换，进位运算
         */
        for (int i = size - 1; i >= 0; i--) {
            if (retArr[i] >= 10) {
                /**
                 * 3.1.处理进位
                 */
                retArr[i - 1] += retArr[i] / 10;
                /**
                 * 3.2.保留个位
                 */
                retArr[i] %= 10;
            }
        }
        return retArr;
    }
}
