package com.prayer.booter.tp;

import com.prayer.console.thirdpart.hc.HazelcastTerminal;
import com.prayer.facade.console.Consoler;

import net.sf.oval.internal.Log;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

/**
 * 
 * @author Lang
 *
 */
public class HazelcastBooter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static void main(final String args[]){
        /** 1.在Consoler中替换日志输出 **/
        Log.setLoggerFactory(new LoggerFactorySLF4JImpl());
        
        final Consoler consoler = new HazelcastTerminal();
        consoler.start(args);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
