package com.prayer.ag.poker;

import com.prayer.ag.util.linklist.SingleLinkList;

/**
 * 算法主体
 * 
 * @author Lang
 *
 */
final class Algorithm {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 法牌函数
     * 
     * @param total
     * @return
     */
    public String pokerProcess(final int total) {
        /**
         * 1.构造这个数量的空链表
         */
        final SingleLinkList<Integer> poker = this.initList(total);
        poker.setBegin();
        for (int idx = 1; idx <= total; idx++) {
            /** 获取游标位置 **/
            int position = poker.getPosition();
            poker.setCurrent(idx);
            for (int idxj = 0; idxj <= idx; idxj++) {
                /** 2.寻找插牌的位置 **/
                poker.moveNext();
                final Integer value = poker.getCurrent();
                if (value != 0) {
                    /** 3.如果当前位置中已经包含了牌，则顺序查找下一个位置 **/
                    idxj--;
                }
                if (idx == total) {
                    /** 4.插牌完成，跳出 **/
                    break;
                }
            }
            /** 打印标号 **/
            appendLine(position, total);
            poker.print(true);
        }
        return poker.toString();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private void appendLine(int position, final int total) {
        final StringBuilder builder = new StringBuilder();
        if (position == total) {
            position = 0;
        }
        for (int idx = 0; idx < position; idx++) {
            /** 计算游标基础位置 **/
            builder.append("   ");
        }
        builder.append('*');
        System.out.println(builder.toString());
    }

    private SingleLinkList<Integer> initList(final int total) {
        final SingleLinkList<Integer> links = new SingleLinkList<>();
        for (int idx = 0; idx < total; idx++) {
            /** 初始化的时候所有值设为0 **/
            links.addNode(0);
        }
        return links;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
