package com.prayer.booter.tp;

import com.prayer.console.thirdpart.hc.HazelcastTerminal;
import com.prayer.facade.console.Consoler;

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
