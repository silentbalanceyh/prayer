package com.prayer.util.dao;

import static com.prayer.util.debug.Log.jvmError;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 执行SQL脚本的单独类
 * 
 * @author Lang
 *
 */
@Guarded
public final class SqlKit {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlKit.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static int execute(@NotNull final JdbcTemplate jdbc, @NotNull @NotBlank @NotEmpty final String sql) {
        int ret = Constants.RC_FAILURE;
        try (final Connection conn = jdbc.getDataSource().getConnection()) {
            final ScriptRunner runner = new ScriptRunner(conn);
            final ByteArrayInputStream istream = new ByteArrayInputStream(sql.getBytes(Resources.SYS_ENCODING.name()));
            final Reader sqlReader = new InputStreamReader(istream);
            // set to false, runs script line by line
            runner.setSendFullScript(false);
            runner.runScript(sqlReader);
            runner.closeConnection();
            // 默认日志级别输出SQL语句是DEBUG级别，只要不是级别则不会输出
            // conn.close();
            ret = Constants.RC_SUCCESS;
        } catch (SQLException | UnsupportedEncodingException ex) {
            jvmError(LOGGER, ex);
        }
        return ret;
    }

    /** **/
    public static int execute(@NotNull final JdbcTemplate jdbc, @NotNull final Reader reader) {
        int ret = Constants.RC_FAILURE;
        try (final Connection conn = jdbc.getDataSource().getConnection()) {
            final ScriptRunner runner = new ScriptRunner(conn);
            // set to false, runs script line by line
            runner.setSendFullScript(true);
            runner.runScript(reader);
            runner.closeConnection();
            // 默认日志级别输出SQL语句是DEBUG级别，只要不是级别则不会输出
            // conn.close();
            ret = Constants.RC_SUCCESS;
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
        }
        return ret;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private SqlKit() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
