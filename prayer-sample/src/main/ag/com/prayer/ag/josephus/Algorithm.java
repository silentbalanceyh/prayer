package com.prayer.ag.josephus;

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
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param total
     * @param witdh
     * @return
     */
    public String josephuProcess(final int total, final int width) {
        /**
         * 1.构造链表
         */
        final SingleLinkList<Integer> list = this.createTotal(total);
        list.setBegin();
        final int size = list.size();
        for (int idxi = 0; idxi < size - 1; idxi++) {
            list.print();
            for (int idxj = 0; idxj < width; idxj++) {
                list.moveNext();
            }
            list.removeCurrent();
        }
        return String.valueOf(list.getCurrent());

    }

    /**
     * 
     * @param total
     * @return
     */
    public SingleLinkList<Integer> createTotal(final int total) {
        final SingleLinkList<Integer> links = new SingleLinkList<>();
        for (int idx = 0; idx < total; idx++) {
            links.addNode(idx + 1);
        }
        return links;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
