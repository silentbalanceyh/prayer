package com.prayer.shutter.engine;

import static com.prayer.util.reflection.Instance.singleton;

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
public class MetaServerShutter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * Main
     * @param args
     * @throws AbstractException
     */
    public static void main(final String... args) throws AbstractException{
        /** 1.在Consoler中替换日志输出 **/
        Log.setLoggerFactory(new LoggerFactorySLF4JImpl());
        
        final Launcher launcher = singleton(Injections.Meta.LAUNCHER);
        launcher.stop();
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
