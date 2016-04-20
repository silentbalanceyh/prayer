package com.prayer.inversor.booter;

import com.prayer.facade.engine.Launcher;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.metaserver.launcher.H2Launcher;

/**
 * MetaServer的启动器，用于启动Meta Server
 * 
 * @author Lang
 *
 */
public class MetaServerBooter {
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
        final Launcher launcher = new H2Launcher();
        launcher.start();
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
