package com.prayer.metaserver.h2.server;

import static com.prayer.util.debug.Log.error;
import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.metaserver.h2.H2Messages;
import com.prayer.util.io.NetKit;

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
     * 停止 Web Console
     * 
     * @param server
     * @return
     */
    public boolean stopWebConsole(@NotNull final Server server) {
        /** 1.检查端口占用 **/
        boolean stopped = false;
        final String port = String.valueOf(server.getPort());
        if (NetKit.isTaken(server.getPort())) {
            info(LOGGER, MessageFormat.format(WebConsole.INFO_STOPPING, port));
            System.out.println(server.getStatus());
        } else {
            error(LOGGER, MessageFormat.format(WebConsole.INFO_NOT_RUNNING, port));
        }
        return stopped;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
