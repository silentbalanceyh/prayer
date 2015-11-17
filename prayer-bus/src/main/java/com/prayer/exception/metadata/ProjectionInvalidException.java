package com.prayer.exception.metadata;

import com.prayer.exception.AbstractDatabaseException;

/**
 * 使用AND和OR连接Expression时不可连接LeafNode
 * @author Lang
 *
 */
public class ProjectionInvalidException extends AbstractDatabaseException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -6734581549794307281L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param expr
     * @param type
     */
    public ProjectionInvalidException(final Class<?> clazz, final String expr, final String type){
        super(clazz, -11004, expr, type);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11004;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
