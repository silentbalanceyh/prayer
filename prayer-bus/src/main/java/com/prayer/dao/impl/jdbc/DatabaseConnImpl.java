package com.prayer.dao.impl.jdbc;

import static com.prayer.util.Log.jvmError;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.dao.jdbc.DatabaseDirector;
import com.prayer.model.bus.Metadata;

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
            @NotBlank @NotEmpty @NotNull final String username, @NotBlank @NotEmpty @NotNull final String password) {
        Metadata metadata = null;
        try (final Connection conn = DriverManager.getConnection(url, username, password)) {
            final DatabaseMetaData sqlMeta = conn.getMetaData();
            /**
             * 传入的category是null值也就是获取的元数据是没有category的信息的
             */
            metadata = new Metadata(sqlMeta, null);
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
        }
        return metadata;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
