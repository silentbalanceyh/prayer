package com.prayer.metaserver.h2.server.poll;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.metaserver.h2.H2Exit;
import com.prayer.facade.engine.metaserver.h2.H2Messages.Database;

/**
 * 
 * @author Lang
 *
 */
public class SingleClosurer implements Runnable {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleClosurer.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient Server database;
    /** **/
    private transient H2Exit exit;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    public SingleClosurer(final Server database, final H2Exit exit) {
        this.database = database;
        this.exit = exit;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public void run() {
        while (true) {
            try {
                /** 30s轮询一次 **/
                // TODO：轮询暂时不需要配置，30s轮询一次
                Thread.sleep(10000);
                if (!database.isRunning(false)) {
                    info(LOGGER, Database.Single.T_STOPPED);
                    break;
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
