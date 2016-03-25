package com.prayer.builder.mssql;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.builder.mssql.alter.MsSqlReflector;
import com.prayer.facade.builder.reflector.Reflector;
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
public class MsSqlRefresher extends AbstractRefresher implements MsSqlStatement {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param connection
     */
    public MsSqlRefresher(@NotNull final JdbcConnection connection) {
        super(connection);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** Schema的反向构造器 **/
    @Override
    public Reflector reflector() {
        return singleton(MsSqlReflector.class, this.connection());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
