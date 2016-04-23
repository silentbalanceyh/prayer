package com.prayer.booter.engine;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Launcher;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Injections;

import net.sf.oval.internal.Log;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

/**
 * MetaServer的启动器，用于启动Meta Server
 * 
 * @author Lang
 *
 */
public class MetaServerBooter {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaServerBooter.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * Main
     * 
     * @param args
     * @throws AbstractException
     */
    public static void main(final String... args) {
        /** 1.在Consoler中替换日志输出 **/
        Log.setLoggerFactory(new LoggerFactorySLF4JImpl());
        
        final Launcher launcher = singleton(Injections.Meta.LAUNCHER);
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
