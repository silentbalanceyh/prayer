package com.prayer.exception.system;

import com.prayer.fantasm.exception.AbstractSystemException;

/**
 * 【Checked】Error-20004：序列化过程出错
 * 
 * @author Lang
 *
 */
public class SerializationException extends AbstractSystemException {

    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 5652134500408183685L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param nodeName
     */
    public SerializationException(final Class<?> clazz, final String nodeName) {
        super(clazz, -20004, nodeName);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -20004;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
