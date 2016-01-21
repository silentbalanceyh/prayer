package com.prayer.builder.impl.mssql;

import com.prayer.base.builder.AbstractRefresher;
import com.prayer.facade.builder.special.MsSqlStatement;
import com.prayer.facade.pool.JdbcConnection;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
/**
 * 
 * @author Lang
 *
 */
@Guarded
public class MsSqlRefresher extends AbstractRefresher implements MsSqlStatement{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param connection
     */
    public MsSqlRefresher(@NotNull final JdbcConnection connection){
        super(connection);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
