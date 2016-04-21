package com.prayer.metaserver.h2.server;

import static com.prayer.util.debug.Log.error;
import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.metaserver.h2.H2Messages;
import com.prayer.util.io.NetKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
class ServerBooter implements H2Messages {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBooter.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 启动所有的Server
     * 
     * @param servers
     * @param clustered
     * @return
     */
    public boolean startDatabase(@NotNull final List<Server> servers) {
        boolean status = false;
        for (final Server server : servers) {
            status = this.startDatabase(server);
            if (status) {
                continue;
            } else {
                break;
            }
        }
        return status;
    }

    /**
     * 启动 Web Console
     **/
    public boolean startWebConsole(@NotNull final Server server) {
        /** 1.检查端口占用 **/
        boolean started = false;
        final String port = String.valueOf(server.getPort());
        if (NetKit.isTaken(server.getPort())) {
            /** Error.端口占用无法启动 **/
            error(LOGGER, MessageFormat.format(WebConsole.ERROR_PORT, port));
            started = false;
        } else {
            info(LOGGER, MessageFormat.format(WebConsole.INFO_STARTING, port));
            if (server.isRunning(false)) {
                info(LOGGER, MessageFormat.format(WebConsole.INFO_RUNNING, port));
                started = false;
            } else {
                try {
                    server.start();
                    info(LOGGER, MessageFormat.format(WebConsole.INFO_STARTED, port, server.getURL()));
                    info(LOGGER, MessageFormat.format(WebConsole.INFO_STATUS, server.getStatus()));
                    started = true;
                } catch (SQLException ex) {
                    jvmError(LOGGER, ex);
                    error(LOGGER, MessageFormat.format(WebConsole.ERROR_SQL, ex.getMessage()));
                    started = false;
                }
            }
        }
        return started;
    }

    // ~ Private Methods =====================================
    private boolean startDatabase(@NotNull final Server server) {
        /** 1.检查端口占用 **/
        boolean started = false;
        final String port = String.valueOf(server.getPort());
        if (NetKit.isTaken(server.getPort())) {
            /** Error.端口占用无法启动 **/
            error(LOGGER, MessageFormat.format(Database.Single.ERROR_PORT, port));
            started = false;
        } else {
            info(LOGGER, MessageFormat.format(Database.Single.INFO_STARTING, port));
            if (server.isRunning(false)) {
                info(LOGGER, MessageFormat.format(Database.Single.INFO_RUNNING, port));
                started = false;
            } else {
                try {
                    server.start();
                    info(LOGGER, MessageFormat.format(Database.Single.INFO_STARTED, port, server.getURL()));
                    info(LOGGER, MessageFormat.format(Database.Single.INFO_STATUS, server.getStatus()));
                    started = true;
                } catch (SQLException ex) {
                    jvmError(LOGGER, ex);
                    error(LOGGER, MessageFormat.format(Database.Single.ERROR_SQL, ex.getMessage()));
                    started = false;
                }
            }
        }
        return started;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
