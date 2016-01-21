package com.prayer.exception.database;

import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 数据库没有在运行的异常信息
 * @author Lang
 *
 */
public class DatabaseStoppedException extends AbstractDatabaseException{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -7794353511568761107L;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public DatabaseStoppedException(final Class<?> clazz, final String url){
        super(clazz,-11016, url);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode(){
        return -11016;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
