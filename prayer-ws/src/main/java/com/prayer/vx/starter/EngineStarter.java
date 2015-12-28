package com.prayer.vx.starter;

import com.prayer.base.exception.AbstractException;
import com.prayer.vx.engine.EngineLauncher;

/**
 * 
 * @author Lang
 *
 */
public class EngineStarter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param args
     */
    public static void main(final String... args) throws AbstractException {
        final EngineLauncher launcher = EngineLauncher.create();
        launcher.runTool(args);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
