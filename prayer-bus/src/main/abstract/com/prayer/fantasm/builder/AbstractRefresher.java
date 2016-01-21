package com.prayer.fantasm.builder;

import com.prayer.facade.builder.Refresher;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 抽象层的Refresher
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractRefresher implements Refresher {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    private transient final JdbcConnection connection; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractRefresher(@NotNull final JdbcConnection connection){
        this.connection = connection;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String buildAlterSQL(@NotNull final Schema schema) throws AbstractDatabaseException{
        
        return null;
    }
    // ~ Methods =============================================
    /**
     * 读取JDBC数据库连接
     * @return
     */
    protected JdbcConnection connection(){
        return this.connection;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
