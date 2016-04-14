package com.prayer.builder.mssql.alter;

import java.text.MessageFormat;
import java.util.List;

import com.prayer.facade.builder.reflector.Reflector;
import com.prayer.facade.database.pool.JdbcConnection;
import com.prayer.facade.database.sql.special.MsSqlStatement;
import com.prayer.facade.database.sql.special.MsSqlWord;
import com.prayer.fantasm.builder.line.AbstractReflector;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Schema的反向读取器，从数据库中读取相关信息生成Schema
 * 
 * @author Lang
 *
 */
@Guarded
public class MsSqlReflector extends AbstractReflector implements Reflector, MsSqlStatement, MsSqlWord {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 连接通过构造函数传递
     * 
     * @param connection
     */
    public MsSqlReflector(@NotNull final JdbcConnection connection) {
        super(connection);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 获取当前系统中对应的表所有的Constraints **/
    public List<String> getConstraints(@NotNull @NotBlank @NotEmpty final String table) {
        final String sql = MessageFormat.format(R_CONSTRAINTS, this.database(), table);
        return this.connection().select(sql, Metadata.CONSTRAINT);
    }

    /** 获取当前系统中对应的所有Columns **/
    public List<String> getColumns(@NotNull @NotBlank @NotEmpty final String table) {
        final String sql = MessageFormat.format(R_COLUMNS, this.database(), table);
        return this.connection().select(sql, Metadata.COLUMN);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
