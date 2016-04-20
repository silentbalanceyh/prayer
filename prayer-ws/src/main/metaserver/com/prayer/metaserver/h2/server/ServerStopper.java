package com.prayer.metaserver.h2.server;

import static com.prayer.util.debug.Log.error;
import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.sql.SQLException;
import java.text.MessageFormat;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.engine.metaserver.h2.H2Messages;
import com.prayer.util.io.NetKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
class ServerStopper implements H2Messages {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStopper.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 停止H2
     * 
     * @param server
     * @return
     */
    public boolean stopDatabase(@NotNull final JsonArray options) {
        boolean status = true;
        final int length = options.size();
        for(int idx = 0; idx < length; idx++){
            final JsonObject data = options.getJsonObject(idx);
            status = this.stopDatabase(data);
        }
        return status;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 
     * @param options
     * @return
     */
    private boolean stopDatabase(@NotNull final JsonObject data) {
        final int port = data.getInteger("tcp.port");
        boolean status = false;
        if (NetKit.isTaken(port)) {
            info(LOGGER, MessageFormat.format(Database.Single.INFO_STOPPING, String.valueOf(port)));
            try {
                final StringBuilder uri = new StringBuilder();
                uri.append("tcp://").append(data.getString("host")).append(Symbol.COLON)
                        .append(port);
                final String url = uri.toString();
                info(LOGGER, MessageFormat.format(Database.Single.INFO_STOPPED, url));
                Server.shutdownTcpServer(url, Constants.TCP_PASSWORD, true, true);
                status = true;
            } catch (SQLException ex) {
                jvmError(LOGGER, ex);
                ex.printStackTrace();
            }
        } else {
            error(LOGGER, MessageFormat.format(Database.Single.INFO_NOT_RUNNING, String.valueOf(port)));
        }
        return status;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
