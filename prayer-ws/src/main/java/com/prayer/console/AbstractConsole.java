package com.prayer.console;

import java.util.Scanner;

import com.prayer.constant.Symbol;

import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractConsole {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /**
     * 获取Help的Map Information
     * 
     * @return
     */
    public abstract JsonObject getHelp();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     */
    protected void populateHelp() {
        System.out.println("Commands are case insensitive, please refer following comments: "); // NOPMD
        for (final String arg : getHelp().fieldNames()) {
            System.out.println(generateArgLine(arg, getHelp().getString(arg))); // NOPMD
        }
        System.out.println(); // NOPMD
    }
    /**
     * 
     * @param prefix
     * @return
     */
    @SuppressWarnings("resource")
    protected String[] prompt(final String prefix) {
        final Scanner scanner = new Scanner(System.in);
        System.out.print(prefix); // NOPMD
        final String line = scanner.nextLine().trim();
        return StringUtil.split(line, Symbol.SPACE);
    }

    /**
     * Console Formatter
     * 
     * @param arg
     * @param comments
     * @return
     */
    protected String generateArgLine(final String arg, final String comments) {
        final int wide = 24 - arg.length();
        final StringBuilder argsLine = new StringBuilder();
        argsLine.append(arg);
        for (int idx = 0; idx < wide; idx++) {
            argsLine.append(Symbol.SPACE);
        }
        argsLine.append(comments);
        return argsLine.toString();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
