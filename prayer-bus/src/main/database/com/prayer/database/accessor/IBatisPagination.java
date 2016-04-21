package com.prayer.database.accessor;
/**
 * 
 * @author Lang
 *
 */
public final class IBatisPagination {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 计算Offset
     * @param index
     * @param size
     * @return
     */
    public static int offset(final int index, final int size){
        return (index - 1) * size;
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private IBatisPagination(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
