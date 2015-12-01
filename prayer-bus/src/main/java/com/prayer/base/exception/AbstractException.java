package com.prayer.base.exception;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractException extends Exception {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 3478027283838490966L;

    // ~ Instance Fields =====================================
    /** **/
    protected transient String errorMessage;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * @param message
     */
    public AbstractException(final String message) {
        super(message);
        this.errorMessage = message;
    }
    // ~ Abstract Methods ====================================
    /** 获取错误代码 **/
    public abstract int getErrorCode();
    /** 获取错误信息 **/
    public String getErrorMessage(){
        return this.errorMessage;
    }
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
