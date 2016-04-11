package com.prayer.builder.mssql;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.builder.mssql.alter.MsSqlReferencer;
import com.prayer.builder.mssql.alter.MsSqlReflector;
import com.prayer.builder.mssql.part.MsSqlFieldSaber;
import com.prayer.builder.mssql.part.MsSqlKeySaber;
import com.prayer.facade.builder.line.FieldSaber;
import com.prayer.facade.builder.line.KeySaber;
import com.prayer.facade.builder.reflector.Reflector;
import com.prayer.facade.model.crucial.Referencer;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.sql.special.MsSqlStatement;
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
    /** 引用构造器 **/
    @Override
    public Referencer referencer(){
        return singleton(MsSqlReferencer.class, this.connection());
    }
    /** 字段操作器 **/
    @Override
    public FieldSaber getFieldSaber() {
        return singleton(MsSqlFieldSaber.class);
    }
    /** **/
    @Override
    public KeySaber getKeySaber(){
        return singleton(MsSqlKeySaber.class);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
