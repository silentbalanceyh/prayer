package com.prayer.ag.latin;

import com.prayer.ag.util.linklist.SingleLinkList;

/**
 * 
 * @author Lang
 *
 */
final class Algorithm {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public String latinMatrix(final int n) {
        /**
         * 构造循环链表
         */
        final SingleLinkList<Integer> latin = this.initList(n);
        latin.setBegin();
        /**
         * 构造结果
         */
        final StringBuilder matrix = new StringBuilder("\n");
        for (int idx = 0; idx < n; idx++) {
            for( int iIdx = 0; iIdx < n; iIdx++ ){
                matrix.append(format(latin.getCurrent())).append(" ");
                latin.moveNext();
            }
            matrix.append("\n");
            latin.moveNext();
        }
        return matrix.toString();
    }
    
    private String format(final int value){
        if(value < 10){
            return " " + value;
        }else{
            return String.valueOf(value);
        }
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private SingleLinkList<Integer> initList(final int n) {
        final SingleLinkList<Integer> links = new SingleLinkList<>();
        for (int idx = 1; idx <= n; idx++) {
            /** 初始化的时候所有值设为0 **/
            links.addNode(idx);
        }
        return links;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
