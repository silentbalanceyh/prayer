package com.prayer.booter.engine;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Launcher;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.vertx.launcher.VertxLauncher;

import net.sf.oval.internal.Log;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

/**
 * 
 * @author Lang
 *
 */
public class EngineBooter {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(EngineBooter.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param args
     * @throws AbstractException
     */
    public static void main(final String args[]) {
        /** 1.在Consoler中替换日志输出 **/
        Log.setLoggerFactory(new LoggerFactorySLF4JImpl());
        /** 2.启动 **/
        final Launcher launcher = singleton(VertxLauncher.class);
        try {
            launcher.start();
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
