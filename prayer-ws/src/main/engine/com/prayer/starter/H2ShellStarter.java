package com.prayer.starter;

import com.prayer.base.exception.AbstractException;
import com.prayer.engine.ConsoleLauncher;

/**
 * 
 * @author Lang
 *
 */
public class H2ShellStarter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param args
     * @throws AbstractException
     */
    public static void main(final String... args) throws AbstractException {
        final ConsoleLauncher console = ConsoleLauncher.create();
        console.runTool(new String[]{"-pshell"});
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
