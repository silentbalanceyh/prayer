package com.prayer.accessor.impl;
/**
 * 
 * @author Lang
 *
 */
public final class IBatisPagerMixer {
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
    private IBatisPagerMixer(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
