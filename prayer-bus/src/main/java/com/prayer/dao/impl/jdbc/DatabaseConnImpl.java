package com.prayer.dao.impl.jdbc;

import static com.prayer.util.Log.peError;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.exception.database.DatabaseInvalidTokenException;
import com.prayer.exception.database.DatabaseStoppedException;
import com.prayer.facade.dao.jdbc.DatabaseDirector;
import com.prayer.model.bus.Metadata;
import com.prayer.util.Instance;
import com.prayer.util.PropertyKit;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Resources;

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
public final class DatabaseConnImpl implements DatabaseDirector {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnImpl.class);
    /** **/
    private static final PropertyKit LOADER = new PropertyKit(DatabaseConnImpl.class, Resources.DB_SQL_ERROR);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Override
    public Metadata getMetadata(@NotBlank @NotEmpty @NotNull final String url,
            @NotBlank @NotEmpty @NotNull final String username, @NotBlank @NotEmpty @NotNull final String password)
                    throws AbstractDatabaseException {
        Metadata metadata = null;
        try (final Connection conn = DriverManager.getConnection(url, username, password)) {
            final DatabaseMetaData sqlMeta = conn.getMetaData();
            /**
             * 传入的category是null值也就是获取的元数据是没有category的信息的
             */
            metadata = new Metadata(sqlMeta, null);
        } catch (SQLException ex) {
            final AbstractDatabaseException error = extractError(ex, url, username, password);
            // 特殊的Console使用，暂时不 peError(LOGGER, error);
            peError(LOGGER, error);
            throw error;
        }
        return metadata;
    }
    // ~ Private Methods =====================================

    private AbstractDatabaseException extractError(final SQLException ex, final String url, final String username,
            final String password) {
        AbstractDatabaseException error = null;
        // 1.根据Error Code获取Class
        final String clsName = LOADER.getString(String.valueOf(ex.getErrorCode()));
        if (StringKit.isNonNil(clsName)) {
            final Class<?> errorCls = Instance.clazz(clsName);
            if (DatabaseStoppedException.class == errorCls) {
                error = new DatabaseStoppedException(getClass(), url);
            } else if (DatabaseInvalidTokenException.class == errorCls) {
                error = new DatabaseInvalidTokenException(getClass(), url, username, password);
            }
        }
        return error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
