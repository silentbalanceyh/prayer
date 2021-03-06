package com.prayer.console;

import static com.prayer.util.Instance.singleton;

import com.prayer.console.commands.ApiCommand;
import com.prayer.console.commands.BDataCommand;
import com.prayer.console.commands.Command;
import com.prayer.console.commands.EngineCommand;
import com.prayer.console.commands.HelpCommand;
import com.prayer.console.commands.InputCommand;
import com.prayer.console.commands.MServerCommand;
import com.prayer.console.commands.SchemaCommand;
import com.prayer.console.commands.StatusCommand;
import com.prayer.console.commands.ToolCommand;
import com.prayer.constant.Constants;

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
    public static JsonObject exit(final String... args) {
        System.out.println("Exit Prayer Engine Console successfully !"); // NOPMD
        System.exit(Constants.ZERO); // NOPMD
        return null; // NOPMD
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject help(final String... args) {
        return execute(HelpCommand.class, args);
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject status(final String... args) {
        return execute(StatusCommand.class, args);
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject mserver(final String... args) {
        return execute(MServerCommand.class, args);
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject engine(final String... args) {
        return execute(EngineCommand.class, args);
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject schema(final String... args) {
        return execute(SchemaCommand.class, args);
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject api(final String... args) {
        return execute(ApiCommand.class, args);
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject input(final String... args) {
        return execute(InputCommand.class, args);
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject tool(final String... args) {
        return execute(ToolCommand.class, args);
    }

    /**
     * 
     * @param args
     * @return
     */
    public static JsonObject bdata(final String... args) {
        return execute(BDataCommand.class, args);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static JsonObject execute(final Class<?> clazz, final String... args) {
        final Command command = singleton(clazz);
        return command.execute(args);
    }

    private CommandSender() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
