package com.prayer.util.dao;

import static com.prayer.util.debug.Log.debug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.prayer.constant.log.DebugKey;
import com.prayer.facade.dao.JdbcTransducer.T;
import com.prayer.facade.kernel.Value;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Input {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Input.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * Insert语句的参数设置，不可不传参
     * 
     * @param sql
     * @param values
     * @param isRetKey
     * @return
     */
    public static PreparedStatementCreator prepStmt(@NotNull @NotBlank @NotEmpty final String sql,
            @NotNull @MinSize(1) final List<Value<?>> values, final boolean isRetKey) {
        return new PreparedStatementCreator() {
            /** **/
            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                PreparedStatement stmt = null;
                if (isRetKey) {
                    stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                } else {
                    stmt = con.prepareStatement(sql);
                }
                final int size = values.size();
                for (int idx = 1; idx <= size; idx++) {
                    // 以数据库的Index为主，数据库从1开始索引，数组本身从0开始索引
                    debug(LOGGER, DebugKey.INFO_JDBC_PARAM, idx, values.get(idx - 1));
                    T.get().injectArgs(stmt, idx, values.get(idx - 1));
                }
                return stmt;
            }
        };
    }

    /**
     * Select语句的参数设置，可不传参，则表示Select所有
     * 
     * @param sql
     * @param values
     * @return
     */
    public static PreparedStatementCreator prepStmt(@NotNull @NotBlank @NotEmpty final String sql,
            @NotNull final List<Value<?>> values) {
        return new PreparedStatementCreator() {
            /** **/
            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                final PreparedStatement stmt = con.prepareStatement(sql);
                final int size = values.size();
                for (int idx = 1; idx <= size; idx++) {
                    // 以数据库的Index为主，数据库从1开始索引，数组本身从0开始索引
                    debug(LOGGER, DebugKey.INFO_JDBC_PARAM, idx, values.get(idx - 1));
                    T.get().injectArgs(stmt, idx, values.get(idx - 1));
                }
                return stmt;
            };
        };
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Input() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
