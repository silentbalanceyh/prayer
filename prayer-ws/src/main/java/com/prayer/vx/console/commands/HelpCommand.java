package com.prayer.vx.console.commands;

import org.apache.commons.cli.CommandLine;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public final class HelpCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String command() {
        return "help";
    }

    // ~ Methods =============================================
    /** **/
    public JsonObject execute(final String... args) {
        final CommandLine cl = this.parse(args);
        // 如果包含有-c
        if (null != cl) {
            if (cl.hasOption('c')) {
                final String command = cl.getOptionValue('c');
                this.help(command);
            }
        }
        return null;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
