package com.prayer.builder.impl.mssql;

import com.prayer.facade.builder.special.MsSqlStatement;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.fantasm.builder.AbstractRefresher;

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
