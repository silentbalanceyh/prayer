package com.prayer.exception;

import static com.prayer.util.Error.error;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractTransactionException extends AbstractException {
    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 4040980881677364462L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 特殊数据处理异常 **/
    public AbstractTransactionException(final Class<?> clazz, final int errorCode, final String process) {
        super(error(clazz, errorCode, process));
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract int getErrorCode();
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
