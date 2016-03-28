package com.prayer.builder.mssql.alter;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.builder.mssql.part.MsSqlKeySaber;
import com.prayer.facade.builder.line.KeySaber;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.sql.special.MsSqlStatement;
import com.prayer.facade.sql.special.MsSqlWord;
import com.prayer.fantasm.builder.AbstractReferencer;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * SQL Server中处理Referencer的方法
 * 
 * @author Lang
 *
 */
@Guarded
public final class MsSqlReferencer extends AbstractReferencer implements MsSqlStatement, MsSqlWord {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 使用传入连接构造 **/
    public MsSqlReferencer(final JdbcConnection connection) {
        super(connection);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 读取SQL的语句 **/
    @Override
    public String buildRefSQL(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        return MessageFormat.format(R_REFERENCES, table, column);
    }

    /** 获取当前的Saber **/
    @Override
    public KeySaber getKeySaber() {
        return singleton(MsSqlKeySaber.class);
    }

    /** 元数据映射表 **/
    @Override
    public ConcurrentMap<String, String> getMetadata() {
        final ConcurrentMap<String, String> metaMap = new ConcurrentHashMap<>();
        metaMap.put(CONSTRAINT, Metadata.CONSTRAINT);
        metaMap.put(TABLE, Metadata.TABLE);
        metaMap.put(COLUMN, Metadata.COLUMN);
        return metaMap;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
