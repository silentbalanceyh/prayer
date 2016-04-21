package com.prayer.console;

import com.prayer.console.util.Outer;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class OutGoing {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** Header **/
    public static void outHeader() {
        Outer.outLn();
        Outer.outLn("|=======> Prayer Framework v.1.0 --------------------------------------|");
        Outer.outLn("|                                                                      |");
        Outer.outLn("|         Welcome to Background Console Application                    |");
        Outer.outLn("|                                                                      |");
        Outer.outLn("------------------------------------------------------------------------");
    }

    /** Connect **/
    public static void outConnect(final String topic) {
        Outer.outLn("[Start] Consoler Starting... ");
        Outer.outLn("[Consoler] -> Connect to " + topic);
        Outer.out("[Consoler] -> Connecting...");
        outProgress(2);
    }

    /** Exit **/
    public static void outExit(final String message) {
        Outer.outLn("[Consoler] -> {0}", message);
        Outer.out("[Stop] Background Consoler Stopping... ");
        outProgress(2);
        Outer.outLn("[End] Consoler Exit.");
    }

    /** Progress **/
    public static void outProgress(final int second) {
        int time = 0;
        while (true) {
            Outer.out(".");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            time++;
            if (time == second) {
                Outer.outLn("");
                break;
            }
        }
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private OutGoing(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
