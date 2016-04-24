package com.prayer.metaserver.h2.callback;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.text.MessageFormat;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.fun.OutLet;
import com.prayer.facade.metaserver.h2.H2Messages.Database;

/**
 * 
 * @author Lang
 *
 */
public class MetaServerClosurer implements Runnable {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaServerClosurer.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient List<Server> database;
    /** **/
    private transient OutLet exit;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    public MetaServerClosurer(final List<Server> database, final OutLet exit) {
        this.database = database;
        this.exit = exit;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public void run() {
        /** 1.轮询次数 **/
        long time = 0;
        int duration = 10000;
        while (true) {
            try {
                /** 10s轮询一次 **/
                // TODO：轮询暂时不需要配置，10s轮询一次
                Thread.sleep(duration);
                boolean shutdown = true;
                int size = this.database.size();
                for (final Server server : this.database) {
                    if (server.isRunning(false)) {
                        shutdown = false;
                        break;
                    } else {
                        size--;
                        info(LOGGER, MessageFormat.format(Database.INFO_QUEUE, String.valueOf(server.getPort()),
                                String.valueOf(size)));
                    }
                }
                // 满足了停止的条件，直接停止H2的Server
                if (shutdown) {
                    info(LOGGER, Database.Single.T_STOPPED);
                    break;
                } else {
                    time++;
                    if (Constants.ZERO == time % 180) {
                        info(LOGGER, MessageFormat.format(Database.INFO_RUN_QUE, String.valueOf(size),
                                String.valueOf(time)));
                    }
                }
            } catch (InterruptedException ex) {
                jvmError(LOGGER, ex);
            }
        }
        exit.exit();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
