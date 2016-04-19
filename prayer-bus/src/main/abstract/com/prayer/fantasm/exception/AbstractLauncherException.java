package com.prayer.fantasm.exception;

import static com.prayer.util.debug.Error.error;

/**
 * 启动Error抽象类
 * @author Lang
 *
 */
public abstract class AbstractLauncherException extends AbstractException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 2530626493427597014L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /**
     * @param message
     */
    public AbstractLauncherException(final String message) {
        super(message);
    }

    /**
     * 
     * @param clazz
     * @param errorCode
     * @param params
     */
    public AbstractLauncherException(final Class<?> clazz, final int errorCode, final Object... params) {
        this(error(clazz, errorCode, params));
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
