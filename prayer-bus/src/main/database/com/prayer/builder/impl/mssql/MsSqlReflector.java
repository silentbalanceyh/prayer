package com.prayer.builder.impl.mssql;

import com.prayer.facade.builder.reflector.Reflector;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Schema的反向读取器，从数据库中读取相关信息生成Schema
 * 
 * @author Lang
 *
 */
@Guarded
public class MsSqlReflector implements Reflector {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient JdbcConnection connection;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 连接通过构造函数传递
     * 
     * @param connection
     */
    public MsSqlReflector(@NotNull final JdbcConnection connection) {
        this.connection = connection;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 反向构造Schema
     */
    @Override
    public Schema buildSchema() {
        // TODO Auto-generated method stub
        return null;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
