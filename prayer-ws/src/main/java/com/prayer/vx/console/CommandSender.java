package com.prayer.vx.console;

import com.prayer.util.cv.Constants;
import com.prayer.vx.console.commands.HelpCommand;
import com.prayer.vx.console.commands.StatusCommand;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public final class CommandSender {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject exit(final String... args){
        System.out.println("Exit Prayer Engine Console successfully !");
        System.exit(Constants.ZERO);
        return null; // NOPMD
    }
    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject help(final String... args){
        final HelpCommand command = new HelpCommand();
        command.execute(args);
        return null; // NOPMD
    }
    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject status(final String... args){
        final StatusCommand command = new StatusCommand();
        return command.execute(args);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private CommandSender(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
