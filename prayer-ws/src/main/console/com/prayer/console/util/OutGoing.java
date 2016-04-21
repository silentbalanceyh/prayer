package com.prayer.console.util;

import java.text.MessageFormat;

import com.prayer.facade.constant.Constants;

import net.sf.oval.constraint.NotNull;
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
    public static void outHeader(final String title) {
        outLn();
        outLn("|=======> Prayer Framework v.1.0 --------------------------------------|");
        outLn("|                                                                      |");
        outLn("|         Welcome to Background Console Application                    |");
        outLn("|                                                                      |");
        outLn("------------------------------------------------------------------------");
        outLn();
        outLn(" ~~ Application Name : " + title + " ~~");
        outLn();
    }

    /** Connect **/
    public static void outConnect(final String topic) {
        outLn("[<Start>] -------------- Background Consoler Starting... ---------------");
        outLn("[Consoler] -> Connect to " + topic);
        out("[Consoler] -> Connecting...");
        outProgress(2);
    }

    /** Exit **/
    public static void outExit(final String message) {
        if(null != message){
            outLn("[Consoler] -> {0} ", message);
        }
        outLn("[<Stop>] -------------- Background Consoler Stopping... ---------------");
        outLn("[End] Consoler Exit.");
    }

    /** Progress **/
    public static void outProgress(final int second) {
        int time = 0;
        while (true) {
            out(".");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            time++;
            if (time == second) {
                outLn("");
                break;
            }
        }
    }

    // ~ Simple Out ==========================================
    /**
     * Console级别的输出
     * 
     * @param key
     * @param params
     */
    public static void out(@NotNull final String key, final Object... params) {
        String message = null;
        if (Constants.ZERO == params.length) {
            /** 直接为key **/
            message = key;
        } else {
            /** 这个时候key为pattern **/
            message = MessageFormat.format(key, params);
        }
        System.out.print(message);
    }

    /**
     * Console级别的输出
     * 
     * @param key
     * @param params
     */
    public static void outLn(@NotNull final String key, final Object... params) {
        out(key, params);
        System.out.println();
    }

    /**
     * Console空白行
     */
    public static void outLn() {
        outLn(Constants.EMPTY_STR);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private OutGoing() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
