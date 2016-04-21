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
public final class Outer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
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
    public static void outLn(){
        outLn(Constants.EMPTY_STR);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Outer() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
