package com.prayer.exception.system;

import com.prayer.exception.AbstractSystemException;

/**
 * 【Checked】Error-20002：系统资源读取IO异常
 * 
 * @author Lang
 * @see
 */
public class ResourceIOException extends AbstractSystemException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -7738630216189192981L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param filePath
     */
    public ResourceIOException(final Class<?> clazz, final String filePath) {
        super(clazz, -20002, filePath);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -20002;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
